package com.tunajuar.chatDo.dataAccess.abstracts;

import com.tunajuar.chatDo.entities.concretes.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contact, Object> {
    Contact findByUserNameAndContactName(String userName, String contactName);

    List<Contact> findByUserName(String userName);
}
