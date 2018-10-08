package ru.shoe.l111hibernate.base.datasets;

import ru.shoe.l111hibernate.base.DataSet;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class PhoneDataSet extends DataSet {
    @Column(name = "number")
    private String number;
   @ManyToOne
    @JoinColumn(name = "User_id")
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
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
