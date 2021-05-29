package com.nure.ua.model.service.impl;

import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.repository.MessageRepository;
import com.nure.ua.model.repository.UserRepository;
import com.nure.ua.model.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Map<User, List<Message>> selectAllUserMessages(User user) {
        List<Message> allMessages = messageRepository.selectAllUserMessages(user.getId());
        return allMessages.stream().collect(Collectors.groupingBy(message -> message.getSenderId() == user.getId() ?
                        userRepository.findById(message.getReceiverId()).orElse(null) :
                        userRepository.findById(message.getSenderId()).orElse(null)));
    }

    @Override
    public void insertMessage(Message message) {
        messageRepository.save(message);
    }
}
