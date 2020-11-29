package com.saphamrah.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class MoshtaryAddressModel implements Parcelable
{


    private static final String TABLE_NAME = "MoshtaryAddress";
    private static final String COLUMN_ccMoshtaryAddress = "ccMoshtaryAddress";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccAddress = "ccAddress";
    private static final String COLUMN_ccNoeAddress = "ccNoeAddress";
    private static final String COLUMN_ccMahaleh = "ccMahaleh";
    private static final String COLUMN_NameNoeAddress = "NameNoeAddress";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_ccShahr = "ccShahr";
    private static final String COLUMN_KhiabanAsli = "KhiabanAsli";
    private static final String COLUMN_KhiabanFarei1 = "KhiabanFarei1";
    private static final String COLUMN_KhiabanFarei2 = "KhiabanFarei2";
    private static final String COLUMN_KoocheAsli = "KoocheAsli";
    private static final String COLUMN_KoocheFarei1 = "KoocheFarei1";
    private static final String COLUMN_KoocheFarei2 = "KoocheFarei2";
    private static final String COLUMN_Pelak = "Pelak";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_CodePosty = "CodePosty";
    private static final String COLUMN_ExtraProp_InsertInPPC = "ExtraProp_InsertInPPC";
    private static final String COLUMN_ExtraProp_IsSendToSql = "ExtraProp_IsSendToSql";
    private static final String COLUMN_Longitude_x = "Longitude_x";
    private static final String COLUMN_Latitude_y = "Latitude_y";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtaryAddress() {
        return COLUMN_ccMoshtaryAddress;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccAddress() {
        return COLUMN_ccAddress;
    }
    public static String COLUMN_ccNoeAddress() {
        return COLUMN_ccNoeAddress;
    }
    public static String COLUMN_ccMahaleh() {
        return COLUMN_ccMahaleh;
    }
    public static String COLUMN_NameNoeAddress() {
        return COLUMN_NameNoeAddress;
    }
    public static String COLUMN_Address() {
        return COLUMN_Address;
    }
    public static String COLUMN_ccShahr() {
        return COLUMN_ccShahr;
    }
    public static String COLUMN_KhiabanAsli() {
        return COLUMN_KhiabanAsli;
    }
    public static String COLUMN_KhiabanFarei1() {
        return COLUMN_KhiabanFarei1;
    }
    public static String COLUMN_KhiabanFarei2() {
        return COLUMN_KhiabanFarei2;
    }
    public static String COLUMN_KoocheAsli() {
        return COLUMN_KoocheAsli;
    }
    public static String COLUMN_KoocheFarei1() {
        return COLUMN_KoocheFarei1;
    }
    public static String COLUMN_KoocheFarei2() {
        return COLUMN_KoocheFarei2;
    }
    public static String COLUMN_Pelak() {
        return COLUMN_Pelak;
    }
    public static String COLUMN_Telephone() {
        return COLUMN_Telephone;
    }
    public static String COLUMN_CodePosty() {
        return COLUMN_CodePosty;
    }
    public static String COLUMN_ExtraProp_InsertInPPC() {
        return COLUMN_ExtraProp_InsertInPPC;
    }
    public static String COLUMN_ExtraProp_IsSendToSql() {
        return COLUMN_ExtraProp_IsSendToSql;
    }
    public static String COLUMN_Longitude_x() {
        return COLUMN_Longitude_x;
    }
    public static String COLUMN_Latitude_y() {
        return COLUMN_Latitude_y;
    }


    public MoshtaryAddressModel()
    {}

    @SerializedName("ccMoshtaryAddress")
    @Expose
    private int ccMoshtaryAddress;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccAddress")
    @Expose
    private int ccAddress;
    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("CodePosty")
    @Expose
    private String CodePosty;
    @SerializedName("ccNoeAddress")
    @Expose
    private int ccNoeAddress;
    @SerializedName("NameNoeAddress")
    @Expose
    private String NameNoeAddress;
    @SerializedName("ccMahaleh")
    @Expose
    private int ccMahaleh;
    @SerializedName("ccShahr")
    @Expose
    private int ccShahr;
    @SerializedName("Telephone")
    @Expose
    private String Telephone;
    @SerializedName("KhiabanAsli")
    @Expose
    private String KhiabanAsli;
    @SerializedName("KhiabanFarei1")
    @Expose
    private String KhiabanFarei1;
    @SerializedName("KhiabanFarei2")
    @Expose
    private String KhiabanFarei2;
    @SerializedName("KoocheAsli")
    @Expose
    private String KoocheAsli;
    @SerializedName("KoocheFarei1")
    @Expose
    private String KoocheFarei1;
    @SerializedName("KoocheFarei2")
    @Expose
    private String KoocheFarei2;
    @SerializedName("Pelak")
    @Expose
    private String Pelak;
    @SerializedName("Longitude_x")
    @Expose
    private double Longitude_x;
    @SerializedName("Latitude_y")
    @Expose
    private double Latitude_y;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("ccMarkazForosh")
    @Expose
    private int ccMarkazForosh;
    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private int ccMarkazSazmanForosh;

    private int ExtraProp_InsertInPPC;
    private int ExtraProp_IsSendToSql;



    //this field used for adding address of new customer
    private String ostanName;
    private int ostanId;
    private String shahrestanName;
    private int shahrestanId;
    private String bakhshName;
    private int bakhshId;
    private String shahrName;
    private int shahrId;
    private String mantagheName;
    private int mantagheId;


    public String getOstanName() {
        return ostanName;
    }
    public void setOstanName(String ostanName) {
        this.ostanName = ostanName;
    }
    public int getOstanId() {
        return ostanId;
    }
    public void setOstanId(int ostanId) {
        this.ostanId = ostanId;
    }
    public String getShahrestanName() {
        return shahrestanName;
    }
    public void setShahrestanName(String shahrestanName) {
        this.shahrestanName = shahrestanName;
    }
    public int getShahrestanId() {
        return shahrestanId;
    }
    public void setShahrestanId(int shahrestanId) {
        this.shahrestanId = shahrestanId;
    }
    public String getBakhshName() {
        return bakhshName;
    }
    public void setBakhshName(String bakhshName) {
        this.bakhshName = bakhshName;
    }
    public int getBakhshId() {
        return bakhshId;
    }
    public void setBakhshId(int bakhshId) {
        this.bakhshId = bakhshId;
    }
    public String getShahrName() {
        return shahrName;
    }
    public void setShahrName(String shahrName) {
        this.shahrName = shahrName;
    }
    public int getShahrId() {
        return shahrId;
    }
    public void setShahrId(int shahrId) {
        this.shahrId = shahrId;
    }
    public String getMantagheName() {
        return mantagheName;
    }
    public void setMantagheName(String mantagheName) {
        this.mantagheName = mantagheName;
    }
    public int getMantagheId() {
        return mantagheId;
    }
    public void setMantagheId(int mantagheId) {
        this.mantagheId = mantagheId;
    }
    //this field used for adding address of new customer


    public void setCcMoshtaryAddress(int ccMoshtaryAddress){
        this.ccMoshtaryAddress = ccMoshtaryAddress;
    }

    public int getCcMoshtaryAddress(){
        return this.ccMoshtaryAddress;
    }

    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }

    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public void setCcAddress(int ccAddress){
        this.ccAddress = ccAddress;
    }

    public int getCcAddress(){
        return this.ccAddress;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public String getAddress(){
        return this.Address;
    }

    public void setCodePosty(String CodePosty){
        this.CodePosty = CodePosty;
    }

    public String getCodePosty(){
        return this.CodePosty;
    }

    public void setCcNoeAddress(int ccNoeAddress){
        this.ccNoeAddress = ccNoeAddress;
    }

    public int getCcNoeAddress(){
        return this.ccNoeAddress;
    }

    public void setNameNoeAddress(String NameNoeAddress){
        this.NameNoeAddress = NameNoeAddress;
    }

    public String getNameNoeAddress(){
        return this.NameNoeAddress;
    }

    public void setCcMahaleh(int ccMahaleh){
        this.ccMahaleh = ccMahaleh;
    }

    public int getCcMahaleh(){
        return this.ccMahaleh;
    }

    public void setCcShahr(int ccShahr){
        this.ccShahr = ccShahr;
    }

    public int getCcShahr(){
        return this.ccShahr;
    }

    public void setTelephone(String Telephone){
        this.Telephone = Telephone;
    }

    public String getTelephone(){
        return this.Telephone;
    }

    public void setKhiabanAsli(String KhiabanAsli){
        this.KhiabanAsli = KhiabanAsli;
    }

    public String getKhiabanAsli(){
        return this.KhiabanAsli;
    }

    public void setKhiabanFarei1(String KhiabanFarei1){
        this.KhiabanFarei1 = KhiabanFarei1;
    }

    public String getKhiabanFarei1(){
        return this.KhiabanFarei1;
    }

    public void setKhiabanFarei2(String KhiabanFarei2){
        this.KhiabanFarei2 = KhiabanFarei2;
    }

    public String getKhiabanFarei2(){
        return this.KhiabanFarei2;
    }

    public void setKoocheAsli(String KoocheAsli){
        this.KoocheAsli = KoocheAsli;
    }

    public String getKoocheAsli(){
        return this.KoocheAsli;
    }

    public void setKoocheFarei1(String KoocheFarei1){
        this.KoocheFarei1 = KoocheFarei1;
    }

    public String getKoocheFarei1(){
        return this.KoocheFarei1;
    }

    public void setKoocheFarei2(String KoocheFarei2){
        this.KoocheFarei2 = KoocheFarei2;
    }

    public String getKoocheFarei2(){
        return this.KoocheFarei2;
    }

    public void setPelak(String Pelak){
        this.Pelak = Pelak;
    }

    public String getPelak(){
        return this.Pelak;
    }

    public void setLongitude_x(double Longitude_x){
        this.Longitude_x = Longitude_x;
    }

    public double getLongitude_x(){
        return this.Longitude_x;
    }

    public void setLatitude_y(double Latitude_y){
        this.Latitude_y = Latitude_y;
    }

    public double getLatitude_y(){
        return this.Latitude_y;
    }

    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }

    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }

    public void setCcMarkazForosh(int ccMarkazForosh){
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public int getCcMarkazForosh(){
        return this.ccMarkazForosh;
    }

    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh){
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public int getCcMarkazSazmanForosh(){
        return this.ccMarkazSazmanForosh;
    }

    public int getExtraProp_InsertInPPC() {
        return ExtraProp_InsertInPPC;
    }

    public void setExtraProp_InsertInPPC(int extraProp_InsertInPPC) {
        ExtraProp_InsertInPPC = extraProp_InsertInPPC;
    }

    public int getExtraProp_IsSendToSql() {
        return ExtraProp_IsSendToSql;
    }

    public void setExtraProp_IsSendToSql(int extraProp_IsSendToSql) {
        ExtraProp_IsSendToSql = extraProp_IsSendToSql;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(ccMoshtaryAddress);
        dest.writeInt(ccMoshtary);
        dest.writeInt(ccAddress);
        dest.writeString(Address);
        dest.writeString(CodePosty);
        dest.writeInt(ccNoeAddress);
        dest.writeString(NameNoeAddress);
        dest.writeInt(ccMahaleh);
        dest.writeInt(ccShahr);
        dest.writeString(Telephone);
        dest.writeString(KhiabanAsli);
        dest.writeString(KhiabanFarei1);
        dest.writeString(KhiabanFarei2);
        dest.writeString(KoocheAsli);
        dest.writeString(KoocheFarei1);
        dest.writeString(KoocheFarei2);
        dest.writeString(Pelak);
        dest.writeDouble(Longitude_x);
        dest.writeDouble(Latitude_y);
        dest.writeInt(CodeVazeiat);
        dest.writeInt(ccMarkazForosh);
        dest.writeInt(ccMarkazSazmanForosh);
        dest.writeInt(ExtraProp_InsertInPPC);
        dest.writeInt(ExtraProp_IsSendToSql);
        dest.writeString(ostanName);
        dest.writeInt(ostanId);
        dest.writeString(shahrestanName);
        dest.writeInt(shahrestanId);
        dest.writeString(bakhshName);
        dest.writeInt(bakhshId);
        dest.writeString(shahrName);
        dest.writeInt(shahrId);
        dest.writeString(mantagheName);
        dest.writeInt(mantagheId);
    }


    public static final Parcelable.Creator<MoshtaryAddressModel> CREATOR
            = new Parcelable.Creator<MoshtaryAddressModel>() {
        public MoshtaryAddressModel createFromParcel(Parcel in) {
            return new MoshtaryAddressModel(in);
        }

        public MoshtaryAddressModel[] newArray(int size) {
            return new MoshtaryAddressModel[size];
        }
    };

    public MoshtaryAddressModel(Parcel in)
    {
        ccMoshtaryAddress = in.readInt();
        ccMoshtary = in.readInt();
        ccAddress = in.readInt();
        Address = in.readString();
        CodePosty = in.readString();
        ccNoeAddress = in.readInt();
        NameNoeAddress = in.readString();
        ccMahaleh = in.readInt();
        ccShahr = in.readInt();
        Telephone = in.readString();
        KhiabanAsli = in.readString();
        KhiabanFarei1 = in.readString();
        KhiabanFarei2 = in.readString();
        KoocheAsli = in.readString();
        KoocheFarei1 = in.readString();
        KoocheFarei2 = in.readString();
        Pelak = in.readString();
        Longitude_x = in.readDouble();
        Longitude_x = in.readDouble();
        CodeVazeiat = in.readInt();
        ccMarkazForosh = in.readInt();
        ccMarkazSazmanForosh = in.readInt();
        ExtraProp_InsertInPPC = in.readInt();
        ExtraProp_IsSendToSql = in.readInt();
        ostanName = in.readString();
        ostanId = in.readInt();
        shahrestanName = in.readString();
        shahrestanId = in.readInt();
        bakhshName = in.readString();
        bakhshId = in.readInt();
        shahrName = in.readString();
        shahrId = in.readInt();
        mantagheName = in.readString();
        mantagheId = in.readInt();
    }



    public String toJsonString()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COLUMN_ccAddress() , ccAddress);
        jsonObject.addProperty(COLUMN_Address() , Address);
        jsonObject.addProperty(COLUMN_ccShahr() , ccShahr);
        jsonObject.addProperty(COLUMN_CodePosty() , CodePosty);
        jsonObject.addProperty(COLUMN_KhiabanAsli() , KhiabanAsli);
        jsonObject.addProperty(COLUMN_KhiabanFarei1() , KhiabanFarei1);
        jsonObject.addProperty(COLUMN_KhiabanFarei2() , KhiabanFarei2);
        jsonObject.addProperty(COLUMN_KoocheAsli() , KoocheAsli);
        jsonObject.addProperty(COLUMN_KoocheFarei1() , KoocheFarei1);
        jsonObject.addProperty(COLUMN_KoocheFarei2() , KoocheFarei2);
        jsonObject.addProperty(COLUMN_Pelak() , Pelak);
        jsonObject.addProperty("Telephon" , Telephone);
        jsonObject.addProperty(COLUMN_Longitude_x() , Longitude_x);
        jsonObject.addProperty(COLUMN_Latitude_y() , Latitude_y);
        return jsonObject.toString();
    }


    public JSONObject toJsonObjectAddress()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccAddress" , ccMoshtaryAddress);
            jsonObject.put("ccNoeAddress" , ccNoeAddress);
            jsonObject.put("Address" , Address);
            jsonObject.put("ccShahr" , ccShahr);
            jsonObject.put("CodePosty" , CodePosty);
            jsonObject.put("KhiabanAsli" , KhiabanAsli);
            jsonObject.put("KhiabanFarei1" , KhiabanFarei1);
            jsonObject.put("KhiabanFarei2" , KhiabanFarei2);
            jsonObject.put("KoocheAsli" , KoocheAsli);
            jsonObject.put("KoocheFarei1" , KoocheFarei1);
            jsonObject.put("KoocheFarei2" , KoocheFarei2);
            jsonObject.put("Pelak" , Pelak);
            jsonObject.put("Telephon" , Telephone);
            jsonObject.put("Longitude_x" , Longitude_x);
            jsonObject.put("Latitude_y" , Latitude_y);
            jsonObject.put("ccMantagheh" , ccMahaleh);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject toJsonObjectMoshtaryAddress()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccAddress" , ccMoshtaryAddress);
            jsonObject.put("ccNoeAddress" , ccNoeAddress);
            jsonObject.put("ccMahaleh" , ccMahaleh);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString() {
        return "MoshtaryAddressModel{" +
                "ccMoshtaryAddress=" + ccMoshtaryAddress +
                ", ccMoshtary=" + ccMoshtary +
                ", ccAddress=" + ccAddress +
                ", Address='" + Address + '\'' +
                ", CodePosty='" + CodePosty + '\'' +
                ", ccNoeAddress=" + ccNoeAddress +
                ", NameNoeAddress='" + NameNoeAddress + '\'' +
                ", ccMahaleh='" + ccMahaleh + '\'' +
                ", ccShahr=" + ccShahr +
                ", Telephone='" + Telephone + '\'' +
                ", KhiabanAsli='" + KhiabanAsli + '\'' +
                ", KhiabanFarei1='" + KhiabanFarei1 + '\'' +
                ", KhiabanFarei2='" + KhiabanFarei2 + '\'' +
                ", KoocheAsli='" + KoocheAsli + '\'' +
                ", KoocheFarei1='" + KoocheFarei1 + '\'' +
                ", KoocheFarei2='" + KoocheFarei2 + '\'' +
                ", Pelak='" + Pelak + '\'' +
                ", Longitude_x=" + Longitude_x +
                ", Latitude_y=" + Latitude_y +
                ", CodeVazeiat=" + CodeVazeiat +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ExtraProp_InsertInPPC=" + ExtraProp_InsertInPPC +
                ", ExtraProp_IsSendToSql=" + ExtraProp_IsSendToSql +
                '}';
    }
}
