package ru.otus.l161;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.l161.app.ServerFrontService;

@Service
public class ServerFrontServiceImplement implements ServerFrontService {
    @Autowired
    private FrontendService frontendService;

    @Override
    public void getNameById(String name, String webSocket) {
        frontendService.getNameById(name, webSocket);
    }

    @Override
    public void getCountUsers(int count, String webSocket) {
        frontendService.getCountUsers(count, webSocket);
    }

    @Override
    public void addUser(String id, String webSocket) {
        frontendService.addUser(id, webSocket);
    }
}
