package ru.otus.l161.messages;

import ru.otus.l161.app.Msg;
import ru.otus.l161.app.MsgToDataBase;
import ru.otus.l161.app.ServerDBService;
import ru.otus.l161.channel.SocketMsgWorker;

public class GetNameMsg extends MsgToDataBase {
    private long userId;

    public GetNameMsg() {
    }

    public GetNameMsg(String webSocketId, long userId) {
        super(webSocketId);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public void exec(ServerDBService dbService, SocketMsgWorker client) {
        String name = dbService.getName(userId);
        Msg msg = new GetNameAnswerMsg(name, getFrom(), getWebSocketId());
        client.send(msg);
    }

    @Override
    public String toString() {
        return "GetNameMsg{" +
                "userId=" + userId +
                ", from='" + from + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
