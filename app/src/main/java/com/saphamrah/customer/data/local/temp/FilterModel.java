package com.saphamrah.customer.data.local.temp;

public class FilterModel {
    private int id;
    private String title;
    private int type;
    private int parent_id;

    public FilterModel(int id, String title, int type, int parent_id) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.parent_id = parent_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
