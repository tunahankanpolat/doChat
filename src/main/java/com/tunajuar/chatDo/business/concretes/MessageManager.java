package com.tunajuar.chatDo.business.concretes;

import com.tunajuar.chatDo.business.abstracts.MessageService;
import com.tunajuar.chatDo.core.utilities.result.*;
import com.tunajuar.chatDo.dataAccess.abstracts.ContactRepository;
import com.tunajuar.chatDo.dataAccess.abstracts.MessageRepository;
import com.tunajuar.chatDo.entities.concretes.Contact;
import com.tunajuar.chatDo.entities.concretes.Message;
import com.tunajuar.chatDo.entities.concretes.ReceiverType;
import com.tunajuar.chatDo.entities.dtos.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageManager implements MessageService {
    private MessageRepository messageRepository;
    private ContactRepository contactRepository;

    @Autowired
    public MessageManager(MessageRepository messageRepository, ContactRepository contactRepository) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public Result sendMessage(MessageDto messageDto) {
        if(hasContact(messageDto.getSender(),messageDto.getReceiver())){
            Message messageWithMaxMessageId = this.messageRepository.findTopByReceiverOrderByMessageIdAsc(messageDto.getReceiver());
            Message messageForAdd = new Message(messageDto);
            if(messageWithMaxMessageId != null)
                messageForAdd.setMessageId(messageWithMaxMessageId.getMessageId() + 1);
            else
                messageForAdd.setMessageId(0L);
            this.messageRepository.insert(messageForAdd);
            return new SuccessResult();
        }
        else
            return new ErrorResult("Sender has not receiver contact.");
    }

    @Override
    public DataResult<List<Message>> getMessages(Contact contact) {
        if(contact.getReceiverType().compareTo(ReceiverType.USER) == 0)
            return new SuccessDataResult<>(this.messageRepository.findBySenderAndReceiver(contact.getUserName(),contact.getContactName()));
        else if (contact.getReceiverType().compareTo(ReceiverType.GROUP) == 0)
            return new SuccessDataResult<>(this.messageRepository.findByReceiver(contact.getContactName()));
        else
            return new ErrorDataResult<>("Receiver Type is not valid");
    }

    private boolean hasContact(String sender, String receiver){
        Contact contact = this.contactRepository.findByUserNameAndContactName(sender,receiver);
        if(contact != null) return true;
        else return false;
    }
}
