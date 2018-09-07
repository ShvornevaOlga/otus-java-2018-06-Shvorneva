package ru.shoe.lo71atm;

import java.util.HashMap;
import java.util.Map;

class BankImpl implements Bank {
    private Map<Long, Bill> bills = new HashMap<>();

    @Override
    public void depositBill(long idClient, long sum) {
        Bill bill = bills.get(idClient);
        bill.deposit(sum);
    }

    @Override
    public void creditBill(long idClient, long sum) {
        Bill bill = bills.get(idClient);
        bill.credit(sum);
    }

    @Override
    public long balance(long idClient) {
        return bills.get(idClient).getBalance();
    }

    void addBill(long idClient) {
        bills.put(idClient, new Bill());
    }
}
