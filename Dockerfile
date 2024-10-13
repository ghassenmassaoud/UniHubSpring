FROM openjdk:17-jdk

COPY target/CloudChaserBack.jar .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "CloudChaserBack.jar"]