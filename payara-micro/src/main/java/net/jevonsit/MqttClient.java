package net.jevonsit;

import javax.ejb.Stateless;

@Stateless
public class MqttClient {

    public static interface MessageCallback {
        void received(String message);
    }

    private MessageCallback messageCallback;

    public void setCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

}
