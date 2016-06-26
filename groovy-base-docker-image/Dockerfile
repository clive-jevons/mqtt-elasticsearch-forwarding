FROM lwieske/java-8

RUN apk update && apk upgrade && apk add curl bash

ENV GROOVY_VERSION=2.4.5

RUN curl -s -L https://bintray.com/artifact/download/groovy/maven/apache-groovy-binary-${GROOVY_VERSION}.zip -o /groovy.zip && mkdir /opt/ && unzip -q -d /opt/ /groovy.zip && rm -rf /groovy.zip && mv /opt/groovy-*/ /opt/groovy/ && rm -rf /var/cache/apk/*
