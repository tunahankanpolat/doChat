package com.tunajuar.chatDo.entities.concretes;

import com.tunajuar.chatDo.entities.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @Indexed
    @Field("user_name")
    private String userName;

    @Field("name")
    private String name;

    @Field("surname")
    private String surname;

    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @Field("email")
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    @Encrypted
    @Field("password")
    private String password;

    @Field("pic_url")
    private String picUrl;

    public User(UserDto userDto){
        this.userName = userDto.getUserName();
        this.name = userDto.getName();
        this.surname = userDto.getSurname();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.picUrl = userDto.getPicUrl();
    }
}
