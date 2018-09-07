package ru.shoe.lo71atm;

public enum Banknote {
    banknote_50(50), banknote_100(100), banknote_500(500), banknote_1000(1000), banknote_5000(5000);
    int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }

    int getNominal() {
        return nominal;
    }
}
