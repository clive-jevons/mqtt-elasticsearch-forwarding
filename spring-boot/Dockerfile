FROM lwieske/java-8

RUN apk update && apk upgrade && apk add curl bash

COPY build/libs/spring-boot-ingest-0.0.1-SNAPSHOT.jar /spring-boot-ingest.jar

ENTRYPOINT ["java", "-jar", "spring-boot-ingest.jar"]
