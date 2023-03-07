FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} cloudgatewaysvc.jar

ENTRYPOINT ["java", "-jar", "/cloudgatewaysvc.jar"]

EXPOSE 9090