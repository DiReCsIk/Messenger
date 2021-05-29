package com.nure.ua.server;

import com.google.gson.Gson;
import com.nure.ua.application.context.ApplicationContextProvider;
import com.nure.ua.command.Command;
import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import static com.nure.ua.util.constant.UtilConstants.INCORRECT_COMMAND_EXCEPTION;

public class ClientConnection implements Runnable {
    private final Map<String, Command> commands;
    private Socket clientSocket;
    private Session clientSession;
    private PrintWriter clientWriter;

    @SuppressWarnings("unchecked")
    public ClientConnection() {
        commands = (Map<String, Command>) ApplicationContextProvider.getApplicationContext().getBean("serverCommands");
    }

    public ClientConnection(Socket clientSocket, Session clientSession) {
        this();
        this.clientSocket = clientSocket;
        this.clientSession = clientSession;
    }

    @Override
    public void run() {
        try (BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            while (!clientSocket.isClosed()) {
                if (clientReader.ready()) {
                    String clientMessage = clientReader.readLine();
                    Request clientRequest = new Gson().fromJson(clientMessage, Request.class);
                    Command command = commands.get(clientRequest.getCommandName());
                    if (command == null) {
                        throw new IOException(INCORRECT_COMMAND_EXCEPTION);
                    }
                    command.execute(clientRequest, clientSession);
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void sendDataToClient(Object data) {
        String jsonString = new Gson().toJson(data);
        clientWriter.println(jsonString);
    }

    public Session getClientSession() {
        return clientSession;
    }
}
