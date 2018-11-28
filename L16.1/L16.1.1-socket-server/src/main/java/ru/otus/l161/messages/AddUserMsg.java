package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToDataBase;

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
    public String toString() {
        return "AddUserMsg{" +
                "userData='" + userData + '\'' +
                ", from='" + from + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
