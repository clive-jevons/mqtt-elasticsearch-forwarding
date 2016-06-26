package net.jevonsit.ingest.clientevents.service;

import net.jevonsit.ingest.clientevents.entities.Message;
import net.jevonsit.ingest.clientevents.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    public Message save(String text) {
        return messageRepository.save(new Message(text));
    }
}
