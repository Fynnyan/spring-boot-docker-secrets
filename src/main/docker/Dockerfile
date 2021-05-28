FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine

RUN adduser -s /bin/sh -u 1000 -D java java
USER java

# Arguments passed in
ARG jarName

# The fabric9io creates a build directory, and the jar is copied in a subdirectory, mavne.
WORKDIR /app
COPY maven/$jarName.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]

