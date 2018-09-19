package ru.shoe.lo81atmdepartment.atm;

import org.junit.Before;
import org.junit.Test;
import ru.shoe.lo81atmdepartment.Banknote;
import ru.shoe.lo81atmdepartment.Client;
import ru.shoe.lo81atmdepartment.bank.Bank;
import ru.shoe.lo81atmdepartment.bank.BankImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ATMimplementTest {
    private ATM atm;
    private Client client;
    private Map<Banknote, Integer> cash;
    private Bank bankImpl;

    @Before
    public void setUp() throws Exception {
        bankImpl = new BankImpl();
        atm = new ATMimplement();
        atm.setBank(bankImpl);
        List<Cell> cellList = new ArrayList<>();
        for (Banknote banknote : Banknote.values()) {
            Cell cell = new Cell(banknote, 100);
            cellList.add(cell);
        }
        atm.setBanknotes(cellList);
        client = new Client();
        bankImpl.addBill(client);
        cash = new HashMap<>();
        cash.put(Banknote.BANKNOTE_50, 3);
        cash.put(Banknote.BANKNOTE_100, 8);
        client.withdrawMoney(cash);
    }

    @Test
    public void putMoneyToATM() throws Exception {
        Map<Banknote, Integer> newBanknotes = atm.lookBanknotes();
        long sum = 0;
        for (Banknote banknote : cash.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) + cash.get(banknote));
            sum += banknote.getNominal() * cash.get(banknote);
        }
        long newBalance = bankImpl.balance(client) + sum;
        atm.putMoneyToATM(client, cash);
        assertEquals(newBanknotes, atm.lookBanknotes());
        assertEquals(newBalance, bankImpl.balance(client));
    }

    @Test
    public void getMoneyFromATM() throws Exception {
        atm.putMoneyToATM(client, cash);
        Map<Banknote, Integer> newBanknotes = atm.lookBanknotes();
        int sum = 750;
        Map<Banknote, Integer> needBanknotes = new HashMap<>();
        needBanknotes.put(Banknote.BANKNOTE_50, 1);
        needBanknotes.put(Banknote.BANKNOTE_100, 2);
        needBanknotes.put(Banknote.BANKNOTE_500, 1);
        for (Banknote banknote : needBanknotes.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) - needBanknotes.get(banknote));
        }
        long newAmount = atm.getAmount();
        newAmount -= sum;
        long newBalance = bankImpl.balance(client) - sum;
        atm.getMoneyFromATM(client, sum);
        assertEquals(newAmount, atm.getAmount());
        assertEquals(newBalance, bankImpl.balance(client));
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
        long balance = bankImpl.balance(client);
        Map<Banknote, Integer> banknotes = new HashMap<>();
        banknotes.put(Banknote.BANKNOTE_50, 1);
        banknotes.put(Banknote.BANKNOTE_100, 4);
        banknotes.put(Banknote.BANKNOTE_500, 1);
        for (Banknote banknote : banknotes.keySet()) {
            newBanknotes.put(banknote, newBanknotes.get(banknote) - banknotes.get(banknote));
        }
        long newAmount = atm.getAmount();
        newAmount -= balance;
        long newBalance = 0;
        atm.getAccountBalance(client);
        assertEquals(newAmount, atm.getAmount());
        assertEquals(newBalance, bankImpl.balance(client));
        assertEquals(newBanknotes, atm.lookBanknotes());
    }
}