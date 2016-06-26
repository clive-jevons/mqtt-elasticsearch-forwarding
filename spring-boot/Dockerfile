#FROM alpine:latest
#RUN apk --update add openjdk7-jre
#CMD ["/usr/bin/java", "-version"]

FROM lwieske/java-8

RUN apk update && apk upgrade && apk add curl bash

COPY build/libs/cs-ingest-clientevents-0.0.1-SNAPSHOT.jar /cs-ingest-clientevents-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "cs-ingest-clientevents-0.0.1-SNAPSHOT.jar"]