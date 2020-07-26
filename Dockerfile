FROM maven:3.6-jdk-8-slim
VOLUME /tmp
ADD target/heremap-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]