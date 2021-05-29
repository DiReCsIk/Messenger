package com.nure.ua.application.beans;

import com.nure.ua.command.Command;
import com.nure.ua.command.impl.FindUserCommandImpl;
import com.nure.ua.command.impl.SendMessageCommandImpl;
import com.nure.ua.command.impl.SignInCommandImpl;
import com.nure.ua.command.impl.SignUpCommandImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.nure.ua.util.constant.CommandConstants.*;

@Component
public class ApplicationBeans {
    private final ConfigurableApplicationContext context;

    @Autowired
    private ApplicationBeans(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Bean
    public Map<String, Command> serverCommands() {
        Map<String, Command> result = new HashMap<>();
        result.put(SIGN_IN_COMMAND, context.getBean(SignInCommandImpl.class));
        result.put(SIGN_UP_COMMAND, context.getBean(SignUpCommandImpl.class));
        result.put(SEND_MESSAGE_COMMAND, context.getBean(SendMessageCommandImpl.class));
        result.put(FIND_USER_COMMAND, context.getBean(FindUserCommandImpl.class));
        return result;
    }
}
