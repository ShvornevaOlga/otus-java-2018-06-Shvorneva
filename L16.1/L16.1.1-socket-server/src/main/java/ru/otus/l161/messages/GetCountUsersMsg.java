package ru.otus.l161.messages;

import ru.otus.l161.app.ServerDBService;
import ru.otus.l161.app.Msg;
import ru.otus.l161.app.MsgToDataBase;
import ru.otus.l161.channel.SocketMsgWorker;

public class GetCountUsersMsg extends MsgToDataBase {

    public GetCountUsersMsg() {
    }

    public GetCountUsersMsg(String webSocketId) {
        super(webSocketId);
    }

    @Override
    public String toString() {
        return "GetCountUsersMsg{" +
                "from='" + from + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }

    @Override
    public void exec(ServerDBService dbService, SocketMsgWorker client) {
        int count = dbService.getCountUsers();
        Msg msg = new GetCountUsersAnswerMsg(count, getFrom(), getWebSocketId());
        client.send(msg);
    }
}
