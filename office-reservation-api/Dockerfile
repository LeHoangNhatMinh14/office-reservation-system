# Stage 1: Build the application using Gradle
FROM gradle:7.5.0-jdk17 AS builder

# Set the working directory inside the builder container
WORKDIR /opt/app

# Copy all files into the builder container
COPY . .

# Build the application, excluding tests for faster build time
RUN gradle build -x test

# Stage 2: Create the runtime image
FROM openjdk:17-jdk

# Set the working directory inside the final container
WORKDIR /opt/app

# Copy the JAR file built in the first stage into the final container
COPY --from=builder /opt/app/build/libs/office-reservation-system-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
