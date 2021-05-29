package com.nure.ua.client.node;

import com.nure.ua.model.entity.Message;
import com.nure.ua.util.enums.MessagePosition;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

import static com.nure.ua.util.constant.UtilConstants.*;

public class MessageBox extends VBox {
    private static final int MESSAGE_BOX_WIDTH = 430;

    public MessageBox(Message message, MessagePosition position) {
        Label messageContent = new Label(message.getText());
        Label messageDate = new Label(message.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss")));
        messageContent.setWrapText(true);
        messageDate.setWrapText(true);
        setSpacing(MESSAGE_SPACING);
        setVisible(false);
        if (position == MessagePosition.LEFT) {
            configureMessageBox(SENDER_MESSAGE_BOX_X_LAYOUT, SENDER_MESSAGE_BOX);
        } else {
            configureMessageBox(RECEIVER_MESSAGE_BOX_X_LAYOUT, RECEIVER_MESSAGE_BOX);
        }
        getChildren().addAll(messageContent, messageDate);
    }

    private void configureMessageBox(int xLayout, String styleClass) {
        setPrefWidth(MESSAGE_BOX_WIDTH);
        setLayoutX(xLayout);
        getStyleClass().add(styleClass);
    }
}
