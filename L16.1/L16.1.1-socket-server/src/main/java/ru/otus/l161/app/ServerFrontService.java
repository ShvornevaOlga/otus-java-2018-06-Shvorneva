package ru.otus.l161.app;

public interface ServerFrontService {
    void getNameById(String name, String webSocket);

    void getCountUsers(int count, String webSocket);

    void addUser(String id, String webSocket);
}
