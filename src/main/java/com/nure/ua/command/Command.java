package com.nure.ua.command;

import com.nure.ua.data_container.Request;
import com.nure.ua.data_container.Response;
import com.nure.ua.data_container.Session;
import com.nure.ua.server.ClientContainer;

import static com.nure.ua.util.constant.UtilConstants.SESSION;

public abstract class Command {
    public abstract void execute(Request clientRequest, Session session);

    protected void sendResponse(Response response) {
        Session session = response.getAttribute(SESSION, Session.class);
        ClientContainer.getClientBySessionId(session.getId())
                .ifPresent(connection -> connection.sendDataToClient(response));
    }
}
