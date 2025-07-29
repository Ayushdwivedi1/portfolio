#!/bin/bash

# Navigate to the microservice directory
cd portfolio-microservice

# Make Maven wrapper executable
chmod +x ./mvnw

# Clean and package the application
./mvnw clean package -DskipTests

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "Build completed successfully!"
    echo "JAR file location: target/portfolio-0.0.1-SNAPSHOT.jar"
else
    echo "Build failed!"
    exit 1
fi 