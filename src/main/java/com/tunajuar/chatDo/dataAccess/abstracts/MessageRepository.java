package com.tunajuar.chatDo.dataAccess.abstracts;

import com.tunajuar.chatDo.entities.concretes.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, Object> {
    Message findTopByReceiverOrderByMessageIdAsc(String receiver);

    List<Message> findBySenderAndReceiver(String sender, String receiver);

    List<Message> findByReceiver(String receiver);
}
