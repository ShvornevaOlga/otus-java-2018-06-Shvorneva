package ru.shoe.l151.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shoe.l151.app.FrontendService;
import ru.shoe.l151.app.MessageSystemContext;
import ru.shoe.l151.app.messages.MsgAddUser;
import ru.shoe.l151.app.messages.MsgGetCountUsers;
import ru.shoe.l151.app.messages.MsgGetNameById;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.messageSystem.Message;
import ru.shoe.l151.messageSystem.MessageSystem;
import ru.shoe.l151.front.websocket.AddUserWebSocket;
import ru.shoe.l151.front.websocket.CountUsersWebSocket;
import ru.shoe.l151.front.websocket.GetNameByIdWebSocket;

import javax.annotation.PostConstruct;

@Service
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    @Autowired
    private MessageSystemContext context;

    public FrontendServiceImpl() {
        address = new Address("Front");
    }

    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
        context.setFrontAddress(getAddress());
        if (context.getDbAddress() != null)
            context.getMessageSystem().start();
    }

    @Override
    public Address getAddress() {
        return address;
    }


    @Override
    public void handleRequestGetNameById(long id, GetNameByIdWebSocket webSocket) {
        Message message = new MsgGetNameById(getAddress(), context.getDbAddress(), id, webSocket);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void getNameById(String name, GetNameByIdWebSocket webSocket) {
        webSocket.sendName(name);
    }

    @Override
    public void handleRequestCountUsers(CountUsersWebSocket webSocket) {
        Message message = new MsgGetCountUsers(getAddress(), context.getDbAddress(), webSocket);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void getCountUsers(String count, CountUsersWebSocket webSocket) {
        webSocket.sendCountUsers(count);
    }

    @Override
    public void handleRequestAddUser(UserDataSet user, AddUserWebSocket webSocket) {
        Message message = new MsgAddUser(getAddress(), context.getDbAddress(), user, webSocket);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void addUser(String id, AddUserWebSocket webSocket) {
        webSocket.sendUserId(id);
    }


    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
