package com.saphamrah.UIModel;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * save data of darkhastFaktor table and moshtary table
 */
public class CustomerDarkhastFaktorModel implements Parcelable
{

    private long ccDarkhastFaktor;
    private String TarikhFaktor;
    private int ShomarehDarkhast;
    private int ShomarehFaktor;
    private int ccForoshandeh;
    private int ccMoshtary;
    private String TarikhErsal;
    private double MablaghKhalesFaktor;
    private int CodeVazeiat;
    private int ExtraProp_IsOld;
    private int ExtraProp_IsSend;
    private String UniqID_Tablet;
    private String fullNameMoshtary;
    private String codeMoshtary;
    private boolean haveEmzaImage;
    private boolean haveFaktorImage;
    private int ccMoshtaryGharardad;
    private int moshtaryGharardadccSazmanForosh;
    private boolean hasReceiptImage;
    private String extraProp_Description;

    public CustomerDarkhastFaktorModel() {
    }


    protected CustomerDarkhastFaktorModel(Parcel in) {
        ccDarkhastFaktor = in.readLong();
        TarikhFaktor = in.readString();
        ShomarehDarkhast = in.readInt();
        ShomarehFaktor = in.readInt();
        ccForoshandeh = in.readInt();
        ccMoshtary = in.readInt();
        TarikhErsal = in.readString();
        MablaghKhalesFaktor = in.readDouble();
        CodeVazeiat = in.readInt();
        ExtraProp_IsOld = in.readInt();
        ExtraProp_IsSend = in.readInt();
        UniqID_Tablet = in.readString();
        fullNameMoshtary = in.readString();
        codeMoshtary = in.readString();
        haveEmzaImage = in.readByte() != 0;
        haveFaktorImage = in.readByte() != 0;
        ccMoshtaryGharardad = in.readInt();
        moshtaryGharardadccSazmanForosh = in.readInt();
        hasReceiptImage = in.readByte() != 0;
        extraProp_Description = in.readString();
    }

    public static final Creator<CustomerDarkhastFaktorModel> CREATOR = new Creator<CustomerDarkhastFaktorModel>() {
        @Override
        public CustomerDarkhastFaktorModel createFromParcel(Parcel in) {
            return new CustomerDarkhastFaktorModel(in);
        }

        @Override
        public CustomerDarkhastFaktorModel[] newArray(int size) {
            return new CustomerDarkhastFaktorModel[size];
        }
    };

    public long getCcDarkhastFaktor() {
        return ccDarkhastFaktor;
    }

    public void setCcDarkhastFaktor(long ccDarkhastFaktor) {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }

    public String getTarikhFaktor() {
        return TarikhFaktor;
    }

    public void setTarikhFaktor(String tarikhFaktor) {
        TarikhFaktor = tarikhFaktor;
    }

    public int getShomarehDarkhast() {
        return ShomarehDarkhast;
    }

    public void setShomarehDarkhast(int shomarehDarkhast) {
        ShomarehDarkhast = shomarehDarkhast;
    }

    public int getShomarehFaktor() {
        return ShomarehFaktor;
    }

    public void setShomarehFaktor(int shomarehFaktor) {
        ShomarehFaktor = shomarehFaktor;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getTarikhErsal() {
        return TarikhErsal;
    }

    public void setTarikhErsal(String tarikhErsal) {
        TarikhErsal = tarikhErsal;
    }

    public double getMablaghKhalesFaktor() {
        return MablaghKhalesFaktor;
    }

    public void setMablaghKhalesFaktor(double mablaghKhalesFaktor) {
        MablaghKhalesFaktor = mablaghKhalesFaktor;
    }

    public int getCodeVazeiat() {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat) {
        CodeVazeiat = codeVazeiat;
    }

    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public int getExtraProp_IsSend() {
        return ExtraProp_IsSend;
    }

    public void setExtraProp_IsSend(int extraProp_IsSend) {
        ExtraProp_IsSend = extraProp_IsSend;
    }

    public String getUniqID_Tablet() {
        return UniqID_Tablet;
    }

    public void setUniqID_Tablet(String uniqID_Tablet) {
        UniqID_Tablet = uniqID_Tablet;
    }

    public String getFullNameMoshtary() {
        return fullNameMoshtary;
    }

    public void setFullNameMoshtary(String fullNameMoshtary) {
        this.fullNameMoshtary = fullNameMoshtary;
    }

    public String getCodeMoshtary() {
        return codeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        this.codeMoshtary = codeMoshtary;
    }

    public boolean getHaveEmzaImage() {
        return haveEmzaImage;
    }

    public void setHaveEmzaImage(boolean haveEmzaImage) {
        this.haveEmzaImage = haveEmzaImage;
    }

    public boolean getHaveFaktorImage() {
        return haveFaktorImage;
    }

    public void setHaveFaktorImage(boolean haveFaktorImage) {
        this.haveFaktorImage = haveFaktorImage;
    }

    public int getCcMoshtaryGharardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtaryGharardad(int ccMoshtaryGharardad) {
        this.ccMoshtaryGharardad = ccMoshtaryGharardad;
    }

    public int getMoshtaryGharardadccSazmanForosh() {
        return moshtaryGharardadccSazmanForosh;
    }

    public void setMoshtaryGharardadccSazmanForosh(int moshtaryGharardadccSazmanForosh) {
        this.moshtaryGharardadccSazmanForosh = moshtaryGharardadccSazmanForosh;
    }

    public boolean hasReceiptImage() {
        return hasReceiptImage;
    }

    public void setHasReceiptImage(boolean hasReceiptImage) {
        this.hasReceiptImage = hasReceiptImage;
    }

    public String getExtraProp_Description() {
        return extraProp_Description;
    }

    public void setExtraProp_Description(String extraProp_Description) {
        this.extraProp_Description = extraProp_Description;
    }


    @NonNull
    @Override
    public String toString() {
        return "CustomerDarkhastFaktorModel{" +
                "ccDarkhastFaktor=" + ccDarkhastFaktor +
                ", TarikhFaktor='" + TarikhFaktor + '\'' +
                ", ShomarehDarkhast=" + ShomarehDarkhast +
                ", ShomarehFaktor=" + ShomarehFaktor +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMoshtary=" + ccMoshtary +
                ", TarikhErsal='" + TarikhErsal + '\'' +
                ", MablaghKhalesFaktor=" + MablaghKhalesFaktor +
                ", CodeVazeiat=" + CodeVazeiat +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", ExtraProp_IsSend=" + ExtraProp_IsSend +
                ", UniqID_Tablet='" + UniqID_Tablet + '\'' +
                ", fullNameMoshtary='" + fullNameMoshtary + '\'' +
                ", codeMoshtary='" + codeMoshtary + '\'' +
                ", haveEmzaImage=" + haveEmzaImage +
                ", haveFaktorImage=" + haveFaktorImage +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", ccMohtaryGharardadSazmanForosh=" + moshtaryGharardadccSazmanForosh +
                ", ExtraProp_Description=" + extraProp_Description +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(ccDarkhastFaktor);
        parcel.writeString(TarikhFaktor);
        parcel.writeInt(ShomarehDarkhast);
        parcel.writeInt(ShomarehFaktor);
        parcel.writeInt(ccForoshandeh);
        parcel.writeInt(ccMoshtary);
        parcel.writeString(TarikhErsal);
        parcel.writeDouble(MablaghKhalesFaktor);
        parcel.writeInt(CodeVazeiat);
        parcel.writeInt(ExtraProp_IsOld);
        parcel.writeInt(ExtraProp_IsSend);
        parcel.writeString(UniqID_Tablet);
        parcel.writeString(fullNameMoshtary);
        parcel.writeString(codeMoshtary);
        parcel.writeByte((byte) (haveEmzaImage ? 1 : 0));
        parcel.writeByte((byte) (haveFaktorImage ? 1 : 0));
        parcel.writeInt(ccMoshtaryGharardad);
        parcel.writeInt(moshtaryGharardadccSazmanForosh);
        parcel.writeByte((byte) (hasReceiptImage ? 1 : 0));
        parcel.writeString(extraProp_Description);

    }


}
