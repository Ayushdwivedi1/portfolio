#!/bin/bash

# Navigate to the microservice directory
cd portfolio-microservice

# Set Java options for production
export JAVA_OPTS="-Xmx512m -Xms256m"

# Start the application
java $JAVA_OPTS -jar target/portfolio-0.0.1-SNAPSHOT.jar 