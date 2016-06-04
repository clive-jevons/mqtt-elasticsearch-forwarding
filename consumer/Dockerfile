FROM zeroturnaround/groovy

ENV PATH /opt/groovy/bin:${PATH}

ADD build/distributions/consumer.tar /src/
WORKDIR /src/consumer

ENTRYPOINT ["sh", "-c", "/src/consumer/bin/consumer"]
