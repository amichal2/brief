FROM openjdk:8-jre-alpine

ENV APPLICATION_USER user
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY ./build/libs/brief-all.jar /app/brief-all.jar
WORKDIR /app

CMD ["java", "-jar", "brief-all.jar"]
