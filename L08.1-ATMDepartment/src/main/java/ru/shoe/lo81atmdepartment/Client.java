package ru.shoe.lo81atmdepartment;

import java.util.HashMap;
import java.util.Map;

public class Client {
    private Map<Banknote, Integer> cash;

    //==================================================================================================================
    //============================constructors================================================================
    //==================================================================================================================
    public Client() {
        cash = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            cash.put(banknote, 0);
        }
    }

    //==================================================================================================================
    //==========================methods======================================================================
    //==================================================================================================================
    public void withdrawMoney(Map<Banknote, Integer> getBanknotes) {
        for (Banknote banknote : cash.keySet()) {
            int quantityClientBanknote = cash.get(banknote);
            int quantityPayBanknote = 0;
            if (getBanknotes.containsKey(banknote))
                quantityPayBanknote = getBanknotes.get(banknote);
            cash.put(banknote, quantityClientBanknote + quantityPayBanknote);
        }
    }

    public void putMoney(Map<Banknote, Integer> putBanknotes) {
        for (Banknote banknote : cash.keySet()) {
            int quantityClientBanknote = cash.get(banknote);
            int quantityPayBanknote = 0;
            if (putBanknotes.containsKey(banknote))
                quantityPayBanknote = putBanknotes.get(banknote);
            cash.put(banknote, quantityClientBanknote - quantityPayBanknote);
        }
    }
}
