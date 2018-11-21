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
public class CountUsersWebSocket {
    private final static Logger log = Logger.getLogger(CountUsersWebSocket.class.getName());
    private Session session;
    @Autowired
    private FrontendService frontendService;

    CountUsersWebSocket(){
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        frontendService.handleRequestCountUsers(this);
    }

    public void sendCountUsers(String count) {
        try {
            getSession().getRemote().sendString(count);
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
        log.info("CountUsersWebSocket onClose");
    }
}
