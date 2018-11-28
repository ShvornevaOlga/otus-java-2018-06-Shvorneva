package ru.otus.l161.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.l161.FrontendService;
import ru.otus.l161.app.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.front.websocket.AddUserWebSocket;
import ru.otus.l161.front.websocket.CountUsersWebSocket;
import ru.otus.l161.front.websocket.GetNameByIdWebSocket;
import ru.otus.l161.messages.AddUserMsg;
import ru.otus.l161.messages.GetCountUsersMsg;
import ru.otus.l161.messages.GetNameMsg;
import ru.otus.l161.messages.PingMsg;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class FrontendServiceImpl implements FrontendService {
    private Map<String, CountUsersWebSocket> countUsersWebSocketMap = new HashMap<>();
    private Map<String, AddUserWebSocket> addUserWebSocketMap = new HashMap<>();
    private Map<String, GetNameByIdWebSocket> getNameWebSocketMap = new HashMap<>();
    @Autowired
    private SocketMsgWorker client;

    public Map<String, CountUsersWebSocket> getCountUsersWebSocketMap() {
        return countUsersWebSocketMap;
    }

    public Map<String, AddUserWebSocket> getAddUserWebSocketMap() {
        return addUserWebSocketMap;
    }

    public Map<String, GetNameByIdWebSocket> getGetNameWebSocketMap() {
        return getNameWebSocketMap;
    }

    @PostConstruct
    private void initClient() {
        client.init();
        Msg msg = new PingMsg("Front");
        client.send(msg);
    }

    @Override
    public void handleRequestGetNameById(long id, GetNameByIdWebSocket webSocket) {
        getNameWebSocketMap.put(webSocket.getId(), webSocket);
        Msg message = new GetNameMsg(webSocket.getId(),id );
        client.send(message);
    }


    @Override
    public void getNameById(String name, String webSocket) {
        getNameWebSocketMap.get(webSocket).sendName(name);
    }

    @Override
    public void handleRequestCountUsers(CountUsersWebSocket webSocket) {
        countUsersWebSocketMap.put(webSocket.getId(), webSocket);
        Msg message = new GetCountUsersMsg(webSocket.getId());
        client.send(message);
    }

    @Override
    public void getCountUsers(int count, String webSocket) {
        countUsersWebSocketMap.get(webSocket).sendCountUsers(count);
    }

    @Override
    public void handleRequestAddUser(String user, AddUserWebSocket webSocket) {
        addUserWebSocketMap.put(webSocket.getId(), webSocket);
        Msg message = new AddUserMsg(webSocket.getId(), user);
        client.send(message);
    }

    @Override
    public void addUser(String id, String webSocket) {
        addUserWebSocketMap.get(webSocket).sendUserId(id);
    }

}
