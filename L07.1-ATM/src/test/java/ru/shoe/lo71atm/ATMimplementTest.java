package ru.shoe.lo71atm;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ATMimplementTest {
    private ATMimplement atm;
    private Client client;
    private Map<Banknote, Integer> cash;
    private BankImpl bankImpl;

    @Before
    public void setUp() throws Exception {
        bankImpl = new BankImpl();
        atm = new ATMimplement();
        atm.setBank(bankImpl);
        Map<Banknote, Integer> banknotes = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            banknotes.put(banknote, 100);
        }
        atm.setBanknotes(banknotes);
        client = new Client(1);
        bankImpl.addBill(client.getId());
        cash = new HashMap<>();
        cash.put(Banknote.banknote_50, 3);
        cash.put(Banknote.banknote_100, 8);
        client.getMoney(cash);
    }

    @Test
    public void putMoneyToATM() throws Exception {
        Map<Banknote, Integer> newBanknotes = atm.lookBanknotes();
        long sum = 0;
        for (Banknote banknote : cash.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) + cash.get(banknote));
            sum += banknote.getNominal() * cash.get(banknote);
        }
        long newBalance = bankImpl.balance(client.getId()) + sum;
        atm.putMoneyToATM(client, cash);
        assertEquals(newBanknotes, atm.lookBanknotes());
        assertEquals(newBalance, bankImpl.balance(client.getId()));
    }

    @Test
    public void getMoneyFromATM() throws Exception {
        atm.putMoneyToATM(client, cash);
        Map<Banknote, Integer> newBanknotes = atm.lookBanknotes();
        int sum = 750;
        Map<Banknote, Integer> needBanknotes = new HashMap<>();
        needBanknotes.put(Banknote.banknote_50, 1);
        needBanknotes.put(Banknote.banknote_100, 2);
        needBanknotes.put(Banknote.banknote_500, 1);
        for (Banknote banknote : needBanknotes.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) - needBanknotes.get(banknote));
        }
        long newAmount = atm.getAmount();
        newAmount -= sum;
        long newBalance = bankImpl.balance(client.getId()) - sum;
        atm.getMoneyFromATM(client, sum);
        assertEquals(newAmount, atm.getAmount());
        assertEquals(newBalance, bankImpl.balance(client.getId()));
        assertEquals(newBanknotes, atm.lookBanknotes());
    }

    @Test
    public void getMuchSum() {
        atm.putMoneyToATM(client, cash);
        try {
            atm.getMoneyFromATM(client, 80);
        } catch (ATMExeption atmExeption) {
            assertEquals("Не корректная сумма", atmExeption.getMessage());
        }
    }

    @Test
    public void getNocorrectSum() {
        try {
            atm.getMoneyFromATM(client, 500);
        } catch (ATMExeption atmExeption) {
            assertEquals("Недостаточно средств", atmExeption.getMessage());
        }
    }

    @Test
    public void getAccountBalance() throws Exception {
        atm.putMoneyToATM(client, cash);
        Map<Banknote, Integer> newBanknotes = atm.lookBanknotes();
        long balance = bankImpl.balance(client.getId());
        Map<Banknote, Integer> banknotes = new HashMap<>();
        banknotes.put(Banknote.banknote_50, 1);
        banknotes.put(Banknote.banknote_100, 4);
        banknotes.put(Banknote.banknote_500, 1);
        for (Banknote banknote : banknotes.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) - banknotes.get(banknote));
        }
        long newAmount = atm.getAmount();
        newAmount -= balance;
        long newBalance = 0;
        atm.getAccountBalance(client);
        assertEquals(newAmount, atm.getAmount());
        assertEquals(newBalance, bankImpl.balance(client.getId()));
        assertEquals(newBanknotes, atm.lookBanknotes());
    }
}