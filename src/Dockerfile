# Start with a base image containing Java runtime
FROM java:8

# Add Author info
LABEL maintainer="mari20201101@gmail.com"

# Add a volume to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
ADD ${JAR_FILE} docker1-springboot.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/docker1-springboot.jar"]
