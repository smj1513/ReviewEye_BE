# Use an official Java runtime as a parent image
FROM azul/zulu-openjdk:21

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the target directory
COPY build/libs/reviewEye.jar revieweye.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "revieweye.jar"]
