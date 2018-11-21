package ru.shoe.l151.front.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.shoe.l151.app.FrontendService;

import java.util.logging.Logger;

@WebSocket
public class GetNameByIdWebSocket {
    private final static Logger log = Logger.getLogger(GetNameByIdWebSocket.class.getName());
    private Session session;
    @Autowired
    private FrontendService frontendService;

    GetNameByIdWebSocket() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        frontendService.handleRequestGetNameById(Long.parseLong(data), this);
    }

    public void sendName(String name) {
        try {
            getSession().getRemote().sendString(name);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        log.info("GetNameByIdWebSocket onOpen");
    }

    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        log.info("GetNameByIdWebSocket onClose");
    }
}
