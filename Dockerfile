FROM gradle:4.10.0-jdk8-alpine AS build

COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle build --no-daemon


FROM openjdk:8-jre-alpine

ENV APPLICATION_USER user
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY --from=build /src/build/libs/brief-all.jar /app/brief-all.jar
WORKDIR /app

CMD ["java", "-jar", "brief-all.jar"]
