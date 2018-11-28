package ru.otus.l161.front.servlet;


import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import ru.otus.l161.front.websocket.GetNameByIdWebSocketCreator;

import java.util.concurrent.TimeUnit;

public class GetNameByIdWebSocketServlet extends WebSocketServlet {
    private final static long LOGOUT_TIME = TimeUnit.MINUTES.toMillis(10);

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        factory.setCreator(new GetNameByIdWebSocketCreator());
    }
}