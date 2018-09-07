package ru.shoe.lo71atm;

class Bill {
    private long balance;

    long getBalance() {
        return balance;
    }

    void deposit(long sum) {
        balance += sum;
    }

    void credit(long sum) {
        balance -= sum;
    }
}
