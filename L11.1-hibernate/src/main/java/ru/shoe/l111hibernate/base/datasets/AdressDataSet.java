package ru.shoe.l111hibernate.base.datasets;

import ru.shoe.l111hibernate.base.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "adress")
public class AdressDataSet extends DataSet {
    @Column(name = "adress")
    private String adress;

    public AdressDataSet() {
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }

    public AdressDataSet(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "AdressDataSet{" +
                "adress='" + adress + '\'' +
                '}';
    }
}
