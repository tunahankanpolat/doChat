package com.tunajuar.chatDo.business.abstracts;

import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Group;

public interface GroupService {
    Result add(Group group);

    Result delete(String groupName);
}
