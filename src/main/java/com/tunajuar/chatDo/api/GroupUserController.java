package com.tunajuar.chatDo.api;

import com.tunajuar.chatDo.business.abstracts.GroupUserService;
import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.core.utilities.result.SuccessResult;
import com.tunajuar.chatDo.entities.concretes.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/group-user")
public class GroupUserController {
    private GroupUserService groupUserService;

    @Autowired
    public GroupUserController(GroupUserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody GroupUser groupUser){
        return this.groupUserService.add(groupUser);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody GroupUser groupUser){
        return this.groupUserService.delete(groupUser);
    }

    @GetMapping("/get-group-users")
    public DataResult<List<GroupUser>> getGroupUsers(String groupName){
        return this.groupUserService.getGroupUsers(groupName);
    }
}
