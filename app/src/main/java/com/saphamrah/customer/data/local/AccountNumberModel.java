package com.saphamrah.customer.data.local;

public class AccountNumberModel {
    private String bank;
    private String type;
    private String number;

    public AccountNumberModel(String bank, String type, String number) {
        this.bank = bank;
        this.type = type;
        this.number = number;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
