package ru.shoe.l101myorm.base;

public class UsersDataSet extends DataSet {
    private String name;
    private int age;

    public UsersDataSet() {
    }

    public UsersDataSet(String name, int age) {
        this.name = name;
        this.age = age;
    }


    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
