package com.nure.ua.client.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nure.ua.client.Client;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

import static javafx.application.Platform.runLater;

public abstract class AbstractController {
    protected static final String VALID_STYLE_CLASS_NAME = "validTextField";
    protected static final String INVALID_STYLE_CLASS_NAME = "invalidTextField";
    protected Client client;

    public abstract void receiveServerInfo(Response response) throws IOException;

    public void setClient(Client client) {
        this.client = client;
    }

    protected void clearStyleClasses(Node node, String... styleClasses) {
        node.getStyleClass().removeAll(styleClasses);
    }

    protected void addFocusedProperty(TextField textField, Pattern pattern) {
        textField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                validateTextField(textField, pattern);
            }
        });
    }

    protected void addFocusedProperty(PasswordField passwordField, PasswordField comparePasswordField, Pattern pattern) {
        passwordField.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) {
                validatePasswordField(passwordField, comparePasswordField, pattern);
            }
        });
    }

    protected void comparePasswords(PasswordField firstPasswordField, PasswordField secondPasswordField) {
        clearStyleClasses(secondPasswordField, VALID_STYLE_CLASS_NAME, INVALID_STYLE_CLASS_NAME);
        if (firstPasswordField.getText().equals(secondPasswordField.getText())) {
            secondPasswordField.getStyleClass().add(VALID_STYLE_CLASS_NAME);
        } else {
            secondPasswordField.getStyleClass().add(INVALID_STYLE_CLASS_NAME);
        }
    }

    protected void sendToServer(Request request) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        client.getServerWriter().println(gson.toJson(request));
    }

    protected void setException(Label label, String exceptionMessage) {
        runLater(() -> {
            label.setText(exceptionMessage);
            label.setVisible(true);
        });
    }

    private void validateTextField(TextField textField, Pattern pattern) {
        clearStyleClasses(textField, VALID_STYLE_CLASS_NAME, INVALID_STYLE_CLASS_NAME);
        if (textField.getText().matches(pattern.pattern())) {
            textField.getStyleClass().add(VALID_STYLE_CLASS_NAME);
        } else {
            textField.getStyleClass().add(INVALID_STYLE_CLASS_NAME);
        }
    }

    private void validatePasswordField(PasswordField passwordField, PasswordField comparePasswordField, Pattern pattern) {
        clearStyleClasses(passwordField, VALID_STYLE_CLASS_NAME, INVALID_STYLE_CLASS_NAME);
        comparePasswords(passwordField, comparePasswordField);
        if (passwordField.getText().matches(pattern.pattern())) {
            passwordField.getStyleClass().add(VALID_STYLE_CLASS_NAME);
        } else {
            passwordField.getStyleClass().add(INVALID_STYLE_CLASS_NAME);
        }
    }
}
