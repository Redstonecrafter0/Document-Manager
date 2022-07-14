FROM eclipse-temurin:17-jre-alpine

RUN mkdir /app
RUN mkdir /data
RUN mkdir /secrets

RUN apk upgrade --update-cache --available
RUN apk add openssl
RUN rm -rf /var/cache/apk/*

WORKDIR /data

COPY Server/build/libs/Server*.jar ./document-manager.jar

CMD ["java", "-jar", "/app/document-manager.jar"]
