package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMoshtarianModel
{

    public static final int SAVED_PORSESHNAME = 1;
    public static final int SAVED_ADAM_FAAL = 2;

    private static final String TABLE_NAME = "ListMoshtarian";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeMoshtaryOld = "CodeMoshtaryOld";
    private static final String COLUMN_FNameMoshtary = "FNameMoshtary";
    private static final String COLUMN_LNameMoshtary = "LNameMoshtary";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_CodeMely = "CodeMely";
    private static final String COLUMN_MoshtaryCodeVazeiat = "MoshtaryCodeVazeiat";
    private static final String COLUMN_txtMoshtaryCodeVazeiat = "txtMoshtaryCodeVazeiat";
    private static final String COLUMN_NameTablo = "NameTablo";
    private static final String COLUMN_NameNoeMoshtary = "NameNoeMoshtary";
    private static final String COLUMN_Latitude_y = "Latitude_y";
    private static final String COLUMN_Longitude_x = "Longitude_x";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_Mobile = "Mobile";
    private static final String COLUMN_MasahatMaghazeh = "MasahatMaghazeh";
    private static final String COLUMN_hasAnbar = "hasAnbar";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_IsMoshtaryAmargar = "IsMoshtaryAmargar";
    private static final String COLUMN_ccMahaleh= "ccMahaleh";
    private static final String COLUMN_CodePosty = "CodePosty";
    private static final String COLUMN_Pelak = "Pelak";
    private static final String COLUMN_CodeVazeiatAmargar = "CodeVazeiatAmargar";
    private static final String COLUMN_KhiabanAsli = "KhiabanAsli";
    private static final String COLUMN_KhiabanFarei1 = "KhiabanFarei1";
    private static final String COLUMN_KhiabanFarei2 = "KhiabanFarei2";
    private static final String COLUMN_KoocheAsli = "KoocheAsli";
    private static final String COLUMN_KoocheFarei1 = "KoocheFarei1";
    private static final String COLUMN_KoocheFarei2 = "KoocheFarei2";
    private static final String COLUMN_TarikhAkharinFaktor = "TarikhAkharinFaktor";
    private static final String COLUMN_MablaghAkharinFaktor = "MablaghAkharinFaktor";
    private static final String COLUMN_ExtraProp_isSend = "ExtraProp_isSend";
    private static final String COLUMN_ExtraProp_Status = "ExtraProp_Status";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary() {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeMoshtaryOld() {
        return COLUMN_CodeMoshtaryOld;
    }
    public static String COLUMN_FNameMoshtary() {
        return COLUMN_FNameMoshtary;
    }
    public static String COLUMN_LNameMoshtary() {
        return COLUMN_LNameMoshtary;
    }
    public static String COLUMN_NameMoshtary() {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_CodeMely() {
        return COLUMN_CodeMely;
    }
    public static String COLUMN_MoshtaryCodeVazeiat() {
        return COLUMN_MoshtaryCodeVazeiat;
    }
    public static String COLUMN_txtMoshtaryCodeVazeiat() {
        return COLUMN_txtMoshtaryCodeVazeiat;
    }
    public static String COLUMN_NameTablo() {
        return COLUMN_NameTablo;
    }
    public static String COLUMN_NameNoeMoshtary() {
        return COLUMN_NameNoeMoshtary;
    }
    public static String COLUMN_Latitude_y() {
        return COLUMN_Latitude_y;
    }
    public static String COLUMN_Longitude_x() {
        return COLUMN_Longitude_x;
    }
    public static String COLUMN_Address() {
        return COLUMN_Address;
    }
    public static String COLUMN_Telephone() {
        return COLUMN_Telephone;
    }
    public static String COLUMN_Mobile() {
        return COLUMN_Mobile;
    }
    public static String COLUMN_MasahatMaghazeh() {
        return COLUMN_MasahatMaghazeh;
    }
    public static String COLUMN_hasAnbar() {
        return COLUMN_hasAnbar;
    }
    public static String COLUMN_ccPorseshnameh() {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_IsMoshtaryAmargar() {
        return COLUMN_IsMoshtaryAmargar;
    }
    public static String COLUMN_ccMahaleh()
    {
        return COLUMN_ccMahaleh;
    }
    public static String COLUMN_CodePosty()
    {
        return COLUMN_CodePosty;
    }
    public static String COLUMN_Pelak()
    {
        return COLUMN_Pelak;
    }
    public static String COLUMN_CodeVazeiatAmargar()
    {
        return COLUMN_CodeVazeiatAmargar;
    }
    public static String COLUMN_KhiabanAsli()
    {
        return COLUMN_KhiabanAsli;
    }
    public static String COLUMN_KhiabanFarei1()
    {
        return COLUMN_KhiabanFarei1;
    }
    public static String COLUMN_KhiabanFarei2()
    {
        return COLUMN_KhiabanFarei2;
    }
    public static String COLUMN_KoocheAsli()
    {
        return COLUMN_KoocheAsli;
    }
    public static String COLUMN_KoocheFarei1()
    {
        return COLUMN_KoocheFarei1;
    }
    public static String COLUMN_KoocheFarei2()
    {
        return COLUMN_KoocheFarei2;
    }
    public static String COLUMN_TarikhAkharinFaktor()
    {
        return COLUMN_TarikhAkharinFaktor;
    }
    public static String COLUMN_MablaghAkharinFaktor()
    {
        return COLUMN_MablaghAkharinFaktor;
    }
    public static String COLUMN_ExtraProp_isSend()
    {
        return COLUMN_ExtraProp_isSend;
    }
    public static String COLUMN_ExtraProp_Status()
    {
        return COLUMN_ExtraProp_Status;
    }


    @SerializedName("Radif")
    @Expose
    private Integer radif;
    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("ccPorseshnameh")
    @Expose
    private int ccPorseshnameh;
    @SerializedName("CodeMoshtaryOld")
    @Expose
    private String codeMoshtaryOld;
    @SerializedName("NameMoshtary")
    @Expose
    private String nameMoshtary;
    @SerializedName("FNameMoshtary")
    @Expose
    private String fNameMoshtary;
    @SerializedName("LNameMoshtary")
    @Expose
    private String lNameMoshtary;
    @SerializedName("MoshtaryCodeVazeiat")
    @Expose
    private int moshtaryCodeVazeiat;
    @SerializedName("txtMoshtaryCodeVazeiat")
    @Expose
    private String txtMoshtaryCodeVazeiat;
    @SerializedName("NameTablo")
    @Expose
    private String nameTablo;
    @SerializedName("NameNoeMoshtary")
    @Expose
    private String nameNoeMoshtary;
    @SerializedName("CodeMely")
    @Expose
    private String CodeMely;
    @SerializedName("Latitude_y")
    @Expose
    private Double latitudeY;
    @SerializedName("Longitude_x")
    @Expose
    private Double longitudeX;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Telephone")
    @Expose
    private String telephone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("IsMoshtaryAmargar")
    @Expose
    private int isMoshtaryAmargar;
    @SerializedName("Pelak")
    @Expose
    private String pelak;
    @SerializedName("CodePosty")
    @Expose
    private String codePosty;
    @SerializedName("ccMahaleh")
    @Expose
    private Integer ccMahaleh;
    @SerializedName("CodeVazeiatAmargar")
    @Expose
    private String codeVazeiatAmargar;
    @SerializedName("KhiabanAsli")
    @Expose
    private String khiabanAsli;
    @SerializedName("KhiabanFarei1")
    @Expose
    private String khiabanFarei1;
    @SerializedName("KhiabanFarei2")
    @Expose
    private String khiabanFarei2;
    @SerializedName("KoocheAsli")
    @Expose
    private String koocheAsli;
    @SerializedName("KoocheFarei1")
    @Expose
    private String koocheFarei1;
    @SerializedName("KoocheFarei2")
    @Expose
    private String koocheFarei2;
    @SerializedName("TarikhAkharinFaktor")
    @Expose
    private String tarikhAkharinFaktor;
    @SerializedName("MablaghAkharinFaktor")
    @Expose
    private long mablaghAkharinFaktor;
    @SerializedName("MasahatMaghazeh")
    @Expose
    private int masahatMaghazeh;
    @SerializedName("HasAnbar")
    @Expose
    private int hasAnbar;
    @SerializedName("Id")
    @Expose
    private Integer id;


    private int ExtraProp_isSend;
    private int ExtraProp_Status;


    public Integer getRadif() {
        return radif;
    }

    public void setRadif(Integer radif) {
        this.radif = radif;
    }

    public Integer getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(Integer ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public Integer getCcPorseshnameh() {
        return ccPorseshnameh;
    }

    public void setCcPorseshnameh(Integer ccPorseshnameh) {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public String getCodeMoshtaryOld() {
        return codeMoshtaryOld;
    }

    public void setCodeMoshtaryOld(String codeMoshtaryOld) {
        this.codeMoshtaryOld = codeMoshtaryOld;
    }

    public String getNameMoshtary() {
        return nameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        this.nameMoshtary = nameMoshtary;
    }

    public String getCodeMely()
    {
        return CodeMely;
    }

    public void setCodeMely(String codeMely)
    {
        CodeMely = codeMely;
    }

    public String getFNameMoshtary() {
        return fNameMoshtary;
    }

    public void setFNameMoshtary(String fNameMoshtary) {
        this.fNameMoshtary = fNameMoshtary;
    }

    public String getLNameMoshtary() {
        return lNameMoshtary;
    }

    public void setLNameMoshtary(String lNameMoshtary) {
        this.lNameMoshtary = lNameMoshtary;
    }

    public Integer getMoshtaryCodeVazeiat() {
        return moshtaryCodeVazeiat;
    }

    public void setMoshtaryCodeVazeiat(Integer moshtaryCodeVazeiat) {
        this.moshtaryCodeVazeiat = moshtaryCodeVazeiat;
    }

    public String getTxtMoshtaryCodeVazeiat() {
        return txtMoshtaryCodeVazeiat;
    }

    public void setTxtMoshtaryCodeVazeiat(String txtMoshtaryCodeVazeiat) {
        this.txtMoshtaryCodeVazeiat = txtMoshtaryCodeVazeiat;
    }

    public String getNameTablo() {
        return nameTablo;
    }

    public void setNameTablo(String nameTablo) {
        this.nameTablo = nameTablo;
    }

    public String getNameNoeMoshtary() {
        return nameNoeMoshtary;
    }

    public void setNameNoeMoshtary(String nameNoeMoshtary) {
        this.nameNoeMoshtary = nameNoeMoshtary;
    }

    public Double getLatitudeY() {
        return latitudeY;
    }

    public void setLatitudeY(Double latitudeY) {
        this.latitudeY = latitudeY;
    }

    public Double getLongitudeX() {
        return longitudeX;
    }

    public void setLongitudeX(Double longitudeX) {
        this.longitudeX = longitudeX;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public Integer getIsMoshtaryAmargar() {
        return isMoshtaryAmargar;
    }

    public void setIsMoshtaryAmargar(Integer isMoshtaryAmargar) {
        this.isMoshtaryAmargar = isMoshtaryAmargar;
    }

    public String getPelak() {
        return pelak;
    }

    public void setPelak(String pelak) {
        this.pelak = pelak;
    }

    public String getCodePosty() {
        return codePosty;
    }

    public void setCodePosty(String codePosty) {
        this.codePosty = codePosty;
    }

    public Integer getCcMahaleh() {
        return ccMahaleh;
    }

    public void setCcMahaleh(Integer ccMahaleh) {
        this.ccMahaleh = ccMahaleh;
    }

    public String getCodeVazeiatAmargar() {
        return codeVazeiatAmargar;
    }

    public void setCodeVazeiatAmargar(String codeVazeiatAmargar) {
        this.codeVazeiatAmargar = codeVazeiatAmargar;
    }

    public String getKhiabanAsli() {
        return khiabanAsli;
    }

    public void setKhiabanAsli(String khiabanAsli) {
        this.khiabanAsli = khiabanAsli;
    }

    public String getKhiabanFarei1() {
        return khiabanFarei1;
    }

    public void setKhiabanFarei1(String khiabanFarei1) {
        this.khiabanFarei1 = khiabanFarei1;
    }

    public String getKhiabanFarei2() {
        return khiabanFarei2;
    }

    public void setKhiabanFarei2(String khiabanFarei2) {
        this.khiabanFarei2 = khiabanFarei2;
    }

    public String getKoocheAsli() {
        return koocheAsli;
    }

    public void setKoocheAsli(String koocheAsli) {
        this.koocheAsli = koocheAsli;
    }

    public String getKoocheFarei1() {
        return koocheFarei1;
    }

    public void setKoocheFarei1(String koocheFarei1) {
        this.koocheFarei1 = koocheFarei1;
    }

    public String getKoocheFarei2() {
        return koocheFarei2;
    }

    public void setKoocheFarei2(String koocheFarei2) {
        this.koocheFarei2 = koocheFarei2;
    }

    public String getTarikhAkharinFaktor() {
        return tarikhAkharinFaktor;
    }

    public void setTarikhAkharinFaktor(String tarikhAkharinFaktor) {
        this.tarikhAkharinFaktor = tarikhAkharinFaktor;
    }

    public long getMablaghAkharinFaktor() {
        return mablaghAkharinFaktor;
    }

    public void setMablaghAkharinFaktor(long mablaghAkharinFaktor) {
        this.mablaghAkharinFaktor = mablaghAkharinFaktor;
    }

    public int getMasahatMaghazeh()
    {
        return masahatMaghazeh;
    }

    public void setMasahatMaghazeh(int masahatMaghazeh)
    {
        this.masahatMaghazeh = masahatMaghazeh;
    }

    public int getHasAnbar()
    {
        return hasAnbar;
    }

    public void setHasAnbar(int hasAnbar)
    {
        this.hasAnbar = hasAnbar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getExtraProp_isSend()
    {
        return ExtraProp_isSend;
    }

    public void setExtraProp_isSend(int extraProp_isSend)
    {
        ExtraProp_isSend = extraProp_isSend;
    }

    public int getExtraProp_Status()
    {
        return ExtraProp_Status;
    }

    public void setExtraProp_Status(int extraProp_Status)
    {
        ExtraProp_Status = extraProp_Status;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ListMoshtarianModel{" +
                "radif=" + radif +
                ", ccMoshtary=" + ccMoshtary +
                ", ccPorseshnameh=" + ccPorseshnameh +
                ", codeMoshtaryOld='" + codeMoshtaryOld + '\'' +
                ", nameMoshtary='" + nameMoshtary + '\'' +
                ", CodeMely='" + CodeMely + '\'' +
                ", fNameMoshtary='" + fNameMoshtary + '\'' +
                ", lNameMoshtary='" + lNameMoshtary + '\'' +
                ", moshtaryCodeVazeiat=" + moshtaryCodeVazeiat +
                ", txtMoshtaryCodeVazeiat='" + txtMoshtaryCodeVazeiat + '\'' +
                ", nameTablo='" + nameTablo + '\'' +
                ", nameNoeMoshtary='" + nameNoeMoshtary + '\'' +
                ", latitudeY=" + latitudeY +
                ", longitudeX=" + longitudeX +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isMoshtaryAmargar=" + isMoshtaryAmargar +
                ", pelak='" + pelak + '\'' +
                ", codePosty='" + codePosty + '\'' +
                ", ccMahaleh=" + ccMahaleh +
                ", codeVazeiatAmargar='" + codeVazeiatAmargar + '\'' +
                ", khiabanAsli='" + khiabanAsli + '\'' +
                ", khiabanFarei1='" + khiabanFarei1 + '\'' +
                ", khiabanFarei2='" + khiabanFarei2 + '\'' +
                ", koocheAsli='" + koocheAsli + '\'' +
                ", koocheFarei1='" + koocheFarei1 + '\'' +
                ", koocheFarei2='" + koocheFarei2 + '\'' +
                ", tarikhAkharinFaktor='" + tarikhAkharinFaktor + '\'' +
                ", mablaghAkharinFaktor=" + mablaghAkharinFaktor +
                ", masahatMaghazeh=" + masahatMaghazeh +
                ", hasAnbar=" + hasAnbar +
                ", id=" + id +
                ", ExtraProp_isSend=" + ExtraProp_isSend +
                ", ExtraProp_Status=" + ExtraProp_Status +
                '}';
    }
}
