package com.tunajuar.chatDo.business.concretes;

import com.tunajuar.chatDo.business.abstracts.GroupUserService;
import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.core.utilities.result.SuccessDataResult;
import com.tunajuar.chatDo.core.utilities.result.SuccessResult;
import com.tunajuar.chatDo.dataAccess.abstracts.GroupUserRepository;
import com.tunajuar.chatDo.entities.concretes.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserManager implements GroupUserService {
    private GroupUserRepository groupUserRepository;

    @Autowired
    public GroupUserManager(GroupUserRepository groupUserRepository) {
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    public Result add(GroupUser groupUser) {
        this.groupUserRepository.insert(groupUser);
        return new SuccessResult();
    }

    @Override
    public Result delete(GroupUser groupUser) {
        this.groupUserRepository.delete(groupUser);
        return new SuccessResult();
    }

    @Override
    public DataResult<List<GroupUser>> getGroupUsers(String groupName) {
        return new SuccessDataResult<>(this.groupUserRepository.findByGroupName(groupName));
    }
}
