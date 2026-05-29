FROM gcr.io/distroless/java25-debian13

COPY target/shorter-url-service.jar app.jar

EXPOSE 8081

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "/app.jar"]
