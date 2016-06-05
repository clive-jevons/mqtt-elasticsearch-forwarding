package net.jevonsit;

import javax.annotation.PostConstruct;

import javax.ejb.Startup;
import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
@Startup
public class Forwarder {

    private final MqttClient mqttClient;
    private final ElasticSearchClient elasticSearchClient;

    @Inject
    public Forwarder(MqttClient mqttClient, ElasticSearchClient elasticSearchClient) {
        this.mqttClient = mqttClient;
        this.elasticSearchClient = elasticSearchClient;
    }

    @PostConstruct
    public void setup() {
        mqttClient.setCallback((String message) -> {
            elasticSearchClient.publish(message);
        });
    }

}
