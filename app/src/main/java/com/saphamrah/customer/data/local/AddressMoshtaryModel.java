package com.saphamrah.customer.data.local;

public class AddressMoshtaryModel {
    private String address;
    private String noeDarkhast;
    private String phone;
    private String codePosti;

    public AddressMoshtaryModel(String address, String noeDarkhast, String phone, String codePosti) {
        this.address = address;
        this.noeDarkhast = noeDarkhast;
        this.phone = phone;
        this.codePosti = codePosti;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNoeDarkhast() {
        return noeDarkhast;
    }

    public void setNoeDarkhast(String noeDarkhast) {
        this.noeDarkhast = noeDarkhast;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCodePosti() {
        return codePosti;
    }

    public void setCodePosti(String codePosti) {
        this.codePosti = codePosti;
    }
}
