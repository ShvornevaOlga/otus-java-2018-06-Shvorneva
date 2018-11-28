package ru.otus.l161.db.base.datasets;


import ru.otus.l161.db.base.DataSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "adressDataSet")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdressDataSet)) return false;
        AdressDataSet that = (AdressDataSet) o;
        return Objects.equals(getAdress(), that.getAdress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAdress());
    }

    @Override
    public String toString() {
        return "AdressDataSet{" +
                "adress='" + adress + '\'' +
                '}';
    }
}
