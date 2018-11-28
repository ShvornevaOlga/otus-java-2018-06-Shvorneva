package ru.otus.l161.app;

public abstract class MsgToDataBase extends Msg {
    protected String from;
    protected String webSocketId;

    protected MsgToDataBase() {
    }

    public MsgToDataBase(String webSocketId) {
        this.webSocketId = webSocketId;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getWebSocketId() {
        return webSocketId;
    }

    public String getFrom() {
        return from;
    }
}
