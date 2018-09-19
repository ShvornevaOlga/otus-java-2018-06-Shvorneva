package ru.shoe.lo81atmdepartment.atm;

import ru.shoe.lo81atmdepartment.*;
import ru.shoe.lo81atmdepartment.bank.Bank;

import java.util.List;
import java.util.Map;

public interface ATM {
    void putMoneyToATM(Client client, Map<Banknote, Integer> putBanknotes);

    void getMoneyFromATM(Client client, long sum) throws ATMExeption;

    void getAccountBalance(Client client);

    Map<Banknote, Integer> lookBanknotes();

    List<Cell> getBanknotes();

    void setBank(Bank bank);

    void setBanknotes(List<Cell> cellList);

    long getAmount();

}
