package ru.shoe.l151.app.messages;

import ru.shoe.l151.app.FrontendService;
import ru.shoe.l151.app.MsgToFrontend;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.GetNameByIdWebSocket;

public class MsgGetNameByIdAnswer extends MsgToFrontend {
    private final String name;
    private final GetNameByIdWebSocket webSocket;

    public MsgGetNameByIdAnswer(Address from, Address to, String name, GetNameByIdWebSocket webSocket) {
        super(from, to);
        this.name = name;
        this.webSocket = webSocket;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.getNameById(name, webSocket);
    }
}
