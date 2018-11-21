package ru.shoe.l151.app.messages;


import ru.shoe.l151.app.FrontendService;
import ru.shoe.l151.app.MsgToFrontend;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.AddUserWebSocket;


public class MsgAddUserAnswer extends MsgToFrontend {
    private final String userId;
    private final AddUserWebSocket webSocket;

    MsgAddUserAnswer(Address from, Address to, String userId, AddUserWebSocket webSocket) {
        super(from, to);
        this.userId = userId;
        this.webSocket = webSocket;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(userId, webSocket);
    }
}
