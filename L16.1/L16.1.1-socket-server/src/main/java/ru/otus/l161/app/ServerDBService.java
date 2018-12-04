package ru.otus.l161.app;

public interface ServerDBService {
    int getCountUsers();

    long addUser(String userData);

    String getName(long userId);
}
