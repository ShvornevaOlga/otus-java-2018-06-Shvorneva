package ru.shoe.l151.app.messages;

import ru.shoe.l151.app.DBServiceWithMessage;
import ru.shoe.l151.app.MsgToDB;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.GetNameByIdWebSocket;

public class MsgGetNameById extends MsgToDB {
    private final long id;
    private final GetNameByIdWebSocket webSocket;

    public MsgGetNameById(Address from, Address to, long id, GetNameByIdWebSocket webSocket) {
        super(from, to);
        this.id = id;
        this.webSocket = webSocket;
    }

    @Override
    public void exec(DBServiceWithMessage dbService) {
        UserDataSet user =dbService.read(id);
        String name = user==null?"user not found":user.getName();
        dbService.getMS().sendMessage(new MsgGetNameByIdAnswer(getTo(), getFrom(), name ,webSocket));
    }
}
