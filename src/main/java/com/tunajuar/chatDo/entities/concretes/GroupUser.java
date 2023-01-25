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
@Document(collection = "group_users")
public class GroupUser {
    @Id
    private ObjectId id;

    @Indexed
    @Field("group_name")
    private String groupName;

    @Field("user_name")
    private String userName;

    public GroupUser(String groupName, String userName) {
        this.groupName = groupName;
        this.userName = userName;
    }
}
