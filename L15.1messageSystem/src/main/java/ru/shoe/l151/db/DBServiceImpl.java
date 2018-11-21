package ru.shoe.l151.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.shoe.l151.app.DBServiceWithMessage;
import ru.shoe.l151.app.MessageSystemContext;
import ru.shoe.l151.db.base.DBService;
import ru.shoe.l151.db.base.datasets.UserDataSet;
import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.messageSystem.MessageSystem;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by tully.
 */
@Service
public class DBServiceImpl implements DBServiceWithMessage {
    private final Address address;
    @Autowired
    private MessageSystemContext context;
    @Autowired
    @Qualifier("cachedDbService")
    private DBService dbService;

    public DBServiceImpl() {
        address = new Address("DB");
    }

    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
        context.setDbAddress(getAddress());
        if(context.getFrontAddress()!=null)
        context.getMessageSystem().start();
    }

    @Override
    public String getMetaData() {
        return dbService.getMetaData();
    }

    @Override
    public void save(UserDataSet dataSet) {
        dbService.save(dataSet);
    }

    @Override
    public UserDataSet read(long id) {
        return dbService.read(id);
    }

    @Override
    public UserDataSet readByName(String name) {
        return dbService.readByName(name);
    }

    @Override
    public List<UserDataSet> readAll() {
        return dbService.readAll();
    }

    @Override
    public void shutdown() {
        dbService.shutdown();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }


}
