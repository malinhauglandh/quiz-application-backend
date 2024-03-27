# Build stage
FROM maven:3.8.2-openjdk-17 AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Package the application
RUN mvn -f pom.xml clean package -DskipTests

# Package stage
FROM openjdk:17
WORKDIR /app
# Copy the jar from the build stage to the /app directory
COPY --from=build /app/target/*.jar app.jar
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
