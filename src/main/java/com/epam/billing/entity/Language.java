package com.epam.billing.entity;

public class Language {

    private int id;
    private String shortName;
    private String fullName;

    public int getId() {
        return id;
    }

    public Language setId(int id) {
        this.id = id;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Language setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Language setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
