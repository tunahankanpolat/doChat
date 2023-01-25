package com.tunajuar.chatDo.business.abstracts;

import com.tunajuar.chatDo.core.utilities.result.DataResult;
import com.tunajuar.chatDo.core.utilities.result.Result;
import com.tunajuar.chatDo.entities.concretes.Contact;
import com.tunajuar.chatDo.entities.concretes.Message;
import com.tunajuar.chatDo.entities.dtos.MessageDto;

import java.util.List;

public interface MessageService {

    Result sendMessage(MessageDto message);
    DataResult<List<Message>> getMessages(Contact contact);
}
