package com.nure.ua.command.impl;

import com.nure.ua.command.Command;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.data_container.Session;
import com.nure.ua.model.entity.User;
import com.nure.ua.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.nure.ua.util.constant.UtilConstants.USER;
import static com.nure.ua.util.constant.UtilConstants.USER_NICKNAME;

@Component
public class FindUserCommandImpl extends Command {
    private final UserService userService;

    @Autowired
    public FindUserCommandImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Request clientRequest, Session session) {
        String userNickname = clientRequest.getAttribute(USER_NICKNAME, String.class);
        User user = userService.selectUserByNickname(userNickname);
        Response response = new Response(session);
        response.putAttribute(USER, user, User.class);
        sendResponse(response);
    }
}
