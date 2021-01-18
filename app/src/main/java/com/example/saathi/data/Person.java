package com.example.saathi.data;

public class Person {
    private final String name, info, uid, profession, phone;
    private boolean isCritical;

    public Person(String name, String info, String uid, String profession, String phone){
        this.info = info;
        this.name = name;
        this.uid = uid;
        this.profession = profession;
        this.phone = phone;
    }

    public Person(String name, String info, String uid, String profession, String phone, boolean isCritical){
        this.info = info;
        this.name = name;
        this.uid = uid;
        this.profession = profession;
        this.isCritical = isCritical;
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

    public boolean getIsCritical() {
        return isCritical;
    }

    public String getPhone() {
        return phone;
    }
}
