package ru.shoe.lo71atm;

public interface Bank {
    void depositBill(Client client, long sum);

    void creditBill(Client client, long sum);

    long balance(Client client);

    void addBill(Client client);
}
