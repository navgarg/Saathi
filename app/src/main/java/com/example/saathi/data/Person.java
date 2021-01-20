package com.example.saathi.data;

public class Person {
    private final String name;
    private final String info;
    private final String uid;
    private final String profession;
    private final String phone;
    private String color;

    public Person(String name, String info, String uid, String profession, String phone){
        this.info = info;
        this.name = name;
        this.uid = uid;
        this.profession = profession;
        this.phone = phone;
    }

    public Person(String name,  String uid,  String profession, String info,String phone, String color){
        this.info = info;
        this.name = name;
        this.uid = uid;
        this.color = color;
        this.profession = profession;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getUid() {
        return uid;
    }

    public String getProfession() {
        return profession;
    }

    public String getColor() {
        return color;
    }

    public String getPhone() {
        return phone;
    }
}
