package ru.shoe.lo81atmdepartment.atm.memento;

import ru.shoe.lo81atmdepartment.atm.Cell;

import java.util.ArrayList;
import java.util.List;

public class ATMMemento {
    private final List<Cell> state = new ArrayList<>();

    public ATMMemento(List<Cell> state) {
        for (Cell cell : state) {
            Cell cellCopy = new Cell(cell.getBanknote(), cell.getCount());
            this.state.add(cellCopy);
        }
    }

    List<Cell> getState() {
        return state;
    }
}