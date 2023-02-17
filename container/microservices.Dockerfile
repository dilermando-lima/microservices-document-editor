FROM core_parent_with_gradle AS build

ARG NAME_PROJECT
ARG SOURCE_APP_PATH

WORKDIR /app

COPY ${SOURCE_APP_PATH} ${NAME_PROJECT}

RUN echo " include ':${NAME_PROJECT}';  " >> settings.gradle

RUN apk update && apk add gcompat binutils

RUN ./gradlew :${NAME_PROJECT}:clean :${NAME_PROJECT}:build

RUN jlink \
    --module-path /opt/java/openjdk/jmods \
    --add-modules java.base,java.logging,java.desktop,java.management,java.naming,java.security.jgss,java.prefs,java.sql,java.xml,java.security.sasl,java.instrument \
    --output jre-custom \
    --strip-debug \
    --no-header-files \
    --no-man-pages \
    --compress 2

# reduce a bit more docker size (-4MB)
RUN strip -p --strip-unneeded jre-custom/lib/server/libjvm.so && \
   find jre-custom -name '*.so' | xargs -i strip -p --strip-unneeded {}

FROM alpine:latest

ARG PORT_APP
ARG NAME_PROJECT
ARG JVM_ARGS

WORKDIR /deployment

COPY --from=build /app/jre-custom jre-custom/
COPY --from=build /app/${NAME_PROJECT}/build/libs/*.jar ${NAME_PROJECT}.jar

ENV ENV_JVM_ARGS ${JVM_ARGS}
ENV ENV_NAME_PROJECT ${NAME_PROJECT}
ENV ENV_PORT_APP ${PORT_APP}

CMD jre-custom/bin/java ${ENV_JVM_ARGS} -jar ${ENV_NAME_PROJECT}.jar
# CMD ["sleep","infinity"]
EXPOSE ${ENV_PORT_APP}
