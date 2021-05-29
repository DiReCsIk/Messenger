package com.nure.ua.client;

import com.google.gson.Gson;
import com.nure.ua.client.controller.AbstractController;
import com.nure.ua.client.controller.impl.MainControllerImpl;
import com.nure.ua.data_container.Response;
import com.nure.ua.data_container.Session;
import com.nure.ua.model.entity.Message;
import com.nure.ua.model.entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.nure.ua.util.constant.ClientConstants.*;
import static com.nure.ua.util.constant.PageConstants.SIGN_IN_PAGE_PATH;
import static com.nure.ua.util.constant.UtilConstants.*;

public class Client extends Application {
    private Map<User, List<Message>> userAndMessages;
    private AbstractController controller;
    private PrintWriter serverWriter;
    private Session session;
    private Stage stage;

    @Override
    public void init() {
        try {
            Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            Thread serverReaderThread = new Thread(new ServerReader(clientSocket.getInputStream()));
            serverReaderThread.start();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        switchCurrentFxml(SIGN_IN_PAGE_PATH);
        stage.setResizable(false);
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(ICON_PATH.toString()));
        stage.show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public void switchCurrentFxml(URL path) throws IOException {
        FXMLLoader loader = new FXMLLoader(path);
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        controller.setClient(this);
        if (controller instanceof MainControllerImpl) {
            ((MainControllerImpl) controller).updateUserChats();
        }
        scene.setFill(Color.TRANSPARENT);
        Platform.runLater(() -> stage.setScene(scene));
    }

    public Map<User, List<Message>> getUserAndMessages() {
        return userAndMessages;
    }

    public PrintWriter getServerWriter() {
        return serverWriter;
    }

    public Session getSession() {
        return session;
    }

    public void setUserAndMessages(Map<User, List<Message>> userAndMessages) {
        this.userAndMessages = userAndMessages;
    }

    private class ServerReader implements Runnable {
        private final BufferedReader serverReaderInput;

        public ServerReader(InputStream serverReaderInput) {
            this.serverReaderInput = new BufferedReader(new InputStreamReader(serverReaderInput));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (serverReaderInput.ready()) {
                        Response response = new Gson().fromJson(serverReaderInput.readLine(), Response.class);
                        receiveData(response);
                    }
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

        private void receiveData(Response response) throws IOException {
            if (response.hasAttribute(SESSION)) {
                session = response.getAttribute(SESSION, Session.class);
            }
            if (response.hasAttribute(MESSAGE)) {
                addMessage(response.getAttribute(MESSAGE, Message.class), response.getAttribute(RECEIVER, User.class),
                        response.getAttribute(SENDER, User.class));
            }
            if (response.hasAttribute(USER)) {
                User user = response.getAttribute(USER, User.class);
                if (!userAndMessages.containsKey(user)) {
                    userAndMessages.put(user, new ArrayList<>());
                }
            }
            controller.receiveServerInfo(response);
        }

        private void addMessage(Message message, User receiver, User sender) {
            User user = receiver.getId() == session.getUser().getId() ? sender : receiver;
            if (userAndMessages.containsKey(user)) {
                userAndMessages.get(user).add(message);
            } else {
                List<Message> list = new ArrayList<>();
                list.add(message);
                userAndMessages.put(user, list);
            }
        }
    }
}
