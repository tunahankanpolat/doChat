package com.tunajuar.chatDo.api;

import com.tunajuar.chatDo.business.abstracts.GroupService;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/group")
public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody Group group){
        return this.groupService.add(group);
    }

    @DeleteMapping("/delete")
    public Result delete(String groupName){
        return this.groupService.delete(groupName);
    }
}
