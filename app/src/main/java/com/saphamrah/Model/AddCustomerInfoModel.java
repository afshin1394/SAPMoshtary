package com.saphamrah.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AddCustomerInfoModel implements Parcelable
{

    private int ccMoshtary;
    private String firstName;
    private String lastName;
    private String tabloName;
    private String nationalCode;
    private String mobile;
    private String noeShakhsiatId;
    private String noeShakhsiatTitle;
    private String noeFaaliatId;
    private String noeFaaliatTitle;
    private String noeSenfId;
    private String noeSenfTitle;
    private String rotbeId;
    private String rotbeTitle;
    private String noeVosolId;
    private String noeVosolTitle;
    private String noeHamlId;
    private String noeHamlTitle;
    private String anbarId;
    private String anbarTitle;
    private String masahatMaghaze;
    private String saateVisit;
    private int isOld;
    private int olaviat;
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels;
    private ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels;
    /*private byte[] nationalCardImage;
    private byte[] javazeKasbImage;
    private byte[] dastehCheckImage;*/

    public AddCustomerInfoModel()
    {
        moshtaryAddressModels = new ArrayList<>();
        moshtaryShomarehHesabModels = new ArrayList<>();
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTabloName() {
        return tabloName;
    }

    public void setTabloName(String tabloName) {
        this.tabloName = tabloName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNoeShakhsiatId() {
        return noeShakhsiatId;
    }

    public void setNoeShakhsiatId(String noeShakhsiatId) {
        this.noeShakhsiatId = noeShakhsiatId;
    }

    public String getNoeShakhsiatTitle() {
        return noeShakhsiatTitle;
    }

    public void setNoeShakhsiatTitle(String noeShakhsiatTitle) {
        this.noeShakhsiatTitle = noeShakhsiatTitle;
    }

    public String getNoeFaaliatId() {
        return noeFaaliatId;
    }

    public void setNoeFaaliatId(String noeFaaliatId) {
        this.noeFaaliatId = noeFaaliatId;
    }

    public String getNoeFaaliatTitle() {
        return noeFaaliatTitle;
    }

    public void setNoeFaaliatTitle(String noeFaaliatTitle) {
        this.noeFaaliatTitle = noeFaaliatTitle;
    }

    public String getNoeSenfId() {
        return noeSenfId;
    }

    public void setNoeSenfId(String noeSenfId) {
        this.noeSenfId = noeSenfId;
    }

    public String getNoeSenfTitle() {
        return noeSenfTitle;
    }

    public void setNoeSenfTitle(String noeSenfTitle) {
        this.noeSenfTitle = noeSenfTitle;
    }

    public String getRotbeId() {
        return rotbeId;
    }

    public void setRotbeId(String rotbeId) {
        this.rotbeId = rotbeId;
    }

    public String getRotbeTitle() {
        return rotbeTitle;
    }

    public void setRotbeTitle(String rotbeTitle) {
        this.rotbeTitle = rotbeTitle;
    }

    public String getNoeVosolId() {
        return noeVosolId;
    }

    public void setNoeVosolId(String noeVosolId) {
        this.noeVosolId = noeVosolId;
    }

    public String getNoeVosolTitle() {
        return noeVosolTitle;
    }

    public void setNoeVosolTitle(String noeVosolTitle) {
        this.noeVosolTitle = noeVosolTitle;
    }

    public String getNoeHamlId() {
        return noeHamlId;
    }

    public void setNoeHamlId(String noeHamlId) {
        this.noeHamlId = noeHamlId;
    }

    public String getNoeHamlTitle() {
        return noeHamlTitle;
    }

    public void setNoeHamlTitle(String noeHamlTitle) {
        this.noeHamlTitle = noeHamlTitle;
    }

    public String getAnbarId() {
        return anbarId;
    }

    public void setAnbarId(String anbarId) {
        this.anbarId = anbarId;
    }

    public String getAnbarTitle() {
        return anbarTitle;
    }

    public void setAnbarTitle(String anbarTitle) {
        this.anbarTitle = anbarTitle;
    }

    public String getMasahatMaghaze() {
        return masahatMaghaze;
    }

    public void setMasahatMaghaze(String masahatMaghaze) {
        this.masahatMaghaze = masahatMaghaze;
    }

    public String getSaateVisit() {
        return saateVisit;
    }

    public void setSaateVisit(String saateVisit) {
        this.saateVisit = saateVisit;
    }

    public int getIsOld() {
        return isOld;
    }

    public void setIsOld(int isOld) {
        this.isOld = isOld;
    }

    public int getOlaviat() {
        return olaviat;
    }

    public void setOlaviat(int olaviat) {
        this.olaviat = olaviat;
    }

    public ArrayList<MoshtaryAddressModel> getMoshtaryAddressModels()
    {
        return moshtaryAddressModels;
    }

    public void setMoshtaryAddressModels(ArrayList<MoshtaryAddressModel> moshtaryAddressModels)
    {
        this.moshtaryAddressModels = new ArrayList<>();
        this.moshtaryAddressModels = moshtaryAddressModels;
    }

    public ArrayList<MoshtaryShomarehHesabModel> getMoshtaryShomarehHesabModels()
    {
        return moshtaryShomarehHesabModels;
    }

    public void setMoshtaryShomarehHesabModels(ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels)
    {
        this.moshtaryShomarehHesabModels = new ArrayList<>();
        this.moshtaryShomarehHesabModels = moshtaryShomarehHesabModels;
    }

    /*public byte[] getNationalCardImage() {
        return nationalCardImage;
    }

    public void setNationalCardImage(byte[] nationalCardImage)
    {
        this.nationalCardImage = nationalCardImage;
    }

    public byte[] getJavazeKasbImage() {
        return javazeKasbImage;
    }

    public void setJavazeKasbImage(byte[] javazeKasbImage) {
        this.javazeKasbImage = javazeKasbImage;
    }

    public byte[] getDastehCheckImage() {
        return dastehCheckImage;
    }

    public void setDastehCheckImage(byte[] dastehCheckImage) {
        this.dastehCheckImage = dastehCheckImage;
    }*/

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(ccMoshtary);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(tabloName);
        dest.writeString(nationalCode);
        dest.writeString(mobile);
        dest.writeString(noeShakhsiatId);
        dest.writeString(noeShakhsiatTitle);
        dest.writeString(noeFaaliatId);
        dest.writeString(noeFaaliatTitle);
        dest.writeString(noeSenfId);
        dest.writeString(noeSenfTitle);
        dest.writeString(rotbeId);
        dest.writeString(rotbeTitle);
        dest.writeString(noeVosolId);
        dest.writeString(noeVosolTitle);
        dest.writeString(noeHamlId);
        dest.writeString(noeHamlTitle);
        dest.writeString(anbarId);
        dest.writeString(anbarTitle);
        dest.writeString(masahatMaghaze);
        dest.writeString(saateVisit);
        dest.writeInt(isOld);
        dest.writeList(moshtaryAddressModels);
        dest.writeList(moshtaryShomarehHesabModels);
        /*dest.writeInt(nationalCardImage.length);
        dest.writeByteArray(nationalCardImage);
        dest.writeInt(javazeKasbImage.length);
        dest.writeByteArray(javazeKasbImage);
        dest.writeInt(dastehCheckImage.length);
        dest.writeByteArray(dastehCheckImage);*/
    }


    public static final Parcelable.Creator<AddCustomerInfoModel> CREATOR
            = new Parcelable.Creator<AddCustomerInfoModel>() {
        public AddCustomerInfoModel createFromParcel(Parcel in) {
            return new AddCustomerInfoModel(in);
        }

        public AddCustomerInfoModel[] newArray(int size) {
            return new AddCustomerInfoModel[size];
        }
    };

    private AddCustomerInfoModel(Parcel in)
    {
        ccMoshtary = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        tabloName = in.readString();
        nationalCode = in.readString();
        mobile = in.readString();
        noeShakhsiatId = in.readString();
        noeShakhsiatTitle = in.readString();
        noeFaaliatId = in.readString();
        noeFaaliatTitle = in.readString();
        noeSenfId = in.readString();
        noeSenfTitle = in.readString();
        rotbeId = in.readString();
        rotbeTitle = in.readString();
        noeVosolId = in.readString();
        noeVosolTitle = in.readString();
        noeHamlId = in.readString();
        noeHamlTitle = in.readString();
        anbarId = in.readString();
        anbarTitle = in.readString();
        masahatMaghaze = in.readString();
        saateVisit = in.readString();
        isOld = in.readInt();
        moshtaryAddressModels = new ArrayList<MoshtaryAddressModel>();
        moshtaryShomarehHesabModels = new ArrayList<MoshtaryShomarehHesabModel>();
        moshtaryAddressModels = in.readArrayList(MoshtaryAddressModel.class.getClassLoader());
        moshtaryShomarehHesabModels = in.readArrayList(MoshtaryShomarehHesabModel.class.getClassLoader());
        /*nationalCardImage = new byte[in.readInt()];
        in.readByteArray(nationalCardImage);
        javazeKasbImage = new byte[in.readInt()];
        in.readByteArray(javazeKasbImage);
        dastehCheckImage = new byte[in.readInt()];
        in.readByteArray(dastehCheckImage);*/
    }


}
