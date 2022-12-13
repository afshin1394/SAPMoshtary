package com.saphamrah.customer.data.local.temp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class FilterSortModel implements Parcelable  {

    private int id;
    private int type;
    private int filterType;
    private String name;
    private int parent_id;
    private boolean isEnabled;
    private boolean isExpanded;

    public FilterSortModel(int id, int type,int filterType, String name, int parent_id,boolean isEnabled) {
        this.id = id;
        this.type = type;
        this.filterType = filterType;
        this.name = name;
        this.parent_id = parent_id;
        this.isEnabled = isEnabled;
    }


    protected FilterSortModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        filterType = in.readInt();
        name = in.readString();
        parent_id = in.readInt();
        isEnabled = in.readByte() != 0;
        isExpanded = in.readByte() != 0;
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
}
