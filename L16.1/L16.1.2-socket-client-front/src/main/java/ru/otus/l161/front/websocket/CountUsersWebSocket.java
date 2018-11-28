package ru.otus.l161.front.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.l161.FrontendService;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@WebSocket
public class CountUsersWebSocket {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
    private final String id;
    private final static Logger log = Logger.getLogger(CountUsersWebSocket.class.getName());
    private Session session;
    @Autowired
    private FrontendService frontendService;

    CountUsersWebSocket(){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        id = "CountUsersWebSocket"+String.valueOf(ID_GENERATOR.getAndIncrement());
    }

    public String getId() {
        return id;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        frontendService.handleRequestCountUsers(this);
    }

    public void sendCountUsers(int count) {
        try {
            getSession().getRemote().sendString(String.valueOf(count));
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        log.info("CountUsersWebSocket onOpen");
    }

    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        frontendService.getCountUsersWebSocketMap().remove(this.id);
        log.info("CountUsersWebSocket onClose");
    }
}
