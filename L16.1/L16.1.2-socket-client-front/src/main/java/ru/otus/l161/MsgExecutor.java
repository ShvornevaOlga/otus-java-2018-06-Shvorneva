package ru.otus.l161;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.l161.app.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.messages.AddUserAnswerMsg;
import ru.otus.l161.messages.GetCountUsersAnswerMsg;
import ru.otus.l161.messages.GetNameAnswerMsg;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class MsgExecutor {
    private static final Logger logger = Logger.getLogger(Math.class.getName());
    @Autowired
    private FrontendService frontendService;
    @Autowired
    private SocketMsgWorker client;

    public MsgExecutor(FrontendService frontendService, SocketMsgWorker client) {
        this.frontendService = frontendService;
        this.client = client;
    }

    @PostConstruct
    private void start() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                while (true) {
                    final Msg msg = client.take();
                    this.exec(msg);
                }
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private void exec(Msg message) {
        if (message instanceof GetCountUsersAnswerMsg) {
            frontendService.getCountUsers(((GetCountUsersAnswerMsg) message).getCount(), ((GetCountUsersAnswerMsg) message).getWebSocketId());
        } else if (message instanceof AddUserAnswerMsg) {
            frontendService.addUser(((AddUserAnswerMsg) message).getUserId(), ((AddUserAnswerMsg) message).getWebSocketId());
        } else if (message instanceof GetNameAnswerMsg) {
            frontendService.getNameById(((GetNameAnswerMsg) message).getUserName(), ((GetNameAnswerMsg) message).getWebSocketId());
        }
    }
}
