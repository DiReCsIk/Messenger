package com.nure.ua.command.impl;

import com.nure.ua.command.Command;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.exception.ServiceException;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.service.UserService;
import com.nure.ua.data_container.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.nure.ua.util.constant.UtilConstants.*;

@Component
public class SignUpCommandImpl extends Command {
    private final UserService userService;

    @Autowired
    public SignUpCommandImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Request request, Session session) {
        User user = new User();
        user.setName(request.getAttribute(USER_NAME, String.class));
        user.setSurname(request.getAttribute(USER_SURNAME, String.class));
        user.setNickname(request.getAttribute(USER_NICKNAME, String.class));
        user.setPhone(request.getAttribute(USER_PHONE, String.class));
        user.setPassword(request.getAttribute(USER_PASSWORD, String.class));
        LocalDateTime birthdate = request.getAttribute(USER_BIRTHDAY, LocalDateTime.class);
        if (birthdate != null) {
            user.setBirthdate(birthdate);
        }
        Response result = new Response(session);
        try {
            userService.insertEntity(user);
        } catch (ServiceException exception) {
            result.putAttribute(EXCEPTION, exception.getMessage(), String.class);
        }
        sendResponse(result);
    }
}
