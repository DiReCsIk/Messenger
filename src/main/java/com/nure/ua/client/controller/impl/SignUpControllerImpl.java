package com.nure.ua.client.controller.impl;

import com.nure.ua.client.controller.AbstractController;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.nure.ua.util.constant.CommandConstants.SIGN_UP_COMMAND;
import static com.nure.ua.util.constant.PageConstants.SIGN_IN_PAGE_PATH;
import static com.nure.ua.util.constant.UtilConstants.*;

public class SignUpControllerImpl extends AbstractController implements Initializable {
    @FXML
    private AnchorPane signUpPane;
    @FXML
    private PasswordField signUpPasswordField;
    @FXML
    private PasswordField signUpConfirmPasswordField;
    @FXML
    private DatePicker signUpBirthdateField;
    @FXML
    private TextField signUpNameField;
    @FXML
    private TextField signUpSurnameField;
    @FXML
    private TextField signUpPhoneField;
    @FXML
    private TextField signUpNicknameField;
    @FXML
    private Label signUpExceptionLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addFocusedProperty(signUpNameField, NAME_REGEX);
        addFocusedProperty(signUpSurnameField, NAME_REGEX);
        addFocusedProperty(signUpNicknameField, NICKNAME_REGEX);
        addFocusedProperty(signUpPhoneField, PHONE_REGEX);
        addFocusedProperty(signUpPasswordField, signUpConfirmPasswordField, PASSWORD_REGEX);
        signUpConfirmPasswordField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                comparePasswords(signUpPasswordField, signUpConfirmPasswordField);
            }
        });
    }

    @Override
    public void receiveServerInfo(Response response) throws IOException {
        if (response.hasAttribute(EXCEPTION)) {
            setException(signUpExceptionLabel, response.getAttribute(EXCEPTION, String.class));
            return;
        }
        toSignInEvent();
    }

    @FXML
    private void signUpEvent() {
        if (signUpPane.getChildren().stream().anyMatch(children -> children.getStyleClass().contains(INVALID_STYLE_CLASS_NAME))) {
            setException(signUpExceptionLabel, ALL_FIELDS_MATCH_PATTERN_EXCEPTION);
        } else {
            Request request = new Request(SIGN_UP_COMMAND);
            request.putAttribute(USER_NAME, signUpNameField.getText(), String.class);
            request.putAttribute(USER_SURNAME, signUpSurnameField.getText(), String.class);
            request.putAttribute(USER_PHONE, signUpPhoneField.getText(), String.class);
            request.putAttribute(USER_NICKNAME, signUpNicknameField.getText(), String.class);
            request.putAttribute(USER_PASSWORD, signUpPasswordField.getText(), String.class);
            if (signUpBirthdateField.getValue() != null) {
                request.putAttribute(USER_BIRTHDAY, signUpBirthdateField.getValue().atStartOfDay(), LocalDateTime.class);
            }
            sendToServer(request);
        }
    }

    @FXML
    private void toSignInEvent() throws IOException {
        client.switchCurrentFxml(SIGN_IN_PAGE_PATH);
    }
}
