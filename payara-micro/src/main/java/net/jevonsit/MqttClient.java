package net.jevonsit;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import java.util.UUID;

import static java.lang.String.format;

@Singleton
public class MqttClient {

    private static final Logger LOG = LoggerFactory.getLogger(MqttClient.class);
    private MqttAsyncClient mqttClient;

    public static interface MessageCallback {
        void received(String message);
    }

    private MessageCallback messageCallback;

    public void setCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    @PostConstruct
    public void initialise() {
        try {
            mqttClient = new MqttAsyncClient("tcp://mqttbroker:1883",
                "payaramicro:" + UUID.randomUUID().toString().substring(0, 5));
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    LOG.warn("Connection lost.", cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    LOG.debug(format("Received message on topic '%s':%n\t%s", topic, message));
                    if (messageCallback != null) {
                        messageCallback.received(new String(message.getPayload()));
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    LOG.debug("Delivery complete: " + token);
                }
            });
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            mqttClient.connect(connectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    try {
                        mqttClient.subscribe("test/topic", 1);
                    } catch (MqttException e) {
                        LOG.error("Unable to subscribe.", e);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    LOG.error("Unable to connect to mqtt broker.", exception);
                }
            });
        } catch (MqttException e) {
            LOG.error("Unable to initialise mqtt async client.", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (mqttClient != null) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                LOG.error("Problem disconnecting mqtt async client.", e);
            }
            try {
                mqttClient.close();
            } catch (MqttException e) {
                LOG.error("Problem closing mqtt async client.", e);
            }
        }
    }

}
