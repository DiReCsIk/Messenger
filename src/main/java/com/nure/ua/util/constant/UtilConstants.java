package com.nure.ua.util.constant;

import java.util.regex.Pattern;

public final class UtilConstants {
    public static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$");
    public static final Pattern PHONE_REGEX = Pattern.compile("\\d{10}");
    public static final Pattern NAME_REGEX = Pattern.compile("([A-Z])([a-z]{1,31})");
    public static final Pattern NICKNAME_REGEX = Pattern.compile("^[^0-9]\\w{2,32}$");

    public static final Integer CHAT_PANE_LEFT_MARGIN = 10;
    public static final Integer CHAT_PANE_RIGHT_MARGIN = 10;
    public static final Integer CHAT_PANE_TOP_MARGIN = 10;
    public static final Integer CHAT_PANE_BOTTOM_MARGIN = 7;
    public static final Integer DEFAULT_MESSAGE_PANE_HEIGHT = 0;
    public static final Integer DEFAULT_MESSAGE_BOX_Y_LAYOUT = 10;
    public static final Integer MESSAGE_BOX_Y_ADDITION = 30;

    public static final String MESSAGE = "message";
    public static final String USER_MESSAGES = "userMessages";
    public static final String SELECTED_CHAT = "selectedChat";
    public static final String RECEIVER = "receiver";
    public static final String USER = "user";
    public static final String SENDER = "sender";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_NICKNAME = "userNickname";
    public static final String USER_BIRTHDAY = "userBirthday";
    public static final String USER_PASSWORD = "userPassword";
    public static final String CHAT_SENDER = "chatSender";
    public static final String MESSAGE_TEXT = "messageText";
    public static final String SESSION = "session";
    public static final String CHAT_TIME = "chatTime";
    public static final Integer MESSAGE_SPACING = 20;
    public static final Integer SENDER_MESSAGE_BOX_X_LAYOUT = 20;
    public static final Integer RECEIVER_MESSAGE_BOX_X_LAYOUT = 610;
    public static final String SENDER_MESSAGE_BOX = "senderMessageBox";
    public static final String RECEIVER_MESSAGE_BOX = "receiverMessageBox";


    public static final String EXCEPTION = "exception";
    public static final String INCORRECT_PHONE_OR_PASSWORD_EXCEPTION = "Incorrect phone or password!";
    public static final String ALL_FIELDS_MATCH_PATTERN_EXCEPTION = "All fields must match their patterns!";
    public static final String USER_ALREADY_EXISTS_EXCEPTION = "User with specified phone already exists!";
    public static final String INCORRECT_COMMAND_EXCEPTION = "Incorrect command!";

    private UtilConstants() {
    }
}
