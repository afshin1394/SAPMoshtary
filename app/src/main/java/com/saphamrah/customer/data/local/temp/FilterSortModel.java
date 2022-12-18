package com.saphamrah.customer.data.local.temp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class FilterSortModel implements Parcelable , Comparable<FilterSortModel>  {

    private int id;
    private int type;
    private int filterType;
    private String name;
    private int parent_id;
    private boolean isEnabled;
    private boolean isExpanded;
    private int maxValue = 0;
    private int minValue = 0;
    private int filterSortType;

    public FilterSortModel(int id, int type,int filterType, String name, int parent_id,boolean isEnabled,int filterSortType) {
        this.id = id;
        this.type = type;
        this.filterType = filterType;
        this.name = name;
        this.parent_id = parent_id;
        this.isEnabled = isEnabled;
        this.filterSortType = filterSortType;
    }



    protected FilterSortModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        filterType = in.readInt();
        name = in.readString();
        parent_id = in.readInt();
        isEnabled = in.readByte() != 0;
        isExpanded = in.readByte() != 0;
        minValue = in.readInt();
        maxValue = in.readInt();
        filterSortType = in.readInt();
    }

    public static final Creator<FilterSortModel> CREATOR = new Creator<FilterSortModel>() {
        @Override
        public FilterSortModel createFromParcel(Parcel in) {
            return new FilterSortModel(in);
        }

        @Override
        public FilterSortModel[] newArray(int size) {
            return new FilterSortModel[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getFilterSortType() {
        return filterSortType;
    }

    public void setFilterSortType(int filterSortType) {
        this.filterSortType = filterSortType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(type);
        parcel.writeInt(filterType);
        parcel.writeString(name);
        parcel.writeInt(parent_id);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
        parcel.writeByte((byte) (isExpanded ? 1 : 0));
        parcel.writeInt(minValue);
        parcel.writeInt(maxValue);
        parcel.writeInt(filterSortType);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterSortModel)) return false;
        FilterSortModel that = (FilterSortModel) o;
        return getId() == that.getId() && getType() == that.getType() && getFilterType() == that.getFilterType() && getParent_id() == that.getParent_id() && isEnabled() == that.isEnabled() && isExpanded() == that.isExpanded() && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getFilterType(), getName(), getParent_id(), isEnabled(), isExpanded());
    }

    @Override
    public int compareTo(FilterSortModel filterSortModel) {
        if (this.id == filterSortModel.getId())
        return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return "FilterSortModel{" +
                "id=" + id +
                ", type=" + type +
                ", filterType=" + filterType +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", isEnabled=" + isEnabled +
                ", isExpanded=" + isExpanded +
                ", maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", filterSortType=" + filterSortType +
                '}';
    }
}
