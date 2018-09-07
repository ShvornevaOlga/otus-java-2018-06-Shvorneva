package ru.shoe.lo71atm;

import java.util.HashMap;
import java.util.Map;

class Client {
    private long id;
    private Map<Banknote, Integer> cash;

    //==================================================================================================================
    //============================constructors================================================================
    //==================================================================================================================
    Client(long id) {
        this.id = id;
        cash = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            cash.put(banknote, 0);
        }
    }

    //==================================================================================================================
    //====================getters, setters=============================================================
    //==================================================================================================================
    long getId() {
        return id;
    }

    //==================================================================================================================
    //==========================methods======================================================================
    //==================================================================================================================
    void getMoney(Map<Banknote, Integer> getBanknotes) {
        for (Banknote banknote : cash.keySet()) {
            int quantityClientBanknote = cash.get(banknote);
            int quantityPayBanknote = 0;
            if (getBanknotes.containsKey(banknote))
                quantityPayBanknote = getBanknotes.get(banknote);
            cash.put(banknote, quantityClientBanknote + quantityPayBanknote);
        }
    }

    void putMoney(Map<Banknote, Integer> putBanknotes) {
        for (Banknote banknote : cash.keySet()) {
            int quantityClientBanknote = cash.get(banknote);
            int quantityPayBanknote = 0;
            if (putBanknotes.containsKey(banknote))
                quantityPayBanknote = putBanknotes.get(banknote);
            cash.put(banknote, quantityClientBanknote - quantityPayBanknote);
        }
    }
}
