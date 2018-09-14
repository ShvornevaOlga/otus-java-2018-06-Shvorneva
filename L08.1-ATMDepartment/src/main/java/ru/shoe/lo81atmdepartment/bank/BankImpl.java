package ru.shoe.lo81atmdepartment.bank;

import ru.shoe.lo81atmdepartment.Client;

import java.util.HashMap;
import java.util.Map;

public class BankImpl implements Bank {
    private Map<Client, Bill> bills = new HashMap<>();

    @Override
    public void depositBill(Client client, long sum) {
        Bill bill = bills.get(client);
        bill.deposit(sum);
    }

    @Override
    public void creditBill(Client client, long sum) {
        Bill bill = bills.get(client);
        bill.credit(sum);
    }

    @Override
    public long balance(Client client) {
        return bills.get(client).getBalance();
    }

    @Override
    public void addBill(Client client) {
        bills.put(client, new Bill());
    }
}
