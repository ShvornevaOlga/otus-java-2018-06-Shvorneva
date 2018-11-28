package ru.otus.l161;


import ru.otus.l161.front.websocket.AddUserWebSocket;
import ru.otus.l161.front.websocket.CountUsersWebSocket;
import ru.otus.l161.front.websocket.GetNameByIdWebSocket;

import java.util.Map;

public interface FrontendService {

    void handleRequestGetNameById(long id, GetNameByIdWebSocket webSocket);

    void getNameById(String name, String webSocket);

    void handleRequestCountUsers(CountUsersWebSocket webSocket);

    void getCountUsers(int count, String webSocket);

    void handleRequestAddUser(String user, AddUserWebSocket webSocket);

    void addUser(String id, String webSocket);

    Map<String, CountUsersWebSocket> getCountUsersWebSocketMap();

    Map<String, AddUserWebSocket> getAddUserWebSocketMap();

    Map<String, GetNameByIdWebSocket> getGetNameWebSocketMap();
}

