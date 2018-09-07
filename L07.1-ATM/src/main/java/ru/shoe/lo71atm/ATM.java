package ru.shoe.lo71atm;

import java.util.Map;

public interface ATM {
    void putMoneyToATM(Client client, Map<Banknote, Integer> putBanknotes);

    void getMoneyFromATM(Client client, long sum) throws ATMExeption;

    void getAccountBalance(Client client);

    Map<Banknote, Integer> lookBanknotes();
}
