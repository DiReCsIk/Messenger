package com.nure.ua.util.constant;

import java.net.URL;
import java.util.Objects;

public class ClientConstants {
    public static final URL ICON_PATH = Objects.requireNonNull(ClientConstants.class.getResource("/image/icon.png"));
    public static final String SERVER_ADDRESS = "localhost";
    public static final String TITLE = "Diregram";
    public static final int SERVER_PORT = 8080;

    private ClientConstants(){
    }
}
