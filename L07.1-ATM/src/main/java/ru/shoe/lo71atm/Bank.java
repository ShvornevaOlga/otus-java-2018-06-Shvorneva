package ru.shoe.lo71atm;

public interface Bank {
    void depositBill(long idClient, long sum);

    void creditBill(long idClient, long sum);

    long balance(long idClient);
}
