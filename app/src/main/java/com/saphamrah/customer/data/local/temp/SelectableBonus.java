package com.saphamrah.customer.data.local.temp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SelectableBonus implements Parcelable {
    private int id;
    private String description;
    private int product_id;
    private int per;
    private int quantity;
    private List<ProductModel> productModels;

    public SelectableBonus(int id, String description, int product_id, int per, int quantity, List<ProductModel> productModels) {
        this.id = id;
        this.description = description;
        this.product_id = product_id;
        this.per = per;
        this.quantity = quantity;
        this.productModels = productModels;
    }


    protected SelectableBonus(Parcel in) {
        id = in.readInt();
        description = in.readString();
        product_id = in.readInt();
        per = in.readInt();
        quantity = in.readInt();
        productModels = in.createTypedArrayList(ProductModel.CREATOR);
    }

    public static final Creator<SelectableBonus> CREATOR = new Creator<SelectableBonus>() {
        @Override
        public SelectableBonus createFromParcel(Parcel in) {
            return new SelectableBonus(in);
        }

        @Override
        public SelectableBonus[] newArray(int size) {
            return new SelectableBonus[size];
        }
    };

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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    @Override
    public String toString() {
        return "SelectableBonusModel{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", product_id=" + product_id +
                ", per=" + per +
                ", quantity=" + quantity +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeInt(product_id);
        parcel.writeInt(per);
        parcel.writeInt(quantity);
        parcel.writeTypedList(productModels);
    }
}
