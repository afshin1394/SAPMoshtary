package com.saphamrah.customer.data.local.temp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FilterSortModel implements Parcelable {

    private int id;
    private int type;
    private String name;
    private int parent_id;
    private boolean isEnabled;
    private boolean isExpanded;

    public FilterSortModel(int id, int type, String name, int parent_id,boolean isEnabled) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.parent_id = parent_id;
        this.isEnabled = isEnabled;
    }

    protected FilterSortModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        name = in.readString();
        parent_id = in.readInt();
        isEnabled = in.readByte() != 0;
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

    @Override
    public String toString() {
        return "FilterSortModel{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", parent_id=" + parent_id +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(type);
        parcel.writeString(name);
        parcel.writeInt(parent_id);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }
}
