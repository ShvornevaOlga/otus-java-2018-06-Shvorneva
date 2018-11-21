package ru.shoe.l151.db.base.datasets;

import ru.shoe.l151.db.base.DataSet;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phoneDataSet")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;
    @ManyToOne
    @JoinColumn(name = "userDataSet_id")
    private UserDataSet userDataSet;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserDataSet getUserDataSet() {
        return userDataSet;
    }

    void setUserDataSet(UserDataSet userDataSet) {
        this.userDataSet = userDataSet;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneDataSet)) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNumber());
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
