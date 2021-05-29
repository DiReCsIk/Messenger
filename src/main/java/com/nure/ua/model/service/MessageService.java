package com.nure.ua.model.service;

import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;

import java.util.List;
import java.util.Map;

public interface MessageService {
    Map<User, List<Message>> selectAllUserMessages(User user);

    void insertMessage(Message message);
}
