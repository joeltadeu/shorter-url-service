FROM openjdk:16-alpine3.13
ADD target/shorter-url-service.jar shorter-url-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/shorter-url-service.jar"]
