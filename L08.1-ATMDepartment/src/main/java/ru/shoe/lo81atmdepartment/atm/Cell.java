package ru.shoe.lo81atmdepartment.atm;

import ru.shoe.lo81atmdepartment.Banknote;

public class Cell {
    private Banknote banknote;
    private int count;

    public Cell(Banknote banknote, int count) {
        this.banknote = banknote;
        this.count = count;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public int getCount() {
        return count;
    }

    void addBanknotesToCell(int count) {
        this.count += count;
    }

    void removeBanknotesFromCell(int count) {
        this.count -= count;
    }

    @Override
    public String toString() {
        return banknote.getNominal() + "руб:" + count;
    }
}
