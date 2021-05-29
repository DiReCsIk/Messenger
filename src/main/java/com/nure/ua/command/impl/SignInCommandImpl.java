package com.nure.ua.command.impl;

import com.google.gson.reflect.TypeToken;
import com.nure.ua.command.Command;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.service.MessageService;
import com.nure.ua.model.service.UserService;
import com.nure.ua.data_container.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.nure.ua.util.constant.UtilConstants.*;

@Component
public class SignInCommandImpl extends Command {
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public SignInCommandImpl(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public void execute(Request request, Session session) {
        String userPhone = request.getAttribute(USER_PHONE, String.class);
        String userPassword = request.getAttribute(USER_PASSWORD, String.class);
        User user = userService.selectUserByPhone(userPhone);
        if (user == null || !user.getPassword().equals(userPassword)) {
            Response result = new Response(session);
            result.putAttribute(EXCEPTION, INCORRECT_PHONE_OR_PASSWORD_EXCEPTION, String.class);
            sendResponse(result);
            return;
        }
        session.setUser(user);
        Response result = new Response(session);
        result.putAttribute(USER_MESSAGES, messageService.selectAllUserMessages(user), new TypeToken<Map<User, List<Message>>>() {
        }.getType());
        sendResponse(result);
    }
}
