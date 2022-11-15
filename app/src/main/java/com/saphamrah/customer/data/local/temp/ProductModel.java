package com.saphamrah.customer.data.local.temp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductModel implements Parcelable {
    private int id;
    private String nameProduct;
    private long consumerPrice;
    private long sellPrice;
    private String bachNumber;
    private long inventory;
    private long orderCount;
    private String productionDate;
    private String expirationDate;
    private List<Integer> imageResource;
    private boolean isAd;

    private int weight;
    private String sazmanForosh;
    private int numInBox;

    public ProductModel(int id, String nameProduct, long consumerPrice, long sellPrice,String bachNumber, long inventory,long orderCount,String productionDate,String expirationDate,List<Integer> imageResource,boolean isAd, String sazmanForosh, int weight, int numInBox) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.consumerPrice = consumerPrice;
        this.sellPrice = sellPrice;
        this.bachNumber = bachNumber;
        this.inventory = inventory;
        this.imageResource = imageResource;
        this.orderCount = orderCount;
        this.isAd = isAd;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.sazmanForosh = sazmanForosh;
        this.weight = weight;
        this.numInBox = numInBox;
    }


    protected ProductModel(Parcel in) {
        id = in.readInt();
        nameProduct = in.readString();
        consumerPrice = in.readLong();
        sellPrice = in.readLong();
        bachNumber = in.readString();
        inventory = in.readLong();
        orderCount = in.readLong();
        productionDate = in.readString();
        expirationDate = in.readString();
        sazmanForosh = in.readString();
        weight = in.readInt();
        numInBox = in.readInt();
        isAd = in.readByte() != 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getConsumerPrice() {
        return consumerPrice;
    }

    public void setConsumerPrice(long consumerPrice) {
        this.consumerPrice = consumerPrice;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBachNumber() {
        return bachNumber;
    }

    public void setBachNumber(String bachNumber) {
        this.bachNumber = bachNumber;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public List<Integer> getImageResource() {
        return imageResource;
    }

    public void setImageResource(List<Integer> imageResource) {
        this.imageResource = imageResource;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }

    public String getSazmanForosh() {
        return sazmanForosh;
    }

    public void setSazmanForosh(String sazmanForosh) {
        this.sazmanForosh = sazmanForosh;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getNumInBox() {
        return numInBox;
    }

    public void setNumInBox(int numInBox) {
        this.numInBox = numInBox;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nameProduct);
        parcel.writeLong(consumerPrice);
        parcel.writeLong(sellPrice);
        parcel.writeLong(orderCount);
        parcel.writeString(bachNumber);
        parcel.writeLong(inventory);
        parcel.writeString(productionDate);
        parcel.writeString(expirationDate);
        parcel.writeString(sazmanForosh);
        parcel.writeInt(weight);
        parcel.writeInt(numInBox);
        parcel.writeByte((byte) (isAd ? 1 : 0));
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", nameProduct='" + nameProduct + '\'' +
                ", consumerPrice=" + consumerPrice +
                ", sellPrice=" + sellPrice +
                ", bachNumber='" + bachNumber + '\'' +
                ", inventory=" + inventory +
                ", orderCount=" + orderCount +
                ", productionDate='" + productionDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", imageResource=" + imageResource +
                ", sazmanForosh=" + sazmanForosh +
                ", weight=" + weight +
                ", numInBox=" + numInBox +
                ", isAd=" + isAd +
                '}';
    }
}
