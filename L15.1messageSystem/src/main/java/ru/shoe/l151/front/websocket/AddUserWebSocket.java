package ru.shoe.l151.front.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.shoe.l151.app.FrontendService;
import ru.shoe.l151.db.base.datasets.PhoneDataSet;
import ru.shoe.l151.db.base.datasets.UserDataSet;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@WebSocket
public class AddUserWebSocket {
    private final static Logger log = Logger.getLogger(AddUserWebSocket.class.getName());
    private Session session;
    @Autowired
    private FrontendService frontendService;

    AddUserWebSocket() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        Gson gson = new Gson();
        UserDataSet user = gson.fromJson(data, UserDataSet.class);
        Set<PhoneDataSet> phoneDataSets = user.getPhones();
        user.setPhones(new HashSet<>());
        phoneDataSets.forEach(user::addPhone);
        frontendService.handleRequestAddUser(user, this);
    }

    public void sendUserId(String userId) {
        try {
            getSession().getRemote().sendString(userId);
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        log.info("AddUserWebSocket onOpen");
    }

    private Session getSession() {
        return session;
    }

    private void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        log.info("AddUserWebSocket onClose");
    }
}
