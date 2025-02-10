# Use a specific OpenJDK 17 base image
#FROM openjdk:17-jdk-slim
#docker file

FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the target/build/libs folder into the container
# Replace "my-app.jar" with the name of your JAR file
COPY build/libs/my-user-service.jar app.jar

# Expose the port your Spring Boot application is running on (default is 8080)
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
