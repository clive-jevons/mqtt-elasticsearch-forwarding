package net.jevonsit.ingest.clientevents.configuration;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@PropertySource(value = "classpath:elasticsearch.properties")
public class ElasticSearchConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConfiguration.class);

    @Inject
    private Environment environment;

    @Bean
    public Client client() {
        TransportClient client = TransportClient.builder().build();
        TransportAddress address = null;
        try {
            address = new InetSocketTransportAddress(
                InetAddress.getByName(environment.getProperty("elasticsearch.host")),
                Integer.parseInt(environment.getProperty("elasticsearch.port")));
        } catch (UnknownHostException e) {
            LOG.error("Unable to initialise ES transport client.", e);
        }
        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }
}
