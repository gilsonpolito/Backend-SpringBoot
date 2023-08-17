FROM alpine:latest as base

RUN apk update --no-cache &&\
    apk upgrade --no-cache &&\
    apk add --no-cache \
    openjdk8 \
    git \
    maven &&\
    git clone https://github.com/gilsonpolito/Backend-SpringBoot.git
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
ENV PATH="$JAVA_HOME/bin:${PATH}"
WORKDIR "Backend-SpringBoot"
RUN mvn clean install package

FROM alpine:latest
RUN apk update -- no-cache &&\
    apk upgrade --no-cache &&\
    apk add --no-cache openjdk8-jre &&\
    mkdir app 
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk-jre
ENV PATH="$JAVA_HOME/bin:${PATH}"
COPY --from=base /Backend-SpringBoot/target/Backend-SpringBoot-1.0-SNAPSHOT.jar /app
WORKDIR app
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","Backend-SpringBoot-1.0-SNAPSHOT.jar"]
