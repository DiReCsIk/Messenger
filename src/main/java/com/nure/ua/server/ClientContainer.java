package com.nure.ua.server;

import com.nure.ua.model.entity.User;
import com.nure.ua.data_container.Session;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientContainer {
    private static int id = 0;
    private static final List<ClientConnection> connections;

    static {
        connections = new ArrayList<>();
    }

    private ClientContainer() {
    }

    public static void addClient(Socket client) {
        ClientConnection connection = new ClientConnection(client, new Session(null, ++id));
        Thread thread = new Thread(connection);
        thread.start();
        connections.add(connection);
    }

    public static List<ClientConnection> getClientsByUserId(long id) {
        return connections.stream()
                .filter(connection -> (connection.getClientSession().getUser() != null
                        && connection.getClientSession().getUser().getId() == id))
                .collect(Collectors.toList());
    }

    public static Optional<ClientConnection> getClientBySessionId(long id) {
        return connections.stream()
                .filter(connection -> connection.getClientSession().getId() == id)
                .findFirst();
    }

    public static List<Session> getUserSessions(User user) {
        return ClientContainer.getClientsByUserId(user.getId()).stream()
                .map(ClientConnection::getClientSession)
                .collect(Collectors.toList());
    }
}
