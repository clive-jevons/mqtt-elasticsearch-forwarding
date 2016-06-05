@GrabResolver(name = 'jcenter', root = 'http://jcenter.bintray.com/')
@Grab(group='org.apache.activemq', module = 'artemis-jms-client', version='1.1.0')

import javax.jms.*
import org.apache.activemq.artemis.jms.client.*

def brokerUrl = 'tcp://localhost:61616'
def topic = 'test'

new ActiveMQTopicConnectionFactory(brokerUrl).createConnection().with {
  start()
  createSession(false, Session.AUTO_ACKNOWLEDGE).with {
    def message = createObjectMessage('Test')
    createPublisher(createTopic(topic)).publish(message)
  }
  close()
}
