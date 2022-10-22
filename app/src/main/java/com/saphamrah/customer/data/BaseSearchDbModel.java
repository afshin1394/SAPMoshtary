package com.saphamrah.customer.data;

public class BaseSearchDbModel {

    private String name;

    public BaseSearchDbModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
