package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToFront;
import ru.otus.l161.app.ServerFrontService;

public class GetNameAnswerMsg extends MsgToFront {
    private String userName;

    public GetNameAnswerMsg() {
    }

    public GetNameAnswerMsg(String userName, String to, String webSocketID) {
        super(to, webSocketID);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void exec(ServerFrontService frontService) {
        frontService.getNameById(getUserName(), getWebSocketId());
    }

    @Override
    public String toString() {
        return "GetNameAnswerMsg{" +
                "userName='" + userName + '\'' +
                ", to='" + to + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
