FROM maven:latest AS build
COPY . .
RUN mvn clean package -DSkipTests

FROM openjdk:20-jdk-slim
COPY --from=build /target/e-Reach-0.0.1-SNAPSHOT.jar e-Reach.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "e-Reach.jar"]