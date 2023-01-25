package com.tunajuar.chatDo.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "contacts")
public class Contact {
    @Id
    private ObjectId id;

    @Indexed
    @Field("user_name")
    private String userName;

    @Field("receiver_type")
    private ReceiverType receiverType;

    @Field("contact_name")
    private String contactName;

    public Contact(String userName, ReceiverType receiverType, String contactName) {
        this.userName = userName;
        this.receiverType = receiverType;
        this.contactName = contactName;
    }
}
