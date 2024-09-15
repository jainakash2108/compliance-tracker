# Use the Eclipse Temurin JDK 21 (Alpine) as the base image
FROM eclipse-temurin:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Expose port 8080 for the application
EXPOSE 8080

# Copy the built JAR file from the Gradle build directory to the container
COPY build/libs/compliance-tracker-*.jar /app/app.jar

# Define the entry point for running the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
