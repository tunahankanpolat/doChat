package com.tunajuar.chatDo.business.concretes;

import com.tunajuar.chatDo.business.abstracts.ContactService;
import com.tunajuar.chatDo.business.abstracts.GroupService;
import com.tunajuar.chatDo.core.utilities.result.*;
import com.tunajuar.chatDo.dataAccess.abstracts.ContactRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.GroupRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.GroupUserRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.UserRepository;
import com.tunajuar.chatDo.entities.concretes.Contact;
import com.tunajuar.chatDo.entities.concretes.Group;
import com.tunajuar.chatDo.entities.concretes.GroupUser;
import com.tunajuar.chatDo.entities.concretes.ReceiverType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactManager implements ContactService {
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    private GroupUserRepository groupUserRepository;

    private GroupService groupService;

    public ContactManager(ContactRepository contactRepository, UserRepository userRepository, GroupRepository groupRepository, GroupUserRepository groupUserRepository, GroupService groupService) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.groupService = groupService;
    }

    @Override
    public Result add(Contact contact) {
        if(contact.getReceiverType() != null && contact.getReceiverType().compareTo(ReceiverType.USER) == 0){
            if(this.userRepository.findById(contact.getContactName()).isPresent()){
                if(this.contactRepository.findByUserNameAndContactName(contact.getUserName(),contact.getContactName()) == null){
                    this.contactRepository.insert(contact);
                    this.contactRepository.insert(new Contact(contact.getContactName(),contact.getReceiverType(),contact.getUserName()));
                    return new SuccessResult("User contact successfully Added!!");
                }else {
                    return new ErrorResult("User contact already exists");
                }
            }else
                return new ErrorResult("There is no user with this name!!");
        } else if (contact.getReceiverType() != null && contact.getReceiverType().compareTo(ReceiverType.GROUP) == 0){
            if(this.groupRepository.findById(contact.getContactName()).isPresent()){
                if(this.contactRepository.findByUserNameAndContactName(contact.getUserName(),contact.getContactName()) == null) {
                    this.contactRepository.insert(contact);
                    this.groupUserRepository.insert(new GroupUser(contact.getContactName(),contact.getUserName()));
                    return new SuccessResult("Group contact successfully Added!!");
                }else{
                    return new ErrorResult("Group contact already exists");
                }
            }else {
                this.groupService.add(new Group(contact.getContactName(),contact.getUserName()));
                return new SuccessResult("Group successfully Created!!");
            }
        }else
            return new ErrorResult("Choose Receiver Type!!");
    }

    @Override
    public Result delete(String userName, String contactName) {
        Contact contact1 = this.contactRepository.findByUserNameAndContactName(userName, contactName);
        if(contact1 != null){
            this.contactRepository.delete(contact1);
            if(contact1.getReceiverType().compareTo(ReceiverType.USER) == 0){
                Contact contact2 = this.contactRepository.findByUserNameAndContactName(contactName, userName);
                this.contactRepository.delete(contact2);
                return new SuccessResult("User contact successfully Deleted!!");
            } else {
                List<GroupUser> groupUser = this.groupUserRepository.findByGroupNameAndUserName(contactName,userName);
                //this.groupUserRepository.delete(groupUser);
                return new SuccessResult("Group contact successfully Deleted!!");
            }
        }else {
            return new ErrorResult("There is no contact with this name");
        }
    }

    @Override
    public DataResult<List<Contact>> getUserContacts(String userName) {
        return new SuccessDataResult<>(this.contactRepository.findByUserName(userName));
    }
}
