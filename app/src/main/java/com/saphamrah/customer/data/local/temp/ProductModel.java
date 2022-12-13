package com.saphamrah.customer.data.local.temp;

import static com.saphamrah.customer.presentation.createRequest.CreateRequestActivity.sort_order;
import static com.saphamrah.customer.utils.Constants.MAX_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MAX_SELL_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_CONSUMER_PRICE;
import static com.saphamrah.customer.utils.Constants.MIN_SELL_PRICE;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductModel implements Parcelable ,Comparable<ProductModel>{


    private int id;
    private String nameProduct;
    private long consumerPrice;
    private long sellPrice;
    private String codeKala;
    private long inventory;
    private String productionDate;
    private String expirationDate;
    private List<Integer> imageResource;
    private boolean isAd;
    private int boxCount;
    private int packCount;
    private int numCount;
    private int numInPack;
    private int numInBox;
    private int orderCount;
    

    private int weight;
    private String sazmanForosh;
    private int ccSazmanForosh;
    private int gorohKalaId;
    private int brandId;


    public ProductModel(int id,int brandId,int gorohKalaId, String nameProduct, long consumerPrice, long sellPrice, String codeKala, long inventory, String productionDate, String expirationDate, List<Integer> imageResource, boolean isAd, int boxCount, int packCount, int numCount, int numInPack, int numInBox,int orderCount, int weight, String sazmanForosh,int ccSazmanForosh) {
        this.id = id;
        this.gorohKalaId = gorohKalaId;
        this.brandId = brandId;
        this.nameProduct = nameProduct;
        this.consumerPrice = consumerPrice;
        this.sellPrice = sellPrice;
        this.codeKala = codeKala;
        this.inventory = inventory;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.imageResource = imageResource;
        this.isAd = isAd;
        this.boxCount = boxCount;
        this.packCount = packCount;
        this.numCount = numCount;
        this.numInPack = numInPack;
        this.numInBox = numInBox;
        this.orderCount = orderCount;
        this.weight = weight;
        this.sazmanForosh = sazmanForosh;
        this.ccSazmanForosh = ccSazmanForosh;
    }

    protected ProductModel(Parcel in) {
        id = in.readInt();
        brandId = in.readInt();
        gorohKalaId = in.readInt();
        nameProduct = in.readString();
        consumerPrice = in.readLong();
        sellPrice = in.readLong();
        codeKala = in.readString();
        inventory = in.readLong();
        productionDate = in.readString();
        expirationDate = in.readString();
        isAd = in.readByte() != 0;
        boxCount = in.readInt();
        packCount = in.readInt();
        numCount = in.readInt();
        numInPack = in.readInt();
        orderCount = in.readInt();
        weight = in.readInt();
        sazmanForosh = in.readString();
        numInBox = in.readInt();
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


    public int getCcSazmanForosh() {
        return ccSazmanForosh;
    }

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

    public String getCodeKala() {
        return codeKala;
    }

    public void setCodeKala(String codeKala) {
        this.codeKala = codeKala;
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

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
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

    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }

    public int getPackCount() {
        return packCount;
    }

    public void setPackCount(int packCount) {
        this.packCount = packCount;
    }

    public int getNumCount() {
        return numCount;
    }

    public void setNumCount(int numCount) {
        this.numCount = numCount;
    }

    public int getNumInPack() {
        return numInPack;
    }

    public void setNumInPack(int numInPack) {
        this.numInPack = numInPack;
    }

    public int getGorohKalaId() {
        return gorohKalaId;
    }

    public void setGorohKalaId(int gorohKalaId) {
        this.gorohKalaId = gorohKalaId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeInt(brandId);
        parcel.writeInt(gorohKalaId);
        parcel.writeString(nameProduct);
        parcel.writeLong(consumerPrice);
        parcel.writeLong(sellPrice);
        parcel.writeString(codeKala);
        parcel.writeLong(inventory);
        parcel.writeString(productionDate);
        parcel.writeString(expirationDate);
        parcel.writeByte((byte) (isAd ? 1 : 0));
        parcel.writeInt(boxCount);
        parcel.writeInt(packCount);
        parcel.writeInt(numCount);
        parcel.writeInt(numInPack);
        parcel.writeInt(orderCount);
        parcel.writeInt(weight);
        parcel.writeString(sazmanForosh);
        parcel.writeInt(numInBox);
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                "brandId=" + brandId +
                "gorohkalaId" + gorohKalaId +
                ", nameProduct='" + nameProduct + '\'' +
                ", consumerPrice=" + consumerPrice +
                ", sellPrice=" + sellPrice +
                ", codeKala='" + codeKala + '\'' +
                ", inventory=" + inventory +
                ", productionDate='" + productionDate + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", imageResource=" + imageResource +
                ", isAd=" + isAd +
                ", boxCount=" + boxCount +
                ", packCount=" + packCount +
                ", numCount=" + numCount +
                ", numInPack=" + numInPack +
                ", numInBox=" + numInBox +
                ", orderCount=" + orderCount +
                ", weight=" + weight +
                ", sazmanForosh='" + sazmanForosh + '\'' +
                '}';
    }


    @Override
    public int compareTo(ProductModel productModel) {
        switch (sort_order){
            case MAX_SELL_PRICE:
                if (this.sellPrice < productModel.sellPrice)
                    return 1;
                else
                    return -1;
            case MIN_SELL_PRICE:
                if (this.sellPrice > productModel.sellPrice)
                    return 1;
                else
                    return -1;

            case MAX_CONSUMER_PRICE:
                if (this.consumerPrice < productModel.consumerPrice)
                    return 1;
                else
                    return -1;

            case MIN_CONSUMER_PRICE:
                if (this.consumerPrice > productModel.consumerPrice)
                    return 1;
                else
                    return -1;
        }

        return 0;
    }
}
