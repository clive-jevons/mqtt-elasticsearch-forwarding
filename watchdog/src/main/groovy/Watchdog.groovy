import groovy.json.JsonSlurper

class Watchdog {

  public static void main(String[] args) {
    Watchdog app = new Watchdog()
  }

  JsonSlurper slurper = new JsonSlurper()
  def startTime

  def Watchdog() {
    println "Watchdog starting ..."
    while (true) {
      try {
        "http://elasticsearch:9200/clientevents/clientevent/_count".toURL().withReader { reader ->
          def json = slurper.parse(reader)
          if (!startTime) startTime = new Date()
          long timediff = (new Date().time - startTime.time) / 1000
          println "${new Date()} Count after ${timediff} seconds: ${json.count}"
        }
      } catch (Exception e) {
        println "${new Date()} Problem while getting count data: ${e.getMessage()}"
      }
      Thread.sleep(1000)
    }
  }

}
