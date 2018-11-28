package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToDataBase;

public class GetCountUsersMsg extends MsgToDataBase {

    public GetCountUsersMsg(){}
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
}
