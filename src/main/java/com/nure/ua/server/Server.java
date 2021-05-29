package com.nure.ua.server;

import java.io.IOException;
import java.net.ServerSocket;

import static com.nure.ua.server.ClientContainer.*;
import static com.nure.ua.util.constant.ServerConstants.SERVER_PORT;

public class Server extends Thread {
    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            while (!server.isClosed()) {
                addClient(server.accept());
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
