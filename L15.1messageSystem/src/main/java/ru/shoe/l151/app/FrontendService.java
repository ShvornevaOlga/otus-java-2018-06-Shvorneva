package ru.shoe.l151.app;

import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Addressee;
import ru.shoe.l151.front.websocket.AddUserWebSocket;
import ru.shoe.l151.front.websocket.CountUsersWebSocket;
import ru.shoe.l151.front.websocket.GetNameByIdWebSocket;

public interface FrontendService extends Addressee {
    void init();

    void handleRequestGetNameById(long id, GetNameByIdWebSocket webSocket);

    void getNameById(String name, GetNameByIdWebSocket webSocket);

    void handleRequestCountUsers(CountUsersWebSocket webSocket);

    void getCountUsers(String count, CountUsersWebSocket webSocket);

    void handleRequestAddUser(UserDataSet user, AddUserWebSocket webSocket);

    void addUser(String id, AddUserWebSocket webSocket);
}

