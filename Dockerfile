FROM java:8

VOLUME /tmp

ADD ./target/shorterurl-1.0.0-SNAPSHOT.jar shorterurl-1.0.0-SNAPSHOT.jar 

RUN bash -c 'touch /shorterurl-1.0.0-SNAPSHOT.jar'

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shorterurl-1.0.0-SNAPSHOT.jar"]
