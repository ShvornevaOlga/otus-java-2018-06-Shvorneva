package ru.otus.l161;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.l161.app.MsgToFront;
import ru.otus.l161.app.ServerFrontService;
import ru.otus.l161.channel.SocketMsgWorker;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MsgExecutor {
    private static final Logger logger = Logger.getLogger(Math.class.getName());
    @Autowired
    private ServerFrontService frontendService;
    @Autowired
    private SocketMsgWorker client;

    public MsgExecutor(ServerFrontService frontendService, SocketMsgWorker client) {
        this.frontendService = frontendService;
        this.client = client;
    }

    @PostConstruct
    private void start() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final MsgToFront msg = (MsgToFront) client.take();
                    msg.exec(frontendService);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

}
