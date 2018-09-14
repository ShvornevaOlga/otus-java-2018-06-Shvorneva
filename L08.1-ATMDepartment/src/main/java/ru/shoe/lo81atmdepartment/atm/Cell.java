package ru.shoe.lo81atmdepartment.atm;

import ru.shoe.lo81atmdepartment.Banknote;

public class Cell {
    private Banknote banknote;
    private int count;

    public Cell(Banknote banknote) {
        this.banknote = banknote;
    }

    Banknote getBanknote() {
        return banknote;
    }

    int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return banknote.getNominal() + "руб:" + count;
    }
}
