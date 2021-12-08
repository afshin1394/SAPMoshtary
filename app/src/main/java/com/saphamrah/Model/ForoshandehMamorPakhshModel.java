package com.saphamrah.Model;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForoshandehMamorPakhshModel
{

    private static final String TABLE_NAME = "ForoshandehMamorPakhsh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMamorPakhsh = "ccMamorPakhsh";
    private static final String COLUMN_NoeMasouliat = "NoeMasouliat";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_ccAmargar = "ccAmargar";
    private static final String COLUMN_ccAfradModir = "ccAfradModir";
    private static final String COLUMN_CodeForoshandeh = "CodeForoshandeh";
    private static final String COLUMN_FullName = "FullName";
    private static final String COLUMN_DeviceSerialNumber = "DeviceSerialNumber";
    private static final String COLUMN_CanGetProgram = "CanGetProgram";
    private static final String COLUMN_CanSetFaktorKharejAzMahal = "CanSetFaktorKharejAzMahal";
    private static final String COLUMN_CanGetDarkhastTelephoni = "CanGetDarkhastTelephoni";
    private static final String COLUMN_CanGetPhotoChidman= "CanGetPhotoChidman";
    private static final String COLUMN_CanChangeMoshtaryPosition = "CanChangeMoshtaryPosition";
    private static final String COLUMN_NoeForoshandehMamorPakhsh = "NoeForoshandehMamorPakhsh";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_ccMarkazAnbar = "ccMarkazAnbar";
    private static final String COLUMN_ccMarkazAnbarVosol = "ccMarkazAnbarVosol";
    private static final String COLUMN_NameMarkazForosh = "NameMarkazForosh";
    private static final String COLUMN_NameMarkazAnbar = "NameMarkazAnbar";
    private static final String COLUMN_MaxTedadCheckBargashty = "MaxTedadCheckBargashty";
    private static final String COLUMN_MaxMablaghCheckBargashty = "MaxMablaghCheckBargashty";
    private static final String COLUMN_MaxModatCheckBargashty = "MaxModatCheckBargashty";
    private static final String COLUMN_Max_ResidCheck = "Max_ResidCheck";
    private static final String COLUMN_Max_ResidNaghd = "Max_ResidNaghd";
    private static final String COLUMN_ExtraProp_IsSelect = "ExtraProp_IsSelect";
    private static final String COLUMN_NameMarkazSazmanForoshSakhtarForosh = "NameMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_NameSazmanForosh = "NameSazmanForosh";
    private static final String COLUMN_TelephoneShobeh = "TelephoneShobeh";
    private static final String COLUMN_TelephoneForoshandehMamorPakhsh = "TelephoneForoshandehMamorPakhsh";
    private static final String COLUMN_PosNumber = "PosNumber";
    private static final String COLUMN_ccPosShomarehHesab = "ccPosShomarehHesab";
    private static final String COLUMN_ccMarkaz = "ccMarkaz";
    private static final String COLUMN_IsMojazForSabtDarkhast = "IsMojazForSabtDarkhast";
    private static final String COLUMN_ccMarkazSazmanForoshSakhtarForosh = "ccMarkazSazmanForoshSakhtarForosh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_CanGetMarjoee = "CanGetMarjoee";
    private static final String COLUMN_NoeSabtMarjoee = "NoeSabtMarjoee";
    private static final String COLUMN_ccDarkhastFaktorNoeForosh = "ccDarkhastFaktorNoeForosh";																							   
    private static final String COLUMN_ccMashin= "ccMashin";
    private static final String COLUMN_flag_SabtNaghd = "flag_SabtNaghd";
    private static final String COLUMN_ccForoshandehs = "ccForoshandehs";
    private static final String COLUMN_ccAnbar = "ccAnbar";
    private static final String COLUMN_ccAnbarak = "ccAnbarak";
	private static final String COLUMN_CheckOlaviatMoshtary = "CheckOlaviatMoshtary";																					 
	private static final String COLUMN_FromDateKharejAzMahal = "FromDateKharejAzMahal";
	private static final String COLUMN_EndDateKharejAzMahal = "EndDateKharejAzMahal";
	private static final String COLUMN_ccAnbarMarjoee = "ccAnbarMarjoee";
	private static final String COLUMN_CodeAnbarMarjoee= "CodeNoeAnbarMarjoee";
    private static final String COLUMN_Version= "Version";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccForoshandeh() {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMamorPakhsh() {
        return COLUMN_ccMamorPakhsh;
    }
    public static String COLUMN_NoeMasouliat() {
        return COLUMN_NoeMasouliat;
    }
    public static String COLUMN_ccAfrad() {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_ccAmargar() {
        return COLUMN_ccAmargar;
    }
    public static String COLUMN_ccAfradModir() {
        return COLUMN_ccAfradModir;
    }
    public static String COLUMN_CodeForoshandeh() {
        return COLUMN_CodeForoshandeh;
    }
    public static String COLUMN_FullName() {
        return COLUMN_FullName;
    }
    public static String COLUMN_DeviceSerialNumber() {
        return COLUMN_DeviceSerialNumber;
    }
    public static String COLUMN_CanGetProgram() {
        return COLUMN_CanGetProgram;
    }
    public static String COLUMN_CanSetFaktorKharejAzMahal() {
        return COLUMN_CanSetFaktorKharejAzMahal;
    }
    public static String COLUMN_CanGetDarkhastTelephoni() {
        return COLUMN_CanGetDarkhastTelephoni;
    }
    public static String COLUMN_CanGetPhotoChidman() {
        return COLUMN_CanGetPhotoChidman;
    }
    public static String COLUMN_CanChangeMoshtaryPosition() {
        return COLUMN_CanChangeMoshtaryPosition;
    }
    public static String COLUMN_NoeForoshandehMamorPakhsh() {
        return COLUMN_NoeForoshandehMamorPakhsh;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_ccMarkazAnbar() {
        return COLUMN_ccMarkazAnbar;
    }
    public static String COLUMN_ccMarkazAnbarVosol() {
        return COLUMN_ccMarkazAnbarVosol;
    }
    public static String COLUMN_NameMarkazForosh() {
        return COLUMN_NameMarkazForosh;
    }
    public static String COLUMN_NameMarkazAnbar() {
        return COLUMN_NameMarkazAnbar;
    }
    public static String COLUMN_MaxTedadCheckBargashty() {
        return COLUMN_MaxTedadCheckBargashty;
    }
    public static String COLUMN_MaxMablaghCheckBargashty() {
        return COLUMN_MaxMablaghCheckBargashty;
    }
    public static String COLUMN_MaxModatCheckBargashty() {
        return COLUMN_MaxModatCheckBargashty;
    }
    public static String COLUMN_Max_ResidCheck() {
        return COLUMN_Max_ResidCheck;
    }
    public static String COLUMN_Max_ResidNaghd() {
        return COLUMN_Max_ResidNaghd;
    }
    public static String COLUMN_ExtraProp_IsSelect() {
        return COLUMN_ExtraProp_IsSelect;
    }
    public static String COLUMN_NameMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_NameMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_NameSazmanForosh() {
        return COLUMN_NameSazmanForosh;
    }
    public static String COLUMN_TelephoneShobeh() {
        return COLUMN_TelephoneShobeh;
    }
    public static String COLUMN_TelephoneForoshandehMamorPakhsh() {
        return COLUMN_TelephoneForoshandehMamorPakhsh;
    }
    public static String COLUMN_PosNumber() {
        return COLUMN_PosNumber;
    }
    public static String COLUMN_ccPosShomarehHesab() {
        return COLUMN_ccPosShomarehHesab;
    }
    public static String COLUMN_ccMarkaz() {
        return COLUMN_ccMarkaz;
    }
    public static String COLUMN_IsMojazForSabtDarkhast() {
        return COLUMN_IsMojazForSabtDarkhast;
    }
    public static String COLUMN_ccMarkazSazmanForoshSakhtarForosh() {
        return COLUMN_ccMarkazSazmanForoshSakhtarForosh;
    }
    public static String COLUMN_ccMarkazSazmanForosh() {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_ccSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }
    public static String COLUMN_CanGetMarjoee() {
        return COLUMN_CanGetMarjoee;
    }
    public static String COLUMN_NoeSabtMarjoee() {
        return COLUMN_NoeSabtMarjoee;
    }
    public static String COLUMN_ccDarkhastFaktorNoeForosh() {
        return COLUMN_ccDarkhastFaktorNoeForosh;
    }
    public static String COLUMN_ccMashin() {
        return COLUMN_ccMashin;
    }
    public static String COLUMN_flag_SabtNaghd() {
        return COLUMN_flag_SabtNaghd;
    }
    public static String COLUMN_ccForoshandehs() {
        return COLUMN_ccForoshandehs;
    }
    public static String COLUMN_ccAnbar() {
        return COLUMN_ccAnbar;
    }
    public static String COLUMN_ccAnbarak() {
        return COLUMN_ccAnbarak;
    }
    public static String COLUMN_CheckOlaviatMoshtary() {
        return COLUMN_CheckOlaviatMoshtary;
    }
    public static String COLUMN_FromDateKharejAzMahal() {
        return COLUMN_FromDateKharejAzMahal;
    }
    public static String COLUMN_EndDateKharejAzMahal() {
        return COLUMN_EndDateKharejAzMahal;
    }
    public static String COLUMN_ccAnbarMarjoee() {
        return COLUMN_ccAnbarMarjoee;
    }
    public static String COLUMN_CodeNoeAnbarMarjoee() {
        return COLUMN_CodeAnbarMarjoee;
    }
    //TODO
    public static String COLUMN_Version() {
        return COLUMN_Version;
    }




    @SerializedName("ccAfrad")
    @Expose
    private Integer ccAfrad;
    @SerializedName("ccAmargar")
    @Expose
    private int ccAmargar;
    @SerializedName("NoeMasouliat")
    @Expose
    private Integer noeMasouliat;
    @SerializedName("ccForoshandeh")
    @Expose
    private Integer ccForoshandeh;
    @SerializedName("ccMamorPakhsh")
    @Expose
    private Integer ccMamorPakhsh;
    @SerializedName("CodeForoshandeh")
    @Expose
    private String codeForoshandeh;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("DeviceSerialNumber")
    @Expose
    private String deviceSerialNumber;
    @SerializedName("CanSetFaktorKharejAzMahal")
    @Expose
    private Integer canSetFaktorKharejAzMahal;
    @SerializedName("CanGetProgram")
    @Expose
    private Integer canGetProgram;
    @SerializedName("CanGetDarkhastTelephoni")
    @Expose
    private Integer canGetDarkhastTelephoni;
    @SerializedName("CanGetPhotoChidman")
    @Expose
    private Integer canGetPhotoChidman;
    @SerializedName("CanChangeMoshtaryPosition")
    @Expose
    private Integer canChangeMoshtaryPosition;
    @SerializedName("NoeForoshandehMamorPakhsh")
    @Expose
    private Integer noeForoshandehMamorPakhsh;
    private String nameNoeForoshandehMamorPakhsh;
    @SerializedName("ccMarkazForosh")
    @Expose
    private Integer ccMarkazForosh;
    @SerializedName("ccMarkazAnbar")
    @Expose
    private Integer ccMarkazAnbar;
    @SerializedName("ccMarkazAnbarVosol")
    @Expose
    private Integer ccMarkazAnbarVosol;
    @SerializedName("NameMarkazForosh")
    @Expose
    private String nameMarkazForosh;
    @SerializedName("NameMarkazAnbar")
    @Expose
    private String nameMarkazAnbar;
    @SerializedName("MaxTedadCheckBargashty")
    @Expose
    private Integer maxTedadCheckBargashty;
    @SerializedName("MaxMablaghCheckBargashty")
    @Expose
    private Long maxMablaghCheckBargashty;
    @SerializedName("MaxModatCheckBargashty")
    @Expose
    private Integer maxModatCheckBargashty;
    @SerializedName("Max_ResidCheck")
    @Expose
    private Integer maxResidCheck;
    @SerializedName("Max_ResidNaghd")
    @Expose
    private Integer maxResidNaghd;
    @SerializedName("ccAfradModir")
    @Expose
    private Integer ccAfradModir;
    @SerializedName("NameMarkazSazmanForoshSakhtarForosh")
    @Expose
    private String nameMarkazSazmanForoshSakhtarForosh;
    @SerializedName("NameSazmanForosh")
    @Expose
    private String nameSazmanForosh;
    @SerializedName("TelephoneShobeh")
    @Expose
    private String telephoneShobeh;
    @SerializedName("TelephoneForoshandehMamorPakhsh")
    @Expose
    private String telephoneForoshandehMamorPakhsh;
    @SerializedName("PosNumber")
    @Expose
    private String posNumber;
    @SerializedName("ccPosShomarehHesab")
    @Expose
    private Integer ccPosShomarehHesab;
    @SerializedName("ccMarkaz")
    @Expose
    private Integer ccMarkaz;
    @SerializedName("IsMojazForSabtDarkhast")
    @Expose
    private Integer isMojazForSabtDarkhast;
    @SerializedName("ccMarkazSazmanForoshSakhtarForosh")
    @Expose
    private Integer ccMarkazSazmanForoshSakhtarForosh;
    @SerializedName("ccMarkazSazmanForosh")
    @Expose
    private Integer ccMarkazSazmanForosh;
    @SerializedName("ccSazmanForosh")
    @Expose
    private Integer ccSazmanForosh;
    @SerializedName("CanGetMarjoee")
    @Expose
    private Integer canGetMarjoee;
    @SerializedName("NoeSabtMarjoee")
    @Expose
    private Integer noeSabtMarjoee;
    @SerializedName("ccDarkhastFaktorNoeForosh")
    @Expose
    private int ccDarkhastFaktorNoeForosh;	  
    @SerializedName("ccMashin")
    @Expose
    private Integer ccMashin;
    @SerializedName("flag_SabtNaghd")
    @Expose
    private Integer flagSabtNaghd;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ccForoshandehs")
    @Expose
    private String ccForoshandehs;
    @SerializedName("ccAnbar")
    @Expose
    private int ccAnbar;
    @SerializedName("ccAnbarak")
    @Expose
    private int ccAnbarak;
    @SerializedName("CheckOlaviatMoshtary")
    @Expose
    private int CheckOlaviatMoshtary;
    @SerializedName("FromDateKharejAzMahal")
    @Expose
    private String FromDateKharejAzMahal;
    @SerializedName("EndDateKharejAzMahal")
    @Expose
    private String EndDateKharejAzMahal;
    @SerializedName("ccAnbarMarjoee")
    @Expose
    private int ccAnbarMarjoee;
    @SerializedName("CodeNoeAnbarMarjoee")
    @Expose
    private int CodeNoeAnbarMarjoee;

    private Integer extraPropIsSelect;


    @SerializedName("Version")
    @Expose
    private String version;



    public Integer getCcAfrad() {
        return ccAfrad;
    }
    public void setCcAfrad(Integer ccAfrad) {
        this.ccAfrad = ccAfrad;
    }

    public int getCcAmargar()
    {
        return ccAmargar;
    }
    public void setCcAmargar(int ccAmargar)
    {
        this.ccAmargar = ccAmargar;
    }

    public Integer getNoeMasouliat() {
        return noeMasouliat;
    }

    public void setNoeMasouliat(Integer noeMasouliat) {
        this.noeMasouliat = noeMasouliat;
    }

    public int getCcForoshandeh() {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMamorPakhsh() {
        return ccMamorPakhsh;
    }

    public void setCcMamorPakhsh(int ccMamorPakhsh) {
        this.ccMamorPakhsh = ccMamorPakhsh;
    }

    public String getCodeForoshandeh() {
        return codeForoshandeh;
    }

    public void setCodeForoshandeh(String codeForoshandeh) {
        this.codeForoshandeh = codeForoshandeh;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public Integer getCanSetFaktorKharejAzMahal() {
        return canSetFaktorKharejAzMahal;
    }

    public void setCanSetFaktorKharejAzMahal(Integer canSetFaktorKharejAzMahal) {
        this.canSetFaktorKharejAzMahal = canSetFaktorKharejAzMahal;
    }

    public Integer getCanGetProgram() {
        return canGetProgram;
    }

    public void setCanGetProgram(Integer canGetProgram) {
        this.canGetProgram = canGetProgram;
    }

    public Integer getCanGetDarkhastTelephoni() {
        return canGetDarkhastTelephoni;
    }

    public void setCanGetDarkhastTelephoni(Integer canGetDarkhastTelephoni) {
        this.canGetDarkhastTelephoni = canGetDarkhastTelephoni;
    }

    public Integer getCanGetPhotoChidman() {
        return canGetPhotoChidman;
    }

    public void setCanGetPhotoChidman(Integer canGetPhotoChidman) {
        this.canGetPhotoChidman = canGetPhotoChidman;
    }

    public Integer getCanChangeMoshtaryPosition() {
        return canChangeMoshtaryPosition;
    }

    public void setCanChangeMoshtaryPosition(Integer canChangeMoshtaryPosition) {
        this.canChangeMoshtaryPosition = canChangeMoshtaryPosition;
    }

    public Integer getNoeForoshandehMamorPakhsh() {
        return noeForoshandehMamorPakhsh;
    }

    public void setNoeForoshandehMamorPakhsh(Integer noeForoshandehMamorPakhsh) {
        this.noeForoshandehMamorPakhsh = noeForoshandehMamorPakhsh;
    }

    public String getNameNoeForoshandehMamorPakhsh() {
        return nameNoeForoshandehMamorPakhsh;
    }

    public void setNameNoeForoshandehMamorPakhsh(String nameNoeForoshandehMamorPakhsh) {
        this.nameNoeForoshandehMamorPakhsh = nameNoeForoshandehMamorPakhsh;
    }

    public Integer getCcMarkazForosh() {
        return ccMarkazForosh;
    }

    public void setCcMarkazForosh(Integer ccMarkazForosh) {
        this.ccMarkazForosh = ccMarkazForosh;
    }

    public Integer getCcMarkazAnbar() {
        return ccMarkazAnbar;
    }

    public void setCcMarkazAnbar(Integer ccMarkazAnbar) {
        this.ccMarkazAnbar = ccMarkazAnbar;
    }

    public Integer getCcMarkazAnbarVosol() {
        return ccMarkazAnbarVosol;
    }

    public void setCcMarkazAnbarVosol(Integer ccMarkazAnbarVosol) {
        this.ccMarkazAnbarVosol = ccMarkazAnbarVosol;
    }

    public String getNameMarkazForosh() {
        return nameMarkazForosh;
    }

    public void setNameMarkazForosh(String nameMarkazForosh) {
        this.nameMarkazForosh = nameMarkazForosh;
    }

    public String getNameMarkazAnbar() {
        return nameMarkazAnbar;
    }

    public void setNameMarkazAnbar(String nameMarkazAnbar) {
        this.nameMarkazAnbar = nameMarkazAnbar;
    }

    public Integer getMaxTedadCheckBargashty() {
        return maxTedadCheckBargashty;
    }

    public void setMaxTedadCheckBargashty(Integer maxTedadCheckBargashty) {
        this.maxTedadCheckBargashty = maxTedadCheckBargashty;
    }

    public Long getMaxMablaghCheckBargashty() {
        return maxMablaghCheckBargashty;
    }

    public void setMaxMablaghCheckBargashty(Long maxMablaghCheckBargashty) {
        this.maxMablaghCheckBargashty = maxMablaghCheckBargashty;
    }

    public Integer getMaxModatCheckBargashty() {
        return maxModatCheckBargashty;
    }

    public void setMaxModatCheckBargashty(Integer maxModatCheckBargashty) {
        this.maxModatCheckBargashty = maxModatCheckBargashty;
    }

    public Integer getMaxResidCheck() {
        return maxResidCheck;
    }

    public void setMaxResidCheck(Integer maxResidCheck) {
        this.maxResidCheck = maxResidCheck;
    }

    public Integer getMaxResidNaghd() {
        return maxResidNaghd;
    }

    public void setMaxResidNaghd(Integer maxResidNaghd) {
        this.maxResidNaghd = maxResidNaghd;
    }

    public Integer getCcAfradModir() {
        return ccAfradModir;
    }

    public void setCcAfradModir(Integer ccAfradModir) {
        this.ccAfradModir = ccAfradModir;
    }

    public String getNameMarkazSazmanForoshSakhtarForosh() {
        return nameMarkazSazmanForoshSakhtarForosh;
    }

    public void setNameMarkazSazmanForoshSakhtarForosh(String nameMarkazSazmanForoshSakhtarForosh) {
        this.nameMarkazSazmanForoshSakhtarForosh = nameMarkazSazmanForoshSakhtarForosh;
    }

    public String getNameSazmanForosh() {
        return nameSazmanForosh;
    }

    public void setNameSazmanForosh(String nameSazmanForosh) {
        this.nameSazmanForosh = nameSazmanForosh;
    }

    public String getTelephoneShobeh() {
        return telephoneShobeh;
    }

    public void setTelephoneShobeh(String telephoneShobeh) {
        this.telephoneShobeh = telephoneShobeh;
    }

    public String getTelephoneForoshandehMamorPakhsh() {
        return telephoneForoshandehMamorPakhsh;
    }

    public void setTelephoneForoshandehMamorPakhsh(String telephoneForoshandehMamorPakhsh) {
        this.telephoneForoshandehMamorPakhsh = telephoneForoshandehMamorPakhsh;
    }

    public String getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(String posNumber) {
        this.posNumber = posNumber;
    }

    public Integer getCcPosShomarehHesab() {
        return ccPosShomarehHesab;
    }

    public void setCcPosShomarehHesab(Integer ccPosShomarehHesab) {
        this.ccPosShomarehHesab = ccPosShomarehHesab;
    }

    public Integer getCcMarkaz() {
        return ccMarkaz;
    }

    public void setCcMarkaz(Integer ccMarkaz) {
        this.ccMarkaz = ccMarkaz;
    }

    public Integer getIsMojazForSabtDarkhast() {
        return isMojazForSabtDarkhast;
    }

    public void setIsMojazForSabtDarkhast(Integer isMojazForSabtDarkhast) {
        this.isMojazForSabtDarkhast = isMojazForSabtDarkhast;
    }

    public Integer getCcMarkazSazmanForoshSakhtarForosh() {
        return ccMarkazSazmanForoshSakhtarForosh;
    }

    public void setCcMarkazSazmanForoshSakhtarForosh(Integer ccMarkazSazmanForoshSakhtarForosh) {
        this.ccMarkazSazmanForoshSakhtarForosh = ccMarkazSazmanForoshSakhtarForosh;
    }

    public Integer getCcMarkazSazmanForosh() {
        return ccMarkazSazmanForosh;
    }

    public void setCcMarkazSazmanForosh(Integer ccMarkazSazmanForosh) {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    public Integer getCcSazmanForosh() {
        return ccSazmanForosh;
    }

    public void setCcSazmanForosh(Integer ccSazmanForosh) {
        this.ccSazmanForosh = ccSazmanForosh;
    }

    public Integer getCanGetMarjoee() {
        return canGetMarjoee;
    }

    public void setCanGetMarjoee(Integer canGetMarjoee) {
        this.canGetMarjoee = canGetMarjoee;
    }

    public Integer getNoeSabtMarjoee()
    {
        return noeSabtMarjoee;
    }

    public void setNoeSabtMarjoee(Integer noeSabtMarjoee)
    {
        this.noeSabtMarjoee = noeSabtMarjoee;
    }

	public int getCcDarkhastFaktorNoeForosh()
    {
        return ccDarkhastFaktorNoeForosh;
    }
    public void setCcDarkhastFaktorNoeForosh(int ccDarkhastFaktorNoeForosh)
    {
        this.ccDarkhastFaktorNoeForosh = ccDarkhastFaktorNoeForosh;
    }

    public Integer getCcMashin() {
        return ccMashin;
    }

    public void setCcMashin(Integer ccMashin) {
        this.ccMashin = ccMashin;
    }

    public Integer getFlagSabtNaghd() {
        return flagSabtNaghd;
    }

    public void setFlagSabtNaghd(Integer flagSabtNaghd) {
        this.flagSabtNaghd = flagSabtNaghd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCcAnbarMarjoee() {
        return ccAnbarMarjoee;
    }

    public void setCcAnbarMarjoee(int ccAnbarMarjoee) {
        this.ccAnbarMarjoee = ccAnbarMarjoee;
    }

    public int getCodeNoeAnbarMarjoee() {
        return CodeNoeAnbarMarjoee;
    }

    public void setCodeNoeAnbarMarjoee(int codeNoeAnbarMarjoee) {
        CodeNoeAnbarMarjoee = codeNoeAnbarMarjoee;
    }

    public Integer getExtraPropIsSelect() {
        return extraPropIsSelect;
    }

    public void setExtraPropIsSelect(Integer extraPropIsSelect) {
        this.extraPropIsSelect = extraPropIsSelect;
    }
    public String getCcForoshandehs() {
        return ccForoshandehs;
    }
    public void setCcForoshandehs(String ccForoshandehs) {
        this.ccForoshandehs = ccForoshandehs;
    }
    public int getCcAnbar() {
        return ccAnbar;
    }
    public void setCcAnbar(int ccAnbar) {
        this.ccAnbar = ccAnbar;
    }
    public int getCcAnbarak() {
        return ccAnbarak;
    }
    public void setCcAnbarak(int ccAnbarak) {
        this.ccAnbarak = ccAnbarak;
    }

    public int getCheckOlaviatMoshtary()
    {

        return CheckOlaviatMoshtary;
    }
    public void setCheckOlaviatMoshtary(int checkOlaviatMoshtary)
    {
        CheckOlaviatMoshtary = checkOlaviatMoshtary;
    }

    public String getFromDateKharejAzMahal()
    {
        return FromDateKharejAzMahal;
    }
    public void setFromDateKharejAzMahal(String fromDateKharejAzMahal)
    {
        FromDateKharejAzMahal = fromDateKharejAzMahal;
    }

    public String getEndDateKharejAzMahal()
    {
        return EndDateKharejAzMahal;
    }
    public void setEndDateKharejAzMahal(String endDateKharejAzMahal)
    {
        EndDateKharejAzMahal = endDateKharejAzMahal;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    private View.OnClickListener btnGetProgramClickListener;
    public View.OnClickListener getBtnGetProgramClickListener() {
        return btnGetProgramClickListener;
    }
    public void setBtnGetProgramClickListener(View.OnClickListener requestBtnClickListener) {
        this.btnGetProgramClickListener = requestBtnClickListener;
    }

    private View.OnClickListener layShowDateAlertClickListener;
    public View.OnClickListener getbtnShowDateAlertClickListener() {
        return layShowDateAlertClickListener;
    }
    public void setbtnShowDateAlertClickListener(View.OnClickListener requestBtnClickListener)
    {
        this.layShowDateAlertClickListener = requestBtnClickListener;
    }

    private View.OnClickListener btnUpdateForoshandehClickListener;
    public View.OnClickListener getBtnUpdateForoshandehClickListener() {
        return btnUpdateForoshandehClickListener;
    }
    public void setBtnUpdateForoshandehClickListener(View.OnClickListener btnUpdateForoshandehClickListener) {
        this.btnUpdateForoshandehClickListener = btnUpdateForoshandehClickListener;
    }


    private View.OnClickListener btnUpdateKalaModatVosolClickListener;
    public View.OnClickListener getBtnUpdateKalaModatVosolClickListener() {
        return btnUpdateKalaModatVosolClickListener;
    }
    public void setBtnUpdateKalaModatVosolClickListener(View.OnClickListener btnUpdateKalaModatVosolClickListener) {
        this.btnUpdateKalaModatVosolClickListener = btnUpdateKalaModatVosolClickListener;
    }


    private View.OnClickListener btnUpdateJayezehTakhfifClickListener;
    public View.OnClickListener getBtnUpdateJayezehTakhfifClickListener() {
        return btnUpdateJayezehTakhfifClickListener;
    }
    public void setBtnUpdateJayezehTakhfifClickListener(View.OnClickListener btnUpdateJayezehTakhfifClickListener) {
        this.btnUpdateJayezehTakhfifClickListener = btnUpdateJayezehTakhfifClickListener;
    }

    private View.OnClickListener btnUpdateCustomersClickListener;
    public View.OnClickListener getBtnUpdateCustomersClickListener() {
        return btnUpdateCustomersClickListener;
    }
    public void setBtnUpdateCustomersClickListener(View.OnClickListener btnUpdateCustomersClickListener) {
        this.btnUpdateCustomersClickListener = btnUpdateCustomersClickListener;
    }



    private View.OnClickListener btnUpdateEtebarForoshandehClickListener;
    public View.OnClickListener getBtnUpdateEtebarForoshandehClickListener() {
        return btnUpdateEtebarForoshandehClickListener;
    }
    public void setBtnUpdateEtebarForoshandehClickListener(View.OnClickListener btnUpdateEtebarForoshandehClickListener) {
        this.btnUpdateEtebarForoshandehClickListener = btnUpdateEtebarForoshandehClickListener;
    }



    private View.OnClickListener btnUpdateParametersClickListener;
    public View.OnClickListener getBtnUpdateParametersClickListener() {
        return btnUpdateParametersClickListener;
    }
    public void setBtnUpdateParametersClickListener(View.OnClickListener btnUpdateParametersClickListener) {
        this.btnUpdateParametersClickListener = btnUpdateParametersClickListener;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "ForoshandehMamorPakhshModel{" +
                "ccAfrad=" + ccAfrad +
                ", ccAmargar=" + ccAmargar +
                ", noeMasouliat=" + noeMasouliat +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMamorPakhsh=" + ccMamorPakhsh +
                ", codeForoshandeh='" + codeForoshandeh + '\'' +
                ", fullName='" + fullName + '\'' +
                ", deviceSerialNumber='" + deviceSerialNumber + '\'' +
                ", canSetFaktorKharejAzMahal=" + canSetFaktorKharejAzMahal +
                ", canGetProgram=" + canGetProgram +
                ", canGetDarkhastTelephoni=" + canGetDarkhastTelephoni +
                ", canGetPhotoChidman=" + canGetPhotoChidman +
                ", canChangeMoshtaryPosition=" + canChangeMoshtaryPosition +
                ", noeForoshandehMamorPakhsh=" + noeForoshandehMamorPakhsh +
                ", nameNoeForoshandehMamorPakhsh='" + nameNoeForoshandehMamorPakhsh + '\'' +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", ccMarkazAnbar=" + ccMarkazAnbar +
                ", ccMarkazAnbarVosol=" + ccMarkazAnbarVosol +
                ", nameMarkazForosh='" + nameMarkazForosh + '\'' +
                ", nameMarkazAnbar='" + nameMarkazAnbar + '\'' +
                ", maxTedadCheckBargashty=" + maxTedadCheckBargashty +
                ", maxMablaghCheckBargashty=" + maxMablaghCheckBargashty +
                ", maxModatCheckBargashty=" + maxModatCheckBargashty +
                ", maxResidCheck=" + maxResidCheck +
                ", maxResidNaghd=" + maxResidNaghd +
                ", ccAfradModir=" + ccAfradModir +
                ", nameMarkazSazmanForoshSakhtarForosh='" + nameMarkazSazmanForoshSakhtarForosh + '\'' +
                ", nameSazmanForosh='" + nameSazmanForosh + '\'' +
                ", telephoneShobeh='" + telephoneShobeh + '\'' +
                ", telephoneForoshandehMamorPakhsh='" + telephoneForoshandehMamorPakhsh + '\'' +
                ", posNumber='" + posNumber + '\'' +
                ", ccPosShomarehHesab=" + ccPosShomarehHesab +
                ", ccMarkaz=" + ccMarkaz +
                ", isMojazForSabtDarkhast=" + isMojazForSabtDarkhast +
                ", ccMarkazSazmanForoshSakhtarForosh=" + ccMarkazSazmanForoshSakhtarForosh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccSazmanForosh=" + ccSazmanForosh +
                ", canGetMarjoee=" + canGetMarjoee +
                ", noeSabtMarjoee=" + noeSabtMarjoee +
                ", ccDarkhastFaktorNoeForosh=" + ccDarkhastFaktorNoeForosh +
                ", ccMashin=" + ccMashin +
                ", flagSabtNaghd=" + flagSabtNaghd +
                ", id=" + id +
                ", ccForoshandehs='" + ccForoshandehs + '\'' +
                ", ccAnbar=" + ccAnbar +
                ", ccAnbarak=" + ccAnbarak +
                ", CheckOlaviatMoshtary=" + CheckOlaviatMoshtary +
                ", FromDateKharejAzMahal='" + FromDateKharejAzMahal + '\'' +
                ", EndDateKharejAzMahal='" + EndDateKharejAzMahal + '\'' +
                ", extraPropIsSelect=" + extraPropIsSelect +
                ", btnGetProgramClickListener=" + btnGetProgramClickListener +
                ", layShowDateAlertClickListener=" + layShowDateAlertClickListener +
                ", btnUpdateForoshandehClickListener=" + btnUpdateForoshandehClickListener +
                ", btnUpdateKalaModatVosolClickListener=" + btnUpdateKalaModatVosolClickListener +
                ", btnUpdateJayezehTakhfifClickListener=" + btnUpdateJayezehTakhfifClickListener +
                ", btnUpdateCustomersClickListener=" + btnUpdateCustomersClickListener +
                ", btnUpdateEtebarForoshandehClickListener=" + btnUpdateEtebarForoshandehClickListener +
                ", btnUpdateParametersClickListener=" + btnUpdateParametersClickListener +
                '}';
    }
}
