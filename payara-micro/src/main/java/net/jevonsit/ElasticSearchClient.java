package net.jevonsit;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Singleton
public class ElasticSearchClient {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchClient.class);

    TransportClient transportClient;

    @PostConstruct
    public void intialise() {
        try {
            transportClient = new TransportClient.Builder().settings(
                Settings.settingsBuilder()
                        .put("client.transport.sniff", true)
                        .put("cluster.name", "elasticsearch")
                        .build())
                                                           .build()
            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch"), 9300));
        } catch (UnknownHostException e) {
            LOG.error("Unable to initialise ES transport client.", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (transportClient != null) {
            transportClient.close();
        }
    }

    @Asynchronous
    public void publish(String message) {
        LOG.info("Publishing message: " + message);
        IndexRequest indexRequest = new IndexRequest("clientevents", "clientevent", UUID.randomUUID().toString());
        indexRequest.source("the_message", message);
        transportClient.index(indexRequest);
    }

}
