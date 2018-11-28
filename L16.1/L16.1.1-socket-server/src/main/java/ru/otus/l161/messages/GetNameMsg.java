package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToDataBase;

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
    public String toString() {
        return "GetNameMsg{" +
                "userId=" + userId +
                ", from='" + from + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
