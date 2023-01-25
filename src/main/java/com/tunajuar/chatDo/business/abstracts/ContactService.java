package com.tunajuar.chatDo.business.abstracts;

import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Contact;

import java.util.List;

public interface ContactService {
    Result add(Contact contact);

    Result delete(String userName, String contactName);

    DataResult<List<Contact>> getUserContacts(String userName);
}
