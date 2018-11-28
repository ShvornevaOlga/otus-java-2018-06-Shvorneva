package ru.otus.l161.front.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.logging.Logger;

public class AddUserWebSocketCreator implements WebSocketCreator {
    private final static Logger log = Logger.getLogger(AddUserWebSocketCreator.class.getName());

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        AddUserWebSocket socket = new AddUserWebSocket();
        log.info("Socket created");
        return socket;
    }
}
