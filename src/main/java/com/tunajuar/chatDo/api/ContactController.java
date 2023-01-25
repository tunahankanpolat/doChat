package com.tunajuar.chatDo.api;

import com.tunajuar.chatDo.business.abstracts.ContactService;
import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/contact")
public class ContactController {
    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody Contact contact){
        return this.contactService.add(contact);
    }

    @DeleteMapping("/delete")
    public Result delete(String userName, String contactName){
        return this.contactService.delete(userName, contactName);
    }

    @GetMapping("/get-user-contacts")
    public DataResult<List<Contact>> getUserContacts(String userName){
        return this.contactService.getUserContacts(userName);
    }
}
