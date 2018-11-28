package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToFront;

public class AddUserAnswerMsg extends MsgToFront {
    private String userId;

    public AddUserAnswerMsg() {
    }

    public AddUserAnswerMsg(String userId, String to, String webSocketID) {
        super(to, webSocketID);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "AddUserAnswerMsg{" +
                "userId='" + userId + '\'' +
                ", to='" + to + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
