FROM openjdk:21-slim-bullseye

COPY target/*.jar app.jar

ENV JAVA_OPTS="-Xms128m -Xmx256m"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
