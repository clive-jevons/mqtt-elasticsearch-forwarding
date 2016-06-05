# The Application

from MqttClient import MqttClient
from ElasticSearchClient import ElasticSearchClient
import time, threading

class OutOfRetriesException(Exception):

    def __init__(self, message):
        super(OutOfRetriesException, self).__init__(message)

class App:

    def __init__(self):
        def connect_mqtt():
            self.mqtt_client = MqttClient(self.forward_message)
        self.do_with_retries(connect_mqtt, 'connect to mqtt')
        def connect_elastic_search():
            self.elastic_search_client = ElasticSearchClient()
        self.do_with_retries(connect_elastic_search, 'connect to elastic search')
        print "Application initialised"
        try:
            while True:
                time.sleep(5)
        finally:
            self.mqtt_client.close()
            self.elastic_search_client.close()

    def do_with_retries(self, action, description):
        success = False
        retry_count = 20
        while not success and retry_count > 0:
            try:
                action()
                success = True
            except:
                retry_count -= 1
                print "Failed to %s. Retry count: %d" % (description, retry_count)
                time.sleep(1)
        if not success:
            raise OutOfRetriesException("Unable to %s" % description)

    def run(self):
        print "Application started"

    def forward_message(self, mqtt_message):
        def publish_to_elastic():
            self.elastic_search_client.publish(mqtt_message)
        threading.Thread(target = publish_to_elastic).start()
