package ru.otus.l161.app;

public abstract class MsgToFront extends Msg {
    protected String to;
    protected String webSocketId;

    protected MsgToFront() {
    }

    public MsgToFront(String to, String webSocketId) {
        this.to = to;
        this.webSocketId = webSocketId;
    }

    public String getWebSocketId() {
        return webSocketId;
    }

    public String getTo() {
        return to;
    }
}
