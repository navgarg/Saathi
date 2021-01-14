package com.example.saathi.data;

public class Person {
    private String name, info, uid;

    public Person(String name, String info, String uid){
        this.info = info;
        this.name = name;
        this.uid = uid;
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
}
