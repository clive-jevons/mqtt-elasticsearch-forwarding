import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ElasticClient implements java.io.Closeable {

  TransportClient elasticClient
  ExecutorService executorService = Executors.newCachedThreadPool()

  def ElasticClient() {
    println "Creating elasticsearch elasticClient"
    elasticClient = TransportClient.builder().settings(Settings.settingsBuilder {
      client.transport.sniff = true
      cluster.name = 'elasticsearch'
    }).build()
    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch"), 9300))
  }

  def addToIndex(String topic, String message) {
    executorService.submit {
      println "Creating index for ${topic} / ${message}"
      try {
        elasticClient.index {
          index "clientevents"
          type "clientevent"
          id "${UUID.randomUUID().toString()}"
          source {
            the_message = message
            the_topic = topic
            test = "Test string"
          }
        }
      } catch (Exception e) {
        e.printStackTrace()
      }
    }
    println "Creation of index for ${topic} / ${message} queued ..."
  }

  void close() throws java.io.IOException {
    try {
      elasticClient.close()
    } catch (Exception e) {
      e.printStackTrace()
    }
  }

}
