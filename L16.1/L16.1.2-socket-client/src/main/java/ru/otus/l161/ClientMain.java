package ru.otus.l161;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.l161.app.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.db.base.DBService;
import ru.otus.l161.messages.PingMsg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ClientMain {
    private static final Logger logger = Logger.getLogger(ClientMain.class.getName());

    private static final String HOST = "localhost";
    private static final int PORT = 5050;
    @Autowired
    @Qualifier("cachedDbService")
    private DBService dbService;

    @SuppressWarnings("InfiniteLoopStatement")
    void start() throws Exception {

        SocketMsgWorker client = new ClientSocketMsgWorker(HOST, PORT);
        client.init();
        Msg msg = new PingMsg("Db");
        client.send(msg);
        MsgExecutor msgExecutor = new MsgExecutor(dbService, client);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final Msg message = client.take();
                    msgExecutor.exec(message);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });

    }

}
