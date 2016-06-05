FROM payara/micro

COPY build/libs/payara-micro-consumer.war /payara-micro-consumer.war

ENTRYPOINT ["java", "-jar", "payara-micro.jar", "--deploy", "/payara-micro-consumer.war"]
