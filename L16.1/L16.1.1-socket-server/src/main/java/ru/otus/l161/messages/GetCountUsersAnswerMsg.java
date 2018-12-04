package ru.otus.l161.messages;

import ru.otus.l161.app.MsgToFront;
import ru.otus.l161.app.ServerFrontService;

public class GetCountUsersAnswerMsg extends MsgToFront {
    private int count;

    public GetCountUsersAnswerMsg() {
    }

    public GetCountUsersAnswerMsg(int count, String to, String webSocketID) {
        super(to, webSocketID);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void exec(ServerFrontService frontService) {
        frontService.getCountUsers(getCount(), getWebSocketId());
    }

    @Override
    public String toString() {
        return "GetCountUsersAnswerMsg{" +
                "count=" + count +
                ", to='" + to + '\'' +
                ", webSocketId='" + webSocketId + '\'' +
                '}';
    }
}
