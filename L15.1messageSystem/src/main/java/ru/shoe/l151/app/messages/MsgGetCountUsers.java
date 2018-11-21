package ru.shoe.l151.app.messages;

import ru.shoe.l151.app.DBServiceWithMessage;
import ru.shoe.l151.app.MsgToDB;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.CountUsersWebSocket;

import java.util.List;


public class MsgGetCountUsers extends MsgToDB {
    private final CountUsersWebSocket webSocket;

    public MsgGetCountUsers(Address from, Address to, CountUsersWebSocket webSocket) {
        super(from, to);
        this.webSocket = webSocket;
    }

    @Override
    public void exec(DBServiceWithMessage dbService) {
        List<UserDataSet> users = dbService.readAll();
        String count = String.valueOf(users.size());
        dbService.getMS().sendMessage(new MsgGetCountUsersAnswer(getTo(), getFrom(), count, webSocket));
    }
}
