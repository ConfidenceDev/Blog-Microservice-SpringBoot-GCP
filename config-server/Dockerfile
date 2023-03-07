FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} configsvc.jar

ENTRYPOINT ["java", "-jar", "/configsvc.jar"]

EXPOSE 9296