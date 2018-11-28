package ru.otus.l161;

import com.google.gson.Gson;
import ru.otus.l161.app.Msg;
import ru.otus.l161.channel.SocketMsgWorker;
import ru.otus.l161.db.base.DBService;
import ru.otus.l161.db.base.datasets.PhoneDataSet;
import ru.otus.l161.db.base.datasets.UserDataSet;
import ru.otus.l161.messages.*;

import java.util.HashSet;
import java.util.Set;

class MsgExecutor {
    private DBService dbService;
    private SocketMsgWorker client;

    MsgExecutor(DBService dbService, SocketMsgWorker client) {
        this.dbService = dbService;
        this.client = client;
    }

    void exec(Msg message) {
        if (message instanceof GetCountUsersMsg) {
            int count = dbService.readAll().size();
            Msg msg = new GetCountUsersAnswerMsg(count, ((GetCountUsersMsg) message).getFrom(), ((GetCountUsersMsg) message).getWebSocketId());
            client.send(msg);
        } else if (message instanceof AddUserMsg) {
            Gson gson = new Gson();
            UserDataSet user = gson.fromJson(((AddUserMsg)message).getUserData(), UserDataSet.class);
            Set<PhoneDataSet> phoneDataSets = user.getPhones();
            user.setPhones(new HashSet<>());
            phoneDataSets.forEach(user::addPhone);
            dbService.save(user);
            long userId = user.getId();
            Msg msg = new AddUserAnswerMsg(String.valueOf(userId), ((AddUserMsg) message).getFrom(), ((AddUserMsg) message).getWebSocketId());
            client.send(msg);
        }else if (message instanceof GetNameMsg) {
            String name  = dbService.read(((GetNameMsg)message).getUserId()).getName();
            Msg msg = new GetNameAnswerMsg(name, ((GetNameMsg) message).getFrom(), ((GetNameMsg) message).getWebSocketId());
            client.send(msg);
        }
    }
}
