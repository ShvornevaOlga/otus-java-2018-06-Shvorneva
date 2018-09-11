package ru.shoe.lo71atm;

public enum Banknote {
    BANKNOTE_50(50), BANKNOTE_100(100), BANKNOTE_500(500), BANKNOTE_1000(1000), BANKNOTE_5000(5000);
    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    int getNominal() {
        return nominal;
    }
}
