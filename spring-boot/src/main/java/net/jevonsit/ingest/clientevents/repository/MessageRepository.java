package net.jevonsit.ingest.clientevents.repository;

import net.jevonsit.ingest.clientevents.entities.Message;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface MessageRepository extends ElasticsearchRepository<Message, String> {

}
