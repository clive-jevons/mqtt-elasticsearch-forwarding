package net.jevonsit;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class ElasticSearchClient {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchClient.class);

    public void publish(String message) {
        LOG.info("Publishing message: " + message);
    }

}
