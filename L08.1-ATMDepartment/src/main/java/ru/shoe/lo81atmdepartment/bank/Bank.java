package ru.shoe.lo81atmdepartment.bank;

import ru.shoe.lo81atmdepartment.Client;

public interface Bank {
    void depositBill(Client client, long sum);

    void creditBill(Client client, long sum);

    long balance(Client client);

    void addBill(Client client);
}
