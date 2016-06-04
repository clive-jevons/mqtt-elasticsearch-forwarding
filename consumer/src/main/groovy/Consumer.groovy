class Consumer {

  ElasticClient elasticClient
  MqttClient mqttClient

  public static void main(String... args) {
    Consumer consumer = new Consumer()
  }

  def Consumer() {
    try {
      elasticClient = new ElasticClient()
      mqttClient = new MqttClient()

      elasticClient.withCloseable {
        mqttClient.withCloseable {

          mqttClient.setCallback { String topic, String message ->
            elasticClient.addToIndex(topic, message)
          }
          mqttClient.connectAndSubscribe()

          while (true) {
            Thread.sleep 500
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace()
    }
  }

}
