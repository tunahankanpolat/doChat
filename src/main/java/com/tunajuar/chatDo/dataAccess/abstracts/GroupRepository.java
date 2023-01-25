package com.tunajuar.chatDo.dataAccess.abstracts;

import com.tunajuar.chatDo.entities.concretes.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group,String> {
}
