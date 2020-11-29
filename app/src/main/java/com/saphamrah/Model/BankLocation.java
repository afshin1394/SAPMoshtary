package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankLocation
{

    @SerializedName("NoeBank")
    @Expose
    private Integer noeBank;
    @SerializedName("ccBankShobehPosition")
    @Expose
    private Integer ccBankShobehPosition;
    @SerializedName("NameBank")
    @Expose
    private String nameBank;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Lat_Bank")
    @Expose
    private String latBank;
    @SerializedName("Lng_Bank")
    @Expose
    private String lngBank;
    @SerializedName("Distance")
    @Expose
    private Double distance;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public Integer getNoeBank() {
        return noeBank;
    }

    public void setNoeBank(Integer noeBank) {
        this.noeBank = noeBank;
    }

    public Integer getCcBankShobehPosition() {
        return ccBankShobehPosition;
    }

    public void setCcBankShobehPosition(Integer ccBankShobehPosition) {
        this.ccBankShobehPosition = ccBankShobehPosition;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatBank() {
        return latBank;
    }

    public void setLatBank(String latBank) {
        this.latBank = latBank;
    }

    public String getLngBank() {
        return lngBank;
    }

    public void setLngBank(String lngBank) {
        this.lngBank = lngBank;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
