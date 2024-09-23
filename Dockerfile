FROM openjdk:17-jdk
COPY target/PiDevArctic-0.0.1-SNAPSHOT.jar .
COPY src/main/resources/data /data
COPY src/main/resources/config /config
EXPOSE 8080
ENTRYPOINT ["java","-jar", "PiDevArctic-0.0.1-SNAPSHOT.jar"]