# Stage 1: Build the application using Gradle
FROM gradle:8.10.1-jdk17 AS builder

# Set the working directory inside the builder container
WORKDIR /app

# Copy all files into the builder container
COPY . .

# Build the application, excluding tests for faster build time
RUN gradle build -x test

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the final container
WORKDIR /app

# Copy the JAR file built in the first stage into the final container
COPY --from=builder /app/build/libs/office-reservation-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
