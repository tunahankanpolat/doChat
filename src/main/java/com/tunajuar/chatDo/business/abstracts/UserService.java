package com.tunajuar.chatDo.business.abstracts;

import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.User;
import com.tunajuar.chatDo.entities.dtos.UserDto;

public interface UserService {
    Result add(User user);

    Result delete(String userName);

    DataResult<User> getUser(String userName);

    Result signUp(UserDto userDto);

    Result signIn(String userName, String password);
}
