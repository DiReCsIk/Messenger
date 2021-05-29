package com.nure.ua.client.controller.impl;

import com.google.gson.reflect.TypeToken;
import com.nure.ua.client.controller.AbstractController;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.nure.ua.util.constant.CommandConstants.SIGN_IN_COMMAND;
import static com.nure.ua.util.constant.PageConstants.MAIN_PAGE_PATH;
import static com.nure.ua.util.constant.PageConstants.SIGN_UP_PAGE_PATH;
import static com.nure.ua.util.constant.UtilConstants.*;

public class SignInControllerImpl extends AbstractController {
    @FXML
    private TextField signInPhone;
    @FXML
    private PasswordField signInPassword;
    @FXML
    private Label signInExceptionLabel;

    @Override
    public void receiveServerInfo(Response response) throws IOException {
        if (response.hasAttribute(EXCEPTION)) {
            setException(signInExceptionLabel, response.getAttribute(EXCEPTION, String.class));
            return;
        }
        if (response.hasAttribute(USER_MESSAGES)) {
            client.setUserAndMessages(response.getAttribute(USER_MESSAGES, new TypeToken<Map<User, List<Message>>>() {
            }.getType()));
        }
        client.switchCurrentFxml(MAIN_PAGE_PATH);
    }

    @FXML
    private void toSignUpEvent() {
        try {
            client.switchCurrentFxml(SIGN_UP_PAGE_PATH);
        } catch (IOException exception) {
            setException(signInExceptionLabel, exception.getMessage());
        }
    }

    @FXML
    private void signInEvent() {
        if (!signInPassword.getText().matches(PASSWORD_REGEX.pattern()) || !signInPhone.getText().matches(PHONE_REGEX.pattern())) {
            setException(signInExceptionLabel, INCORRECT_PHONE_OR_PASSWORD_EXCEPTION);
            return;
        }
        Request request = new Request(SIGN_IN_COMMAND);
        request.putAttribute(USER_PHONE, signInPhone.getText(), String.class);
        request.putAttribute(USER_PASSWORD, signInPassword.getText(), String.class);
        sendToServer(request);
    }
}
