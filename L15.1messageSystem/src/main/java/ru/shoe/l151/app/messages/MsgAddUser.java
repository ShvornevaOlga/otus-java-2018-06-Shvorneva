package ru.shoe.l151.app.messages;

import ru.shoe.l151.app.DBServiceWithMessage;
import ru.shoe.l151.app.MsgToDB;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.AddUserWebSocket;

public class MsgAddUser extends MsgToDB {
    private final UserDataSet user;
    private final AddUserWebSocket webSocket;

    public MsgAddUser(Address from, Address to, UserDataSet user, AddUserWebSocket webSocket) {
        super(from, to);
        this.user = user;
        this.webSocket = webSocket;
    }

    @Override
    public void exec(DBServiceWithMessage dbService) {
        dbService.save(user);
        String name = String.valueOf(user.getId());
        dbService.getMS().sendMessage(new MsgAddUserAnswer(getTo(), getFrom(), name, webSocket));
    }
}
