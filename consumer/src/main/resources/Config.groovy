protocol = 'tcp'
host = 'mqttbroker'
//host = 'localhost'
port = '1883'
brokerAddress = "${protocol}://${host}:${port}"

topicName = 'test/topic'
qos = 1
