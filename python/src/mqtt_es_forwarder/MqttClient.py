import paho.mqtt.client as mqtt

class MqttClient(object):

    def __init__(self, message_callback, topic_name = "test/topic"):
        self.topic_name = topic_name
        self.message_callback = message_callback
        self.initialise_paho_client()
        print "MQTT client initialised"

    def initialise_paho_client(self):
        self.paho_client = mqtt.Client()
        self.paho_client.on_connect = self.on_connect
        self.paho_client.on_message = self.on_message
        self.paho_client.connect("mqttbroker", 1883, 60)
        self.paho_client.subscribe(self.topic_name, 1)
        self.paho_client.loop_start()

    def on_connect(self, *args, **kwargs):
        print "Connected"

    def on_message(self, client_data, user_data, message):
        print "Received message:", str(message)
        self.message_callback(str(message.payload))

    def close(self):
        if self.paho_client:
            try: self.paho_client.unsubscribe(self.topic_name)
            except: print "Unable to unsubscribe"
            try: self.paho_client.loop_stop()
            except: print "Unable to stop loop"
            try: self.paho_client.disconnect()
            except: print "Unable to disconnect"
