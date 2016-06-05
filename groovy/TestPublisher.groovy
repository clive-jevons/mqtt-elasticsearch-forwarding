@Grab(group='org.eclipse.paho', module='org.eclipse.paho.client.mqttv3', version='1.0.2')

import groovy.json.*

import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence

String tmpDir = System.getProperty("java.io.tmpdir")
MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence("${tmpDir}/mqttpublisher")

def config = new ConfigSlurper().parse('file:./TestConfig.groovy'.toURL())
println "Config: ${config}"

def jsonBuilder = new JsonBuilder()
jsonBuilder.person {
  name 'one'
  age 1
}
String json = JsonOutput.prettyPrint(jsonBuilder.toString())

MqttClient client = new MqttClient(config.brokerAddress, "d:bmw:groovy:grv1", dataStore)
MqttConnectOptions connectOptions = new MqttConnectOptions(cleanSession: true)

println "Connecting to: ${config.brokerAddress}"
client.connect(connectOptions)


Boolean running =true
while (running) {

	String input =  System.console().readLine "input message (q for exit)"
	if(input=="q"){
		running=false
	}else{
		MqttMessage message = new MqttMessage(input.bytes)
		message.qos = config.qos
		message.retained = false
		client.publish(config.topicName, message)
		println "Message published to ${config.topicName}:\n${input}"
	}
	
}

client.disconnect()
println "Client disconnected."
