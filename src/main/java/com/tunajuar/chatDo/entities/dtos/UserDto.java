package com.tunajuar.chatDo.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String picUrl;
}
