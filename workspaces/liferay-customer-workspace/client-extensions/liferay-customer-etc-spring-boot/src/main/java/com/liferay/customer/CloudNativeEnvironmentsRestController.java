/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.customer.constants.CloudNativeEnvironmentConstants;
import com.liferay.customer.exception.AddOnsUnavailableException;
import com.liferay.customer.service.CloudNativeEnvironmentService;
import com.liferay.customer.service.OfflineActivationBundleService;
import com.liferay.customer.service.ProvisioningService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import java.net.HttpURLConnection;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * @author Amos Fong
 */
@RequestMapping("/cloud-native-environments")
@RestController
public class CloudNativeEnvironmentsRestController extends BaseRestController {

	@PostMapping("/offline-activation")
	public ResponseEntity<String> postOfflineActivation(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		try {
			JSONObject jsonObject = new JSONObject(json);

			String activationCode = jsonObject.optString("activationCode");
			String jwtToken = jsonObject.optString(
				"token"
			).replaceAll(
				"\\s", ""
			);

			if (Validator.isNull(activationCode) ||
				Validator.isNull(jwtToken)) {

				return new ResponseEntity<>(
					"MISSING_PARAMETERS", HttpStatus.BAD_REQUEST);
			}

			JSONObject cloudNativeEnvironmentJSONObject =
				_cloudNativeEnvironmentService.fetchCloudNativeEnvironment(
					"Bearer " + jwt.getTokenValue(), activationCode);

			if (cloudNativeEnvironmentJSONObject == null) {
				return new ResponseEntity<>(
					"ACTIVATION_CODE_NOT_FOUND", HttpStatus.NOT_FOUND);
			}

			JWTClaimsSet jwtClaimsSet = null;

			try {
				jwtClaimsSet = JWTParser.parse(
					jwtToken
				).getJWTClaimsSet();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to parse the offline activation token",
						exception);
				}

				return new ResponseEntity<>(
					"INVALID_TOKEN", HttpStatus.BAD_REQUEST);
			}

			String environmentId = jwtClaimsSet.getStringClaim("environmentID");

			if (Validator.isNull(environmentId)) {
				return new ResponseEntity<>(
					"INVALID_TOKEN", HttpStatus.BAD_REQUEST);
			}

			HttpResponse<String> httpResponse =
				_provisioningService.activateCloudEnvironment(
					environmentId, activationCode, jwtToken);

			int statusCode = httpResponse.statusCode();

			if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
				_log.error(
					StringBundler.concat(
						"Unable to activate environment ", environmentId, ": ",
						statusCode, " ", httpResponse.body()));

				return new ResponseEntity<>(
					"ACTIVATION_FAILED", HttpStatus.valueOf(statusCode));
			}

			_cloudNativeEnvironmentService.updateCloudNativeEnvironment(
				_getAuthorization(), cloudNativeEnvironmentJSONObject,
				CloudNativeEnvironmentConstants.ACTIVATION_METHOD_OFFLINE,
				environmentId, jwtClaimsSet.getStringClaim("environmentName"));

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Activated offline environment ", environmentId,
						" for activation code ", activationCode));
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(
				"UNEXPECTED_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/offline-activation-bundle")
	public ResponseEntity<StreamingResponseBody> postOfflineActivationBundle(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		try {
			JSONObject jsonObject = new JSONObject(json);

			String dxpVersion = jsonObject.optString("dxpVersion");
			String environmentId = jsonObject.optString("environmentId");

			if (Validator.isNull(dxpVersion) ||
				Validator.isNull(environmentId)) {

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			JSONObject cloudNativeEnvironmentJSONObject =
				_cloudNativeEnvironmentService.
					fetchCloudNativeEnvironmentByEnvironmentId(
						"Bearer " + jwt.getTokenValue(), environmentId);

			if (cloudNativeEnvironmentJSONObject == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			Path path = _offlineActivationBundleService.createBundle(
				environmentId, dxpVersion);

			HttpHeaders httpHeaders = new HttpHeaders();

			httpHeaders.setContentDisposition(
				ContentDisposition.attachment(
				).filename(
					StringBundler.concat(
						environmentId, "-", dxpVersion,
						"-offline-activation-bundle.zip")
				).build());
			httpHeaders.setContentLength(Files.size(path));
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			StreamingResponseBody streamingResponseBody = outputStream -> {
				try {
					Files.copy(path, outputStream);
				}
				finally {
					Files.deleteIfExists(path);
				}
			};

			return new ResponseEntity<>(
				streamingResponseBody, httpHeaders, HttpStatus.OK);
		}
		catch (AddOnsUnavailableException addOnsUnavailableException) {
			if (_log.isWarnEnabled()) {
				_log.warn(addOnsUnavailableException.getMessage());
			}

			return _getErrorResponseEntity(
				"ADD_ONS_UNAVAILABLE", HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private ResponseEntity<StreamingResponseBody> _getErrorResponseEntity(
		String error, HttpStatus httpStatus) {

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setContentType(MediaType.TEXT_PLAIN);

		return new ResponseEntity<>(
			outputStream -> outputStream.write(
				error.getBytes(StandardCharsets.UTF_8)),
			httpHeaders, httpStatus);
	}

	private static final Log _log = LogFactory.getLog(
		CloudNativeEnvironmentsRestController.class);

	@Autowired
	private CloudNativeEnvironmentService _cloudNativeEnvironmentService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private OfflineActivationBundleService _offlineActivationBundleService;

	@Autowired
	private ProvisioningService _provisioningService;

}