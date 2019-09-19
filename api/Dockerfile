# Use an official Maven image (stage 1)
FROM maven:3.5-jdk-8-alpine AS builder

# Set the working directory
WORKDIR /builder

#Copy the current directory contents into the container
COPY . .

# Install any needed packages specified in pom.xml and src
RUN mvn dependency:resolve \
    && mvn package

# Use an official Openjdk image (stage 2)
FROM openjdk:8-jre-alpine

# Set the working directory
WORKDIR /api

# Copy from builder the jar
COPY --from=builder /builder/target/api-0.0.1-SNAPSHOT.jar .


# Run .jar when the container launches
CMD ["java", "-jar", "api-0.0.1-SNAPSHOT.jar"]
