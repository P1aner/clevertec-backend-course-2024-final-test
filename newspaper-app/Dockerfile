FROM openjdk:21-jdk-slim

WORKDIR /images/app

COPY newspaper-app/build/libs/newspaper-app-0.0.1.jar ./app.jar

CMD ["java","-jar","app.jar"]