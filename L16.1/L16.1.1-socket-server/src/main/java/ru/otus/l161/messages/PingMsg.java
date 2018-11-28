package ru.otus.l161.messages;

import ru.otus.l161.app.Msg;

public class PingMsg extends Msg {
    private String socketId;

    public PingMsg() {
    }

    public PingMsg(String socketId) {
        super();
        this.socketId = socketId;
    }

    public String getSocketId() {
        return socketId;
    }

    @Override
    public String toString() {
        return "PingMsg{" +
                "socketId='" + socketId + '\'' +
                '}';
    }
}
