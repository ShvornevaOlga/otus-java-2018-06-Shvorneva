package ru.shoe.l151.app;

import ru.shoe.l151.messageSystem.Address;
import ru.shoe.l151.messageSystem.Addressee;
import ru.shoe.l151.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBServiceWithMessage) {
            exec((DBServiceWithMessage) addressee);
        }
    }

    public abstract void exec(DBServiceWithMessage dbService);
}
