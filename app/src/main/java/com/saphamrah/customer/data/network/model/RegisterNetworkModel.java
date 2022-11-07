package com.saphamrah.customer.data.network.model;

import android.util.Pair;

public class RegisterNetworkModel {
    private String firstName;
    private String lastName;
    private String mobile;
    private String boardName;
    private String identity;
    private String nationalCode;
    private String province;
    private String city;
    private String mainStreet;
    private String mainAlley;
    private String plaque;
    private String postalCode;
    private Pair<Double, Double> latLng;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMainStreet() {
        return mainStreet;
    }

    public void setMainStreet(String mainStreet) {
        this.mainStreet = mainStreet;
    }

    public String getMainAlley() {
        return mainAlley;
    }

    public void setMainAlley(String mainAlley) {
        this.mainAlley = mainAlley;
    }

    public String getPlaque() {
        return plaque;
    }

    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Pair<Double, Double> getLatLng() {
        return latLng;
    }

    public void setLatLng(Pair<Double, Double> latLng) {
        this.latLng = latLng;
    }
}
