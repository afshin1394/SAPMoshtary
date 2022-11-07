package com.saphamrah.customer.data.local.temp;

public class DiscountModel {
    private int id;
    private String description;
    private int percentage;
    private double amount;


    public DiscountModel(int id, String description, int percentage, double amount) {
        this.id = id;
        this.description = description;
        this.percentage = percentage;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
