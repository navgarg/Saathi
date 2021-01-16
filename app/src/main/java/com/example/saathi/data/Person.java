package com.example.saathi.data;

public class Person {
    private String name, info, uid, profession;

    public Person(String name, String info, String uid, String profession){
        this.info = info;
        this.name = name;
        this.uid = uid;
        this.profession = profession;
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
}
