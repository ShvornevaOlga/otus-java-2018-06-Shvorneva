package ru.shoe.lo81atmdepartment.department;

import org.junit.Before;
import org.junit.Test;
import ru.shoe.lo81atmdepartment.Banknote;
import ru.shoe.lo81atmdepartment.Client;
import ru.shoe.lo81atmdepartment.atm.ATM;
import ru.shoe.lo81atmdepartment.atm.ATMExeption;
import ru.shoe.lo81atmdepartment.atm.ATMimplement;
import ru.shoe.lo81atmdepartment.atm.Cell;
import ru.shoe.lo81atmdepartment.atm.memento.ATMOriginator;
import ru.shoe.lo81atmdepartment.bank.Bank;
import ru.shoe.lo81atmdepartment.bank.BankImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ATMDepartmentImplementTest {
    private Bank bank;
    private Client client;
    private Map<Banknote, Integer> cash;
    private List<ATM> atmList;
    private ATMOriginator originator;
    private ATMDepartment department;
    private long sum;

    @Before
    public void setUp() throws Exception {
        bank = new BankImpl();
        originator = new ATMOriginator();
        atmList = new ArrayList<>();
        client = new Client();
        cash = new HashMap<>();
        cash.put(Banknote.BANKNOTE_50, 3);
        cash.put(Banknote.BANKNOTE_100, 8);
        client.withdrawMoney(cash);
        bank.addBill(client);
        sum = initATM();
        department = new ATMDepartmentImplement(atmList, originator);
    }

    private long initATM() {
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            ATM atm = new ATMimplement();
            atm.setBank(bank);
            List<Cell> cellList = new ArrayList<>();
            for (Banknote banknote : Banknote.values()) {
                Cell cell = new Cell(banknote);
                int count = 10 + i * 50;
                cell.setCount(count);
                cellList.add(cell);
                sum += banknote.getNominal() * count;
            }
            atm.setBanknotes(cellList);
            originator.saveState(atm);
            atmList.add(atm);
        }
        return sum;
    }

    @Test
    public void getSum() throws Exception {
        assertEquals(sum, department.getSum());
    }

    @Test
    public void encashment() throws Exception {
        Map<ATM, Map<Banknote, Integer>> cashMap = new HashMap<>();
        for (ATM atm : atmList) {
            cashMap.put(atm, atm.lookBanknotes());
        }
        useATM(client, cash);
        department.encashment();
        useATM(client, cash);
        department.encashment();
        Map<ATM, Map<Banknote, Integer>> newCashMap = new HashMap<>();
        for (ATM atm : atmList) {
            newCashMap.put(atm, atm.lookBanknotes());
        }
        assertEquals(cashMap, newCashMap);
    }

    private void useATM(Client client, Map<Banknote, Integer> cash) {
        ATM atmPut = atmList.get(0);
        atmPut.putMoneyToATM(client, cash);
        ATM atmGet = atmList.get(1);
        try {
            atmGet.getMoneyFromATM(client, 100);
        } catch (ATMExeption atmExeption) {
            System.out.println(atmExeption.getMessage());
        }
        ATM atmBalance = atmList.get(2);
        atmBalance.getAccountBalance(client);
        try {
            atmBalance.getMoneyFromATM(client, 500);
        } catch (ATMExeption atmExeption) {
            System.out.println(atmExeption.getMessage());
        }
    }
}