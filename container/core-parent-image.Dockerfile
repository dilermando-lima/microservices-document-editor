FROM eclipse-temurin:17-jdk-alpine

WORKDIR app

COPY core/api-contracts/ api-contracts/
COPY core/api-core/ api-core/
COPY --chmod=+x gradlew gradlew
COPY gradle/ gradle/
COPY gradle.properties gradle.properties

RUN echo " include ':api-core'; include ':api-contracts'; " >> settings.gradle && echo '' > build.gradle

RUN ./gradlew clean build



