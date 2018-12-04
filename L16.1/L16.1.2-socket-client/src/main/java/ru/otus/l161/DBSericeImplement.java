package ru.otus.l161;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.l161.app.ServerDBService;
import ru.otus.l161.db.base.DBService;
import ru.otus.l161.db.base.datasets.PhoneDataSet;
import ru.otus.l161.db.base.datasets.UserDataSet;

import java.util.HashSet;
import java.util.Set;

@Service
public class DBSericeImplement implements ServerDBService {
    @Autowired
    @Qualifier("cachedDbService")
    private DBService dbService;

    @Override
    public int getCountUsers() {
        return dbService.readAll().size();
    }

    @Override
    public long addUser(String userData) {
        Gson gson = new Gson();
        UserDataSet user = gson.fromJson(userData, UserDataSet.class);
        Set<PhoneDataSet> phoneDataSets = user.getPhones();
        user.setPhones(new HashSet<>());
        phoneDataSets.forEach(user::addPhone);
        dbService.save(user);
        return user.getId();
    }

    @Override
    public String getName(long userId) {
        UserDataSet user = dbService.read(userId);
        return user == null ? "user not found" : user.getName();
    }
}
