package ru.otus.l161.messages;

import ru.otus.l161.app.Msg;
import ru.otus.l161.app.MsgToDataBase;
import ru.otus.l161.app.ServerDBService;
import ru.otus.l161.channel.SocketMsgWorker;

public class AddUserMsg extends MsgToDataBase {
    private String userData;

    public AddUserMsg() {
    }

    public AddUserMsg(String webSocketId, String userData) {
        super(webSocketId);
        this.userData = userData;
    }

    public String getUserData() {
        return userData;
    }

    @Override
    public void exec(ServerDBService dbService, SocketMsgWorker client) {
        long userId =dbService.addUser(userData);
        Msg msg = new AddUserAnswerMsg(String.valueOf(userId), getFrom(), getWebSocketId());
        client.send(msg);
    }

    @Override
    public String toString() {
        return "AddUserMsg{" +
                "userData='" + userData + '\'' +
                ", from='" + from + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
