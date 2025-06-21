FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
EXPOSE 8005
ADD ./target/items-0.0.1-SNAPSHOT.jar items-server.jar

ENTRYPOINT ["java", "-jar", "items-server.jar"]