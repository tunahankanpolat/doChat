package com.tunajuar.chatDo.business.concretes;

import com.tunajuar.chatDo.business.abstracts.GroupService;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.core.utilities.result.SuccessResult;
import com.tunajuar.chatDo.dataAccess.abstracts.ContactRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.GroupRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.GroupUserRepository;
import com.tunajuar.chatDo.entities.concretes.Contact;
import com.tunajuar.chatDo.entities.concretes.Group;
import com.tunajuar.chatDo.entities.concretes.GroupUser;
import com.tunajuar.chatDo.entities.concretes.ReceiverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupManager implements GroupService {
    private GroupRepository groupRepository;
    private ContactRepository contactRepository;
    private GroupUserRepository groupUserRepository;

    public GroupManager(GroupRepository groupRepository, ContactRepository contactRepository, GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.contactRepository = contactRepository;
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    public Result add(Group group) {
        this.groupRepository.insert(group);
        this.contactRepository.insert(new Contact(group.getFounder(), ReceiverType.GROUP,group.getGroupName()));
        this.groupUserRepository.insert(new GroupUser(group.getGroupName(), group.getFounder()));
        return new SuccessResult();
    }

    @Override
    public Result delete(String groupName) {
        Group group = this.groupRepository.findById(groupName).get();
        List<GroupUser> groupUsers = this.groupUserRepository.findByGroupName(groupName);
        this.groupRepository.delete(group);
        for (GroupUser groupUser:groupUsers){
            this.contactRepository.delete(this.contactRepository.findByUserNameAndContactName(groupUser.getUserName(),groupUser.getGroupName()));
            this.groupUserRepository.delete(groupUser);
        }
        return new SuccessResult();
    }
}
