package com.saphamrah.customer.data;

public class LocationDbModel extends BaseBottomSheetRecyclerModel {

    private String name;
    private String type;
    private int resId;
    private boolean isSelected = false;

    public LocationDbModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public LocationDbModel(String name, String type, int resId) {
        this.name = name;
        this.type = type;
        this.resId = resId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "LocationDbModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", resId=" + resId +
                ", isSelected=" + isSelected +
                '}';
    }
}
