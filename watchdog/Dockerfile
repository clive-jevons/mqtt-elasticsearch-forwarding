FROM groovy-base

ENV PATH /opt/groovy/bin:${PATH}

ADD build/distributions/watchdog.tar /src/
WORKDIR /src/watchdog

ENTRYPOINT ["sh", "-c", "/src/watchdog/bin/watchdog"]
