package ru.otus.l161.front.websocket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import java.util.logging.Logger;

public class GetNameByIdWebSocketCreator implements WebSocketCreator {
    private final static Logger log = Logger.getLogger(GetNameByIdWebSocketCreator.class.getName());

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        GetNameByIdWebSocket socket = new GetNameByIdWebSocket();
        log.info("Socket created");
        return socket;
    }
}
