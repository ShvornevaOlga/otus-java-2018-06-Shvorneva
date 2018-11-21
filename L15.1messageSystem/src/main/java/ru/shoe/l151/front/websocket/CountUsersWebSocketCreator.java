package ru.shoe.l151.front.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.logging.Logger;

public class CountUsersWebSocketCreator implements WebSocketCreator {
    private final static Logger log = Logger.getLogger(CountUsersWebSocketCreator.class.getName());

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        CountUsersWebSocket socket = new CountUsersWebSocket();
        log.info("Socket created");
        return socket;
    }
}
