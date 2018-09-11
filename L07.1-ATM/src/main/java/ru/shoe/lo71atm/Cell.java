package ru.shoe.lo71atm;

public class Cell {
        private Banknote banknote;
        private int count;
        public Cell(Banknote banknote){
            this.banknote = banknote;
        }

    public Banknote getBanknote() {
        return banknote;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
