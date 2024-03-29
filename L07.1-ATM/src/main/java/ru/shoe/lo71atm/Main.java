package ru.shoe.lo71atm;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Client client = new Client(1);
        Map<Banknote, Integer> cash = new HashMap<>();
        cash.put(Banknote.BANKNOTE_50, 3);
        cash.put(Banknote.BANKNOTE_100, 8);
        client.withdrawMoney(cash);

        Bank bank = new BankImpl();
        bank.addBill(client);

        ATM atm = new ATMimplement();
        atm.setBank(bank);
        Map<Banknote, Integer> banknotesInAtm = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            banknotesInAtm.put(banknote, 100);
        }
        atm.setBanknotes(banknotesInAtm);

        atm.putMoneyToATM(client, cash);
        try {
            atm.getMoneyFromATM(client, 100);
        } catch (ATMExeption atmExeption) {
            System.out.println(atmExeption.getMessage());
        }
        try {
            atm.getMoneyFromATM(client, 80);
        } catch (ATMExeption atmExeption) {
            System.out.println(atmExeption.getMessage());
        }
        atm.getAccountBalance(client);
        try {
            atm.getMoneyFromATM(client, 500);
        } catch (ATMExeption atmExeption) {
            System.out.println(atmExeption.getMessage());
        }
    }

}
