FROM openjdk:21

WORKDIR app

COPY build/libs/newspaper-0.0.1.jar ./app.jar

CMD ["java","-jar","app.jar"]