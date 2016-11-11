echo "Running menu ui, listening on port 8181"
docker run --name wfd-ui -p 8181:8181 -p 9181:9181 --env-file ~/ibm-cloud-architecture/refarch-cloudnative-container-utils/env/env-eureka-only-local -d wfd-ui:latest
