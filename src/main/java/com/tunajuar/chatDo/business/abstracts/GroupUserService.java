package com.tunajuar.chatDo.business.abstracts;

import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.GroupUser;

import java.util.List;

public interface GroupUserService {
    Result add(GroupUser groupUser);

    Result delete(GroupUser groupUser);

    DataResult<List<GroupUser>> getGroupUsers(String groupName);
}
