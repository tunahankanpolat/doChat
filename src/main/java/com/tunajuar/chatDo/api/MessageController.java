package com.tunajuar.chatDo.api;

import com.tunajuar.chatDo.business.abstracts.MessageService;
import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Contact;
import com.tunajuar.chatDo.entities.concretes.Message;
import com.tunajuar.chatDo.entities.concretes.ReceiverType;
import com.tunajuar.chatDo.entities.dtos.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/message")
public class MessageController {
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/do-chat")
    public Result sendMessage(@Payload MessageDto message){
        if(message.getReceiverType().compareTo(ReceiverType.USER) == 0){
            messagingTemplate.convertAndSend("/chat/"+message.getReceiver()+"/"+message.getSender(),message);
            messageService.sendMessage(message);
        }else if(message.getReceiverType().compareTo(ReceiverType.GROUP) == 0){
            messagingTemplate.convertAndSend("/chat/"+message.getReceiver(),message);
            messageService.sendMessage(message);
        }

        return this.messageService.sendMessage(message);
    }

    @GetMapping("/get-messages")
    public DataResult<List<Message>> getGroupMessages(@RequestBody Contact contact){
        return this.messageService.getMessages(contact);
    }
}
