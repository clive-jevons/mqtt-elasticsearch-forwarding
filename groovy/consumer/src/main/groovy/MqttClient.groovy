import java.util.UUID;

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence

class MqttClient implements java.io.Closeable {

  String mqttHost
  String clientId
  int qualityOfService
  String topicName

  MqttAsyncClient client
  MqttConnectOptions connectOptions

  def MqttClient() {
    println "Setting up temporary MQTT storage"
    String tmpDir = System.getProperty("java.io.tmpdir")
    MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence("${tmpDir}/mqttconsumer")

    def config = new ConfigSlurper().parse(MqttClient.class.getClassLoader().getResource('Config.groovy'))
    println "Config: ${config}"

    mqttHost =  config.brokerAddress
    clientId =  "d:groovy:grv${UUID.randomUUID().toString().substring(0, 5)}"
    qualityOfService = config.qos
    topicName = config.topicName

    client = new MqttAsyncClient(mqttHost, clientId, dataStore)
    connectOptions = new MqttConnectOptions(cleanSession: true)
  }

  def setCallback(Closure closure) {
      client.callback = new MqttCallback() {
        void connectionLost(Throwable t) {
        }
        void deliveryComplete(IMqttDeliveryToken token) {
        }
        void messageArrived(String topic, MqttMessage message) {
          try {
            println "Received message on ${topic}:\n\t${message}"
            closure.call(topic, message.toString())
          } catch (Exception e) {
            e.printStackTrace()
          }
        }
      }
  }

  def connectAndSubscribe() {
    client.connect(connectOptions)

    int waitCount = 0
    while (!client.connected && waitCount++ < 10) {
      println "Wating for client to finish connecting ..."
      try {
        Thread.sleep 500
      } catch (InterruptedException e) {
        println "Interrupted"
      }
    }
    if (!client.connected) {
      throw new IllegalStateException("Unable to connect to MQTT broker in time.")
    }

    println "Subscribing to ${topicName}"
    client.subscribe(topicName, qualityOfService)
  }

  void close() throws IOException {
    client.disconnect()
  }

}
