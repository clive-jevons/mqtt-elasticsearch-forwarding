FROM groovy-base

ENV PATH /opt/groovy/bin:${PATH}

ADD build/distributions/producer.tar /src/
WORKDIR /src/producer

ENTRYPOINT ["sh", "-c", "/src/producer/bin/producer"]
