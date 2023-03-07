FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} articlesvc.jar

ENTRYPOINT ["java", "-jar", "/articlesvc.jar"]

EXPOSE 8082