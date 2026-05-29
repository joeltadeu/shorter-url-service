FROM gcr.io/distroless/java25-debian13

ADD target/shorter-url-service.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
