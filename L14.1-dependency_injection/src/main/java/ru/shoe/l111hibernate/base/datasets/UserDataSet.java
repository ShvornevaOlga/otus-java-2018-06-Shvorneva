package ru.shoe.l111hibernate.base.datasets;

import ru.shoe.l111hibernate.base.DataSet;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "userDataSet")
public class UserDataSet extends DataSet {
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @OneToMany(mappedBy = "userDataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneDataSet> phones = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    private AdressDataSet adress;

    public UserDataSet() {
    }

    public void addPhone(PhoneDataSet phone) {
        phone.setUserDataSet(this);
        phones.add(phone);
    }

    public UserDataSet(String name, int age, AdressDataSet adress) {
        this.name = name;
        this.age = age;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public AdressDataSet getAdress() {
        return adress;
    }

    public void setAdress(AdressDataSet adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDataSet)) return false;
        UserDataSet that = (UserDataSet) o;
        return getAge() == that.getAge() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhones(), that.getPhones()) &&
                Objects.equals(getAdress(), that.getAdress());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAge(), getPhones(), getAdress());
    }

    @Override
    public String toString() {
        String adress = this.adress == null ? "" : this.adress.toString();
        return "UserDataSet{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phones.toString() + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}
