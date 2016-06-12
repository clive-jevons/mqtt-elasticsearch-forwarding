import java.util.UUID

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence

String tmpDir = System.getProperty("java.io.tmpdir")
MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence("${tmpDir}/mqttpublisher")

Thread.sleep 30000

MqttClient client = new MqttClient("tcp://mqttbroker:1883",
  "d:groovy:prd${UUID.randomUUID().toString().substring(0, 5)}", dataStore)
MqttConnectOptions connectOptions = new MqttConnectOptions(cleanSession: true)
client.connect(connectOptions)

Boolean running =true
try {
  while (running) {
    MqttMessage message = new MqttMessage("test-${UUID.randomUUID().toString()}".getBytes())
    message.qos = 0
    message.retained = false
    client.publish("test/topic", message)
    Thread.sleep(10)
  }
} finally {
  try { client.disconnect() } catch (Exception e) {}
  try { client.close() } catch (Exception e) {}
  println "Client disconnected."
}
