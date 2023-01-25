package com.tunajuar.chatDo.business.concretes;

import com.tunajuar.chatDo.business.abstracts.UserService;
import com.tunajuar.chatDo.core.utilities.result.*;
import com.tunajuar.chatDo.dataAccess.abstracts.UserRepository;
import com.tunajuar.chatDo.entities.concretes.User;
import com.tunajuar.chatDo.entities.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result add(User user) {
        this.userRepository.insert(user);
        return new SuccessResult();
    }

    @Override
    public Result delete(String userName) {
        User user = this.userRepository.findById(userName).get();
        this.userRepository.delete(user);
        return new SuccessResult();
    }

    @Override
    public DataResult<User> getUser(String userName) {
        return new SuccessDataResult<>(this.userRepository.findById(userName).get());
    }

    @Override
    public Result signUp(UserDto userDto) {
        if (!this.userRepository.findById(userDto.getUserName()).isPresent()){
            this.userRepository.insert(new User(userDto));
            return new SuccessResult("Registered successfully!!");
        }else {
            return new ErrorResult("Username is not valid");
        }
    }

    @Override
    public DataResult<User> signIn(String userName, String password) {
        if (this.userRepository.findById(userName).isPresent()) {
            if(this.userRepository.findByUserNameAndPassword(userName, password) != null){
                return new SuccessDataResult<>(this.userRepository.findByUserNameAndPassword(userName, password), "Login successful!!");
            }else {
                return new ErrorDataResult<>("Password is not correct");
            }
        }else {
            return new ErrorDataResult<>("Username is not correct");
        }
    }
}
