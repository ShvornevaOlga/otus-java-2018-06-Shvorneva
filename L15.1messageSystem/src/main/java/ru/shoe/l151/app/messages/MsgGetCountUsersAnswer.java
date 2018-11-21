package ru.shoe.l151.app.messages;


import ru.shoe.l151.app.FrontendService;
import ru.shoe.l151.app.MsgToFrontend;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.front.websocket.CountUsersWebSocket;

public class MsgGetCountUsersAnswer extends MsgToFrontend {
    private final String count;
    private final CountUsersWebSocket webSocket;

    MsgGetCountUsersAnswer(Address from, Address to, String count, CountUsersWebSocket webSocket) {
        super(from, to);
        this.count = count;
        this.webSocket = webSocket;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.getCountUsers(count, webSocket);
    }
}
