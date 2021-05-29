package com.nure.ua.client.controller.impl;

import com.nure.ua.client.controller.AbstractController;
import com.nure.ua.client.node.ChatPane;
import com.nure.ua.client.node.MessageBox;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import com.nure.ua.util.enums.MessagePosition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nure.ua.util.constant.CommandConstants.FIND_USER_COMMAND;
import static com.nure.ua.util.constant.CommandConstants.SEND_MESSAGE_COMMAND;
import static com.nure.ua.util.constant.UtilConstants.*;
import static javafx.application.Platform.runLater;

public class MainControllerImpl extends AbstractController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    @FXML
    private VBox userChatsBox;
    @FXML
    private AnchorPane messageAnchorPane;
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private TextField addUserTextField;
    @FXML
    private TextField userInput;
    @FXML
    private ImageView addUserButton;
    private ChatPane selectedChat;
    private List<ChatPane> userChats;

    @Override
    public void receiveServerInfo(Response response) {
        if (response.hasAttribute(MESSAGE)) {
            addMessage(response.getAttribute(MESSAGE, Message.class), response.getAttribute(RECEIVER, User.class),
                    response.getAttribute(SENDER, User.class));
        }
        if (response.hasAttribute(USER)) {
            addChat(response.getAttribute(USER, User.class));
        }
    }

    public void updateUserChats() {
        userChats = new ArrayList<>();
        userChatsBox.getChildren().clear();
        for (Map.Entry<User, List<Message>> entry : client.getUserAndMessages().entrySet()) {
            Message lastMessage = entry.getValue().get(entry.getValue().size() - 1);
            ChatPane chatPane = new ChatPane(entry.getKey(), lastMessage.getText(), getMessageTime(lastMessage));
            chatPane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::chatClickEvent);
            VBox.setMargin(chatPane, new Insets(CHAT_PANE_TOP_MARGIN, CHAT_PANE_BOTTOM_MARGIN, CHAT_PANE_LEFT_MARGIN, CHAT_PANE_RIGHT_MARGIN));
            userChats.add(chatPane);
            userChatsBox.getChildren().add(chatPane);
        }
    }

    @FXML
    private void initialize() {
        messageAnchorPane.heightProperty().addListener(observable -> messageScrollPane.setVvalue(1D));
    }

    @FXML
    private void addUser() {
        String input = addUserTextField.getText();
        if (input.isBlank()) {
            return;
        }
        Request request = new Request(FIND_USER_COMMAND);
        request.putAttribute(USER_NICKNAME, input, String.class);
        sendToServer(request);
        addUserTextField.clear();
    }

    @FXML
    private void sendMessage() {
        String input = userInput.getText();
        if (selectedChat == null || input.isBlank()) {
            return;
        }
        User receiver = selectedChat.getChatUser();
        User sender = client.getSession().getUser();
        Request request = new Request(SEND_MESSAGE_COMMAND);
        request.putAttribute(RECEIVER, receiver, User.class);
        request.putAttribute(SENDER, sender, User.class);
        request.putAttribute(MESSAGE, input, String.class);
        sendToServer(request);
        userInput.clear();
    }

    private void addChat(User user) {
        runLater(() -> {
            ChatPane chat = null;
            boolean foundChat = false;
            for (ChatPane chatPane : userChats) {
                if (chatPane.getChatUser().getId() == user.getId()) {
                    foundChat = true;
                    chat = chatPane;
                    break;
                }
            }
            if (!foundChat) {
                chat = new ChatPane(user, "", "");
                userChats.add(chat);
                userChatsBox.getChildren().add(chat);
                VBox.setMargin(chat, new Insets(CHAT_PANE_TOP_MARGIN, CHAT_PANE_BOTTOM_MARGIN, CHAT_PANE_LEFT_MARGIN, CHAT_PANE_RIGHT_MARGIN));
                chat.addEventHandler(MouseEvent.MOUSE_CLICKED, this::chatClickEvent);
            }
            setActiveChat(chat);
        });
    }

    private void addMessage(Message message, User receiver, User sender) {
        runLater(() -> {
            User user = client.getSession().getUser().getId() == receiver.getId() ? sender : receiver;
            boolean isNewChat = true;
            for (ChatPane chatPane : userChats) {
                if (chatPane.getChatUser().getId() == user.getId()) {
                    runLater(() -> {
                        chatPane.setLastMessageText(message.getText());
                        chatPane.setChatLastMessageTime(getMessageTime(message));
                    });
                    isNewChat = false;
                    break;
                }
            }
            if (isNewChat) {
                ChatPane newChat = new ChatPane(user, message.getText(), getMessageTime(message));
                userChats.add(newChat);
                newChat.addEventHandler(MouseEvent.MOUSE_CLICKED, this::chatClickEvent);
                userChatsBox.getChildren().add(newChat);
            }
            if (selectedChat != null && selectedChat.getChatUser().getId() == user.getId()) {
                runLater(() -> setActiveChat(selectedChat));
            }
        });
    }

    private String getMessageTime(Message message) {
        return message.getTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()) ?
                message.getTime().toLocalTime().format(formatter) :
                message.getTime().toLocalDate().getDayOfWeek().name().substring(0, 3);
    }

    private void chatClickEvent(MouseEvent event) {
        ChatPane source = (ChatPane) event.getSource();
        if (source == null || source.getStyleClass().contains(SELECTED_CHAT)) {
            return;
        }
        setActiveChat(source);
    }

    private void setActiveChat(ChatPane chat) {
        if (selectedChat != null) {
            selectedChat.getStyleClass().remove(SELECTED_CHAT);
        }
        chat.getStyleClass().add(SELECTED_CHAT);
        selectedChat = chat;
        User user = chat.getChatUser();
        List<Message> messageList = client.getUserAndMessages().get(user);
        messageAnchorPane.getChildren().clear();
        messageAnchorPane.setPrefHeight(DEFAULT_MESSAGE_PANE_HEIGHT);
        for (Message message : messageList) {
            MessagePosition position;
            if (user.getId() == message.getSenderId()) {
                position = MessagePosition.LEFT;
            } else {
                position = MessagePosition.RIGHT;
            }
            MessageBox messageBox = new MessageBox(message, position);
            messageAnchorPane.getChildren().addAll(messageBox);
        }
        Thread thread = new LayoutSetter();
        thread.start();
    }

    private class LayoutSetter extends Thread {

        @Override
        public void run() {
            List<Node> children = messageAnchorPane.getChildren();
            int yLayout = DEFAULT_MESSAGE_BOX_Y_LAYOUT;
            for (Node child : children) {
                if (child instanceof MessageBox messageBox) {
                    while (messageBox.getHeight() == 0) Thread.onSpinWait();
                    int finalYLayout = yLayout;
                    runLater(() -> {
                        child.setLayoutY(finalYLayout);
                        child.setVisible(true);
                    });
                    yLayout += MESSAGE_BOX_Y_ADDITION + messageBox.getHeight();
                }
            }
            if (yLayout > messageAnchorPane.getHeight()) {
                messageAnchorPane.setPrefHeight(yLayout - DEFAULT_MESSAGE_BOX_Y_LAYOUT);
            }
        }
    }
}
