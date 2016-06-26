package net.jevonsit.ingest.clientevents.inboundadapter;

import net.jevonsit.ingest.clientevents.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import javax.inject.Inject;
import java.util.UUID;


@Configuration
@PropertySource(value = "classpath:mqtt.properties")
public class MqttInputChannelAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MqttInputChannelAdapter.class);

    @Inject
    private Environment environment;

    @Inject
    private MessageService messageService;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(environment.getProperty("mqtt.address"),
                "SpringBoot:" + UUID.randomUUID().toString().substring(0, 5),
                environment.getProperty("mqtt.topic"));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                if (LOG.isDebugEnabled()) LOG.debug("--->" + message.getPayload());
                messageService.save(message.getPayload().toString());
            }
        };
    }
}
