package ru.shoe.lo81atmdepartment.atm.memento;

import ru.shoe.lo81atmdepartment.atm.Cell;

import java.util.ArrayList;
import java.util.List;

class ATMMemento {
    private final List<Cell> state = new ArrayList<>();

    ATMMemento(List<Cell> state) {
        this.state.addAll(state);
    }

    List<Cell> getState() {
        return state;
    }
}
