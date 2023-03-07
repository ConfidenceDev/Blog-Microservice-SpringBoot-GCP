FROM openjdk:14-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} creatorsvc.jar

ENTRYPOINT ["java", "-jar", "/creatorsvc.jar"]

EXPOSE 8081