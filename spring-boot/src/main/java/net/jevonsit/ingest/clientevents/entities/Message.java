package net.jevonsit.ingest.clientevents.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "message", shards = 1, replicas = 0, refreshInterval = "-1")
public class Message {

    @Id
    private String id;

    String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
