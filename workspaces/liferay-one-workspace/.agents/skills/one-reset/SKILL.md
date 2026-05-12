---

allowed-tools: [Bash, Glob, Grep, Read, mcp__chrome-devtools__click, mcp__chrome-devtools__evaluate_script, mcp__chrome-devtools__fill, mcp__chrome-devtools__fill_form, mcp__chrome-devtools__list_pages, mcp__chrome-devtools__navigate_page, mcp__chrome-devtools__new_page, mcp__chrome-devtools__select_page, mcp__chrome-devtools__take_snapshot, mcp__chrome-devtools__wait_for]
description: Wipe the local Liferay One Docker environment (DB + volumes) and rebootstrap from scratch. Use when the user asks to reset, wipe, or re-bootstrap the local Liferay One server.
name: one-reset

---

# Reset Liferay One Server

Destroy the local Liferay One Docker stack — including all volumes — and rebootstrap a fresh environment. Run from `workspaces/liferay-one-workspace/`.

Use this when batch resources (Object definitions, list types, relationships) need to be re-imported from a clean slate, or when the running container is in a bad state and a restart will not recover it.

## 1. Tear Down with Volumes

`scripts/bootstrap.sh` does not currently accept a `clean` subcommand — it always runs the full `up` flow. Tear down via Docker Compose directly to wipe all volumes:

```bash
docker compose --file docker-compose.yaml down --volumes
```

This stops the Liferay and MySQL containers and deletes their named volumes, so the database and document library start empty on the next bootstrap.

## 2. Stage the Activation Key

`bootstrap.sh` only extracts the trial license. To keep the full activation key in place across resets, copy it from `~/dev/projects/licenses` into the workspace's Docker deploy folder before the rebuild — the folder is baked into the image during `buildDockerImage`, so the file must be present before `up` runs:

```bash
cp ~/dev/projects/licenses/activation-key-*.xml \
	build/docker/deploy/
```

When no activation key is present in `~/dev/projects/licenses`, skip this step and the trial license from `extract_license.sh` will be used instead.

## 3. Rebootstrap

```bash
scripts/bootstrap.sh up
```

This extracts the hotfix and trial license, rebuilds the Docker image (including any files staged in `build/docker/deploy/`), tags it as `liferay:local`, starts the containers, waits for `http://localhost:8080/c/portal/status` to return healthy, and deploys the client extensions (including the batch client extension that imports list types, Object definitions, relationships, etc.).

Liferay is ready when the script prints `Done. Liferay is running at http://localhost:8080.`

## 4. Verify

After bootstrap completes, tail the Liferay log to confirm batch import succeeded and no unexpected errors appeared:

```bash
docker compose --file docker-compose.yaml logs --tail 200 liferay
```

Watch for batch task failures (e.g., `No ObjectDefinition exists with the primary key 0`) — these typically indicate an ERC reference in one of the `batch/*.batch-engine-data.json` files points to an Object that does not exist in this Liferay version. Report any such failures to the user.

## 5. Create the Migration OAuth2 Application

The migration scripts under `~/dev/projects/liferay-one-scripts/liferay-one/migration/` authenticate to Liferay via OAuth2 client credentials. A reset wipes the OAuth2 application, so it has to be recreated. Use the same predictable client ID and secret on every reset so the migration scripts' config does not need to be updated:

| Field | Value |
|-------|-------|
| Client ID | `liferay-one-migration` |
| Client Secret | `liferay-one-migration-secret` |

Liferay does not expose a headless REST API for OAuth2 application admin in this DXP version, so this step drives the `OAuth2AdminPortlet` ActionURLs directly via `curl`. The whole flow — log in, create the application, install the predictable client secret, assign the migration-script scopes — collapses to one bash block. No browser, no `chrome-devtools` MCP, no snapshots.

Run this script:

```bash
#!/bin/bash
set -euo pipefail

ADMIN_EMAIL="test@liferay.com"
ADMIN_PASSWORD="test"
PORTAL_BASE="http://localhost:8080"

APP_NAME="Liferay One Migration"
APP_CLIENT_ID="liferay-one-migration"
APP_CLIENT_SECRET="liferay-one-migration-secret"

LOGIN_PORTLET="com_liferay_login_web_portlet_LoginPortlet"
OAUTH_PORTLET="com_liferay_oauth2_provider_web_internal_portlet_OAuth2AdminPortlet"

JAR=$(mktemp)
trap 'rm -f "$JAR"' EXIT

extract_p_auth() {
	grep -oE 'p_auth=[A-Za-z0-9]+' | head -n1 | cut -d= -f2
}

find_app_id_by_client_id() {
	python3 -c '
import sys, re
needle = sys.argv[1]
html = sys.stdin.read()
for match in re.finditer(r"<tr\b[^>]*>(.*?)</tr>", html, re.DOTALL):
	row = match.group(1)
	if needle in row:
		m = re.search(r"oAuth2ApplicationId=(\d+)", row)
		if m:
			print(m.group(1))
			sys.exit(0)
sys.exit(1)
' "$1"
}

# Seed cookie jar + harvest pre-login p_auth from the maximized login portlet.
LOGIN_HTML=$(curl --silent --cookie-jar "$JAR" --cookie "$JAR" \
	"${PORTAL_BASE}/web/guest/home?p_p_id=${LOGIN_PORTLET}&p_p_state=maximized")
P_AUTH=$(echo "$LOGIN_HTML" | extract_p_auth)

# POST login. The cookie jar now carries the authenticated session.
curl --silent --cookie-jar "$JAR" --cookie "$JAR" --location --output /dev/null \
	--request POST "${PORTAL_BASE}/web/guest/home?p_p_id=${LOGIN_PORTLET}&p_p_lifecycle=1&_${LOGIN_PORTLET}_jakarta.portlet.action=%2Flogin%2Flogin&p_auth=${P_AUTH}" \
	--data-urlencode "_${LOGIN_PORTLET}_login=${ADMIN_EMAIL}" \
	--data-urlencode "_${LOGIN_PORTLET}_password=${ADMIN_PASSWORD}"

if ! grep -qE $'\tID\t' "$JAR"; then
	echo "Login failed — no ID cookie in jar" >&2
	exit 1
fi

# Resolve the admin user's id. Basic auth works against the headless API here.
USER_ID=$(curl --silent --user "${ADMIN_EMAIL}:${ADMIN_PASSWORD}" \
	--get "${PORTAL_BASE}/o/headless-admin-user/v1.0/user-accounts" \
	--data-urlencode "filter=alternateName eq 'test'" \
	--data-urlencode "fields=id" \
	| python3 -c 'import sys,json;print(json.load(sys.stdin)["items"][0]["id"])')

# Harvest a fresh p_auth bound to the authenticated session. Reused for all
# subsequent ActionURL POSTs against the OAuth2 admin portlet.
OAUTH_LIST_URL="${PORTAL_BASE}/group/control_panel/manage?p_p_id=${OAUTH_PORTLET}&p_p_lifecycle=0&p_p_state=maximized"
P_AUTH=$(curl --silent --cookie-jar "$JAR" --cookie "$JAR" "$OAUTH_LIST_URL" | extract_p_auth)

UPDATE_ACTION="${PORTAL_BASE}/group/control_panel/manage?p_p_id=${OAUTH_PORTLET}&p_p_lifecycle=1&_${OAUTH_PORTLET}_jakarta.portlet.action=%2Foauth2_provider%2Fupdate_oauth2_application&p_auth=${P_AUTH}"

# Create the application. clientProfile=4 is Headless Server; the create code
# path regenerates clientSecret regardless of what we submit, so the
# predictable secret is installed by a second update POST below.
curl --silent --cookie-jar "$JAR" --cookie "$JAR" --location --output /dev/null \
	--request POST "$UPDATE_ACTION" \
	--data-urlencode "_${OAUTH_PORTLET}_oAuth2ApplicationId=0" \
	--data-urlencode "_${OAUTH_PORTLET}_name=${APP_NAME}" \
	--data-urlencode "_${OAUTH_PORTLET}_redirectURIs=${PORTAL_BASE}/" \
	--data-urlencode "_${OAUTH_PORTLET}_clientAuthenticationMethod=client_secret_post" \
	--data-urlencode "_${OAUTH_PORTLET}_clientProfile=4" \
	--data-urlencode "_${OAUTH_PORTLET}_grant-CLIENT_CREDENTIALS=true" \
	--data-urlencode "_${OAUTH_PORTLET}_clientCredentialUserId=${USER_ID}" \
	--data-urlencode "_${OAUTH_PORTLET}_clientCredentialUserName=test" \
	--data-urlencode "_${OAUTH_PORTLET}_clientId=${APP_CLIENT_ID}"

# Find the new app's internal id by scanning the list page for the row whose
# client_id column contains APP_CLIENT_ID.
LIST_HTML=$(curl --silent --cookie-jar "$JAR" --cookie "$JAR" "$OAUTH_LIST_URL")
APP_ID=$(echo "$LIST_HTML" | find_app_id_by_client_id "$APP_CLIENT_ID")

if [ -z "$APP_ID" ]; then
	echo "Could not locate application id for clientId=${APP_CLIENT_ID}" >&2
	exit 1
fi

# Second update: install the predictable client secret.
curl --silent --cookie-jar "$JAR" --cookie "$JAR" --location --output /dev/null \
	--request POST "$UPDATE_ACTION" \
	--data-urlencode "_${OAUTH_PORTLET}_oAuth2ApplicationId=${APP_ID}" \
	--data-urlencode "_${OAUTH_PORTLET}_name=${APP_NAME}" \
	--data-urlencode "_${OAUTH_PORTLET}_redirectURIs=${PORTAL_BASE}/" \
	--data-urlencode "_${OAUTH_PORTLET}_clientAuthenticationMethod=client_secret_post" \
	--data-urlencode "_${OAUTH_PORTLET}_clientProfile=4" \
	--data-urlencode "_${OAUTH_PORTLET}_grant-CLIENT_CREDENTIALS=true" \
	--data-urlencode "_${OAUTH_PORTLET}_clientCredentialUserId=${USER_ID}" \
	--data-urlencode "_${OAUTH_PORTLET}_clientCredentialUserName=test" \
	--data-urlencode "_${OAUTH_PORTLET}_clientId=${APP_CLIENT_ID}" \
	--data-urlencode "_${OAUTH_PORTLET}_clientSecret=${APP_CLIENT_SECRET}"

# Assign exactly the three scopes the migration scripts need. scopeAliases is
# a repeated form field — one --data-urlencode per scope. Do not grant every
# scope: the JWT encodes every granted alias into its scope claim, and an
# all-scopes grant produces a ~11 KB Authorization header that Tomcat's
# default maxHttpHeaderSize (8 KB) rejects with HTTP 400.
ASSIGN_ACTION="${PORTAL_BASE}/group/control_panel/manage?p_p_id=${OAUTH_PORTLET}&p_p_lifecycle=1&_${OAUTH_PORTLET}_jakarta.portlet.action=%2Foauth2_provider%2Fassign_scopes&p_auth=${P_AUTH}"

curl --silent --cookie-jar "$JAR" --cookie "$JAR" --location --output /dev/null \
	--request POST "$ASSIGN_ACTION" \
	--data-urlencode "_${OAUTH_PORTLET}_oAuth2ApplicationId=${APP_ID}" \
	--data-urlencode "_${OAUTH_PORTLET}_scopeAliases=c_property.everything.read" \
	--data-urlencode "_${OAUTH_PORTLET}_scopeAliases=c_accountnote.everything.write" \
	--data-urlencode "_${OAUTH_PORTLET}_scopeAliases=Liferay.Headless.Admin.User.everything.read"

echo "OAuth2 application ${APP_CLIENT_ID} (internal id ${APP_ID}) created and scoped."
```

### 5a. Scope set

The migration scripts need only these three aliases — keep the `scopeAliases` list above in sync as new scripts demand more:

- `c_property.everything.read` — pages `/o/c/properties` to find `koroneikiAccountKey` rows
- `c_accountnote.everything.write` — `PUT /o/c/accountnotes/by-external-reference-code/{key}` for upserts
- `Liferay.Headless.Admin.User.everything.read` — looks up Users by Koroneiki `uuid_` custom field

### 5b. Gotchas

- **`clientAuthenticationMethod=client_secret_post`** (the UI dropdown's "Client Secret Basic or Post" maps to this value). `client_secret_basic` alone is not an option.
- **`clientProfile=4`** = Headless Server. Other profile values from `ClientProfile.java`: 0=Web, 1=Native, 2=User Agent, 3=Other.
- **Two POSTs are required** to install a predictable client secret. The first POST (with `oAuth2ApplicationId=0`) creates the app but regenerates `clientSecret` regardless of what's submitted — Headless Server is a confidential client profile, and `UpdateOAuth2ApplicationMVCActionCommand` always rolls the secret on first save. The second POST, keyed by the new `oAuth2ApplicationId`, installs the predictable value. Submitting `clientId` is honored on both POSTs.
- **`scopeAliases` is a repeated field**, not comma-separated. One `--data-urlencode` per alias.
- **`p_auth` rotates per session.** Harvest it once after login (against the OAuth2 admin page so it's bound to the authenticated session) and reuse for every subsequent POST.
- **Locating the new app's id requires row-scoping** because the list page contains `oAuth2ApplicationId=N` URLs for every app, not just ours. The Python helper above scopes to the `<tr>` containing `APP_CLIENT_ID`.
- **First-run password reset / TOS** — on a freshly bootstrapped portal, the first interactive login historically forced a password change + TOS acceptance. On the local docker bundle this gate is bypassed, but if a future Liferay version reintroduces it the script exits with "Login failed — no ID cookie in jar". Drive `chrome-devtools` for that one login and then re-run this script.

Source paths if you need to track field names down:

- `modules/apps/oauth2-provider/oauth2-provider-web/src/main/java/com/liferay/oauth2/provider/web/internal/portlet/action/UpdateOAuth2ApplicationMVCActionCommand.java`
- `modules/apps/oauth2-provider/oauth2-provider-web/src/main/java/com/liferay/oauth2/provider/web/internal/portlet/action/AssignScopesMVCActionCommand.java`
- `modules/apps/oauth2-provider/oauth2-provider-api/src/main/java/com/liferay/oauth2/provider/constants/ClientProfile.java`

### 5c. Verify

Token issuance alone is not enough — an over-scoped app still issues tokens, but Tomcat rejects the resulting request. Probe a full round-trip: fetch a token, then use it against `/o/c/properties` and confirm the response is JSON (not Tomcat's HTML error page):

```bash
TOKEN=$(curl \
	--data "client_id=liferay-one-migration" \
	--data "client_secret=liferay-one-migration-secret" \
	--data "grant_type=client_credentials" \
	--silent \
	--url "http://localhost:8080/o/oauth2/token" \
| jq -r .access_token)

echo "Token length: ${#TOKEN}"

curl \
	--header "Authorization: Bearer ${TOKEN}" \
	--silent \
	--write-out "\nHTTP: %{http_code}\n" \
	--url "http://localhost:8080/o/c/properties?pageSize=1" \
| head -c 400
```

Healthy output: token length well under 2000 chars, `HTTP: 200`, and a JSON body containing `items` / `totalCount`. If the body is HTML starting with `<!doctype html>` and `HTTP: 400`, the JWT is over Tomcat's `maxHttpHeaderSize` — go back to step 5e and trim the scope set. Report the token's `expires_in` from the issuance response so the user knows the TTL.
