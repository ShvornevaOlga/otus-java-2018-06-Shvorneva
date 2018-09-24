package ru.shoe.lo81atmdepartment.atm.memento;

import ru.shoe.lo81atmdepartment.atm.ATM;
import ru.shoe.lo81atmdepartment.atm.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMOriginator {
    private Map<ATM, ATMMemento> memento = new HashMap<>();

    public void saveState(ATM atm) {
        memento.put(atm, atm.createAtmMemento());
    }

    public List<Cell> restoreState(ATM atm) {
        if (memento.containsKey(atm))
            return memento.get(atm).getState();
        return null;
    }
}
