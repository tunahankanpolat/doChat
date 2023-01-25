package com.tunajuar.chatDo.dataAccess.abstracts;

import com.tunajuar.chatDo.entities.concretes.GroupUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUserRepository extends MongoRepository<GroupUser, Object> {
    List<GroupUser> findByGroupName(String groupName);
    List<GroupUser> findByGroupNameAndUserName (String groupName, String userName);
}
