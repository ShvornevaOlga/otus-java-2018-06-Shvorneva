package ru.shoe.lo81atmdepartment;

import ru.shoe.lo81atmdepartment.atm.ATM;
import ru.shoe.lo81atmdepartment.atm.ATMExeption;
import ru.shoe.lo81atmdepartment.atm.ATMimplement;
import ru.shoe.lo81atmdepartment.atm.Cell;
import ru.shoe.lo81atmdepartment.atm.memento.ATMOriginator;
import ru.shoe.lo81atmdepartment.bank.Bank;
import ru.shoe.lo81atmdepartment.bank.BankImpl;
import ru.shoe.lo81atmdepartment.department.ATMDepartment;
import ru.shoe.lo81atmdepartment.department.ATMDepartmentImplement;

import java.util.*;

public class Main {
    private static List<ATM> atmList = new ArrayList<>();
    private static ATMOriginator originator = new ATMOriginator();
    private  static Bank bank = new BankImpl();
    public static void main(String[] args) {
        Client client = new Client();
        Map<Banknote, Integer> cash = new HashMap<>();
        cash.put(Banknote.BANKNOTE_50, 3);
        cash.put(Banknote.BANKNOTE_100, 8);
        client.withdrawMoney(cash);
        bank.addBill(client);
        initATM();
        ATMDepartment department=new ATMDepartmentImplement(atmList, originator);
        useATM(client, cash);
        department.encashment();
        useATM(client, cash);
        department.encashment();
        System.out.println("Сумма во всех банкоматах: "+department.getSum()+"руб.");
    }

    private static void initATM() {
        for (int i = 0; i<3; i++){
            ATM atm = new ATMimplement();
            atm.setBank(bank);
            List<Cell> cellList = new ArrayList<>();
            for (Banknote banknote : Banknote.values()) {
                Cell cell = new Cell(banknote, 10+i*50);
                cellList.add(cell);
            }
            atm.setBanknotes(cellList);
            originator.saveState(atm);
            atmList.add(atm);
        }
    }

    private static void useATM(Client client, Map<Banknote, Integer> cash) {
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
