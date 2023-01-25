package com.tunajuar.chatDo.entities.dtos;

import com.tunajuar.chatDo.entities.concretes.MessageType;
import com.tunajuar.chatDo.entities.concretes.ReceiverType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String sender;
    private String receiver;
    private String message;
    private ReceiverType receiverType;
    private MessageType messageType;
}
