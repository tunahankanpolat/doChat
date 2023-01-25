package com.tunajuar.chatDo.entities.concretes;

import com.tunajuar.chatDo.entities.dtos.MessageDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "messages")
public class Message {
    @Indexed
    @Field("sender")
    private String sender;

    @Indexed
    @Field("receiver")
    private String receiver;

    @Field("message")
    private String message;

    @Field("message_id")
    private Long messageId;

    @Field("receiver_type")
    private ReceiverType receiverType;

    @Field("message_type")
    private MessageType messageType;

    @Field("timestamp")
    private Date timestamp;

    public Message(MessageDto messageDto) {
        this.sender = messageDto.getSender();
        this.receiver = messageDto.getReceiver();
        this.message = messageDto.getMessage();
        this.receiverType = messageDto.getReceiverType();
        this.messageType = messageDto.getMessageType();
        this.timestamp = new Date();
    }
}
