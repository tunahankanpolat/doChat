package com.tunajuar.chatDo.api;

import com.tunajuar.chatDo.business.abstracts.UserService;
import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.User;
import com.tunajuar.chatDo.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody User user){
        return this.userService.add(user);
    }

    @DeleteMapping("/delete")
    public Result delete( String userName){
        return this.userService.delete(userName);
    }

    @GetMapping("/get-user")
    public DataResult<User> getUser(String userName){
        return this.userService.getUser(userName);
    }

    @PostMapping("/signup")
    public Result signUp(@RequestBody UserDto userDto){
        return this.userService.signUp(userDto);
    }

    @PostMapping("/signin")
    public Result signIn(String userName, String password){
        return this.userService.signIn(userName, password);
    }
}
