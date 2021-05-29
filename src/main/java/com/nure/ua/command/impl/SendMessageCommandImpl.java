package com.nure.ua.command.impl;

import com.nure.ua.command.Command;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.data_container.Session;
import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.service.MessageService;
import com.nure.ua.server.ClientContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.nure.ua.util.constant.UtilConstants.*;

@Component
public class SendMessageCommandImpl extends Command {
    private final MessageService messageService;

    @Autowired
    public SendMessageCommandImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Request clientRequest, Session session) {
        User sender = clientRequest.getAttribute(SENDER, User.class);
        User receiver = clientRequest.getAttribute(RECEIVER, User.class);
        String messageText = clientRequest.getAttribute(MESSAGE, String.class);
        Message message = new Message();
        message.setSenderId(sender.getId());
        message.setReceiverId(receiver.getId());
        message.setText(messageText);
        message.setTime(LocalDateTime.now());
        messageService.insertMessage(message);
        List<Session> sessions = ClientContainer.getUserSessions(receiver);
        sessions.addAll(ClientContainer.getUserSessions(sender));
        sessions = sessions.stream().distinct().collect(Collectors.toList());
        for (Session elem : sessions) {
            Response response = new Response(elem);
            response.putAttribute(MESSAGE, message, Message.class);
            response.putAttribute(RECEIVER, receiver, User.class);
            response.putAttribute(SENDER, sender, User.class);
            sendResponse(response);
        }
    }
}
