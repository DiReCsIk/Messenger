package com.nure.ua.client.node;

import com.nure.ua.model.entity.User;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import static com.nure.ua.util.constant.UtilConstants.*;

public class ChatPane extends AnchorPane {
    private static final int CHAT_SENDER_LABEL_X_LAYOUT = 15;
    private static final int CHAT_SENDER_LABEL_Y_LAYOUT = 5;
    private static final int CHAT_SENDER_LABEL_WIDTH = 245;
    private static final int CHAT_SENDER_LABEL_HEIGHT = 17;
    private static final int MESSAGE_TEXT_LABEL_X_LAYOUT = 15;
    private static final int MESSAGE_TEXT_LABEL_Y_LAYOUT = 35;
    private static final int MESSAGE_TEXT_LABEL_WIDTH = 250;
    private static final int MESSAGE_TEXT_LABEL_HEIGHT = 17;
    private static final int MESSAGE_TIME_LABEL_X_LAYOUT = 230;
    private static final int MESSAGE_TIME_LABEL_Y_LAYOUT = 6;
    private static final int MESSAGE_TIME_LABEL_WIDTH = 60;
    private static final int MESSAGE_TIME_LABEL_HEIGHT = 15;
    private static final int CHAT_HEIGHT = 60;
    private static final String CHAT_STYLE_CLASS = "chatPane";
    private final User chatUser;
    private final Label chatLastMessageTime;
    private final Label chatLastMessage;

    public ChatPane(User chatUser, String lastMessage, String messageTime) {
        this.chatUser = chatUser;
        Label chatSender = initLabel(chatUser.getName() + " " + chatUser.getSurname(), CHAT_SENDER, CHAT_SENDER_LABEL_X_LAYOUT, CHAT_SENDER_LABEL_Y_LAYOUT,
                CHAT_SENDER_LABEL_WIDTH, CHAT_SENDER_LABEL_HEIGHT);
        chatLastMessage = initLabel(lastMessage, MESSAGE_TEXT, MESSAGE_TEXT_LABEL_X_LAYOUT,
                MESSAGE_TEXT_LABEL_Y_LAYOUT, MESSAGE_TEXT_LABEL_WIDTH, MESSAGE_TEXT_LABEL_HEIGHT);
        chatLastMessageTime = initLabel(messageTime, CHAT_TIME, MESSAGE_TIME_LABEL_X_LAYOUT,
                MESSAGE_TIME_LABEL_Y_LAYOUT, MESSAGE_TIME_LABEL_WIDTH, MESSAGE_TIME_LABEL_HEIGHT);
        setPrefHeight(CHAT_HEIGHT);
        setCursor(Cursor.HAND);
        getChildren().addAll(chatSender, chatLastMessage, chatLastMessageTime);
        getStyleClass().add(CHAT_STYLE_CLASS);
    }

    public User getChatUser() {
        return chatUser;
    }

    public void setLastMessageText(String text) {
        chatLastMessage.setText(text);
    }

    public void setChatLastMessageTime(String time) {
        chatLastMessageTime.setText(time);
    }

    private Label initLabel(String text, String styleClassName, int xLayout, int yLayout, int width, int height) {
        Label label = new Label(text);
        label.setLayoutX(xLayout);
        label.setLayoutY(yLayout);
        label.getStyleClass().add(styleClassName);
        label.setPrefSize(width, height);
        return label;
    }
}
