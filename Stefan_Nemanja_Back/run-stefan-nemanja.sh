#!/bin/bash

# Navigate to the model directory and build
cd ./model || { echo "Directory /model not found"; exit 1; }
mvn clean install || { echo "Failed to build model"; exit 1; }

# Navigate to the kjar directory and build
cd ../kjar || { echo "Directory ../kjar not found"; exit 1; }
mvn clean install || { echo "Failed to build kjar"; exit 1; }

# Navigate to the service directory and build
cd ../service || { echo "Directory ../service not found"; exit 1; }
mvn clean install || { echo "Failed to build service"; exit 1; }

# Run the Spring Boot project
mvn spring-boot:run || { echo "Failed to start Spring Boot application"; exit 1; }

# Exit successfully
exit 0

