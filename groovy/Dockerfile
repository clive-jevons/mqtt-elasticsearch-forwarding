FROM groovy-base

ENV PATH /opt/groovy/bin:${PATH}

ADD build/distributions/groovy-consumer.tar /src/
WORKDIR /src/groovy-consumer

ENTRYPOINT ["sh", "-c", "/src/groovy-consumer/bin/groovy-consumer"]
