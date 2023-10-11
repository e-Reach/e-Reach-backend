FROM maven:3.8.7 AS build
COPY . .
RUN mvn package -DskipTests

FROM openjdk:19-jdk-alpine
COPY --from=build /target/e-Reach-0.0.1-SNAPSHOT.jar e-Reach.jar
EXPOSE 8080

CMD ["java", "-jar", "e-Reach.jar"]