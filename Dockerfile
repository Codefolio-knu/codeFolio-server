FROM gradle:8.8-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]
