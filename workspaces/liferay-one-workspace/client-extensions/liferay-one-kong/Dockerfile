FROM kong:3.9

COPY kong/kong.yml /kong/declarative/kong.yml

ENV KONG_DATABASE="off"
ENV KONG_DECLARATIVE_CONFIG="/kong/declarative/kong.yml"

# Kong's nginx_worker_processes default is "auto", which resolves to the core
# count the kernel reports rather than the container's CPU allocation. A CPU limit
# is a CFS quota, so it throttles CPU time without changing the visible core count,
# which means "auto" scales with the size of the node the pod lands on and each
# worker carries its own Lua VM. Raising the LCP.json cpu value does not help. Left
# unpinned, a large enough node exhausts the memory limit and the OOM killer
# restarts workers in a loop that never reaches ready. One worker is ample for this
# proxy's traffic, and it makes the memory footprint independent of the node.

ENV KONG_NGINX_WORKER_PROCESSES="1"

# Kong's status_listen default is 127.0.0.1:8007, which is both loopback-only and
# a different port, so this is required for the compose healthcheck and the
# LCP.json probes to reach /status/ready.

ENV KONG_STATUS_LISTEN="0.0.0.0:8100"