FROM swaggerapi/swagger-ui
ENV BASE_URL=/swagger
ENV SWAGGER_JSON=/spec.yaml
VOLUME /api/
EXPOSE 8080