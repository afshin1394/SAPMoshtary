package com.saphamrah.Model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoshtaryModel
{

    private static final String TABLE_NAME = "Moshtary";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_ccAfrad = "ccAfrad";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_NameTablo = "NameTablo";
    private static final String COLUMN_Olaviat = "Olaviat";
    private static final String COLUMN_ModateVosol = "ModateVosol";
    private static final String COLUMN_CodeMoshtary = "CodeMoshtary";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_Mobile = "Mobile";
    private static final String COLUMN_CodeNoeVosolAzMoshtary = "CodeNoeVosolAzMoshtary";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMasir = "ccMasir";
    private static final String COLUMN_ToorVisit = "ToorVisit";
    private static final String COLUMN_CodeNoeHaml = "CodeNoeHaml";
    private static final String COLUMN_FNameMoshtary = "FNameMoshtary";
    private static final String COLUMN_LNameMoshtary = "LNameMoshtary";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_NameDarajeh = "NameDarajeh";
    private static final String COLUMN_CodeMely = "CodeMely";
    private static final String COLUMN_CodeNoeShakhsiat = "CodeNoeShakhsiat";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_ShenasehMely = "ShenasehMely";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_MasahatMaghazeh = "MasahatMaghazeh";
    private static final String COLUMN_HasAnbar = "HasAnbar";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_ExtraProp_ccMoshtaryParent = "ExtraProp_ccMoshtaryParent";
    private static final String COLUMN_ExtraProp_IsMoshtaryAmargar = "ExtraProp_IsMoshtaryAmargar";
    private static final String COLUMN_ExtraProp_ccPorseshnameh = "ExtraProp_ccPorseshnameh";
    private static final String COLUMN_ExtraProp_NoeForoshandeh_First = "ExtraProp_NoeForoshandeh_First";
    private static final String COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir = "ExtraProp_MoshtaryMojazKharejAzMasir";
    private static final String COLUMN_ExtraProp_Olaviat = "ExtraProp_Olaviat";
    private static final String COLUMN_DateOfMasir = "DateOfMasir";
    private static final String COLUMN_ControlEtebarForoshandeh = "ControlEtebarForoshandeh";
    private static final String COLUMN_ModateNaghd = "ModateNaghd";
    private static final String COLUMN_TarikhMoarefiMoshtary = "TarikhMoarefiMoshtary";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMoshtary()
    {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_ccAfrad()
    {
        return COLUMN_ccAfrad;
    }
    public static String COLUMN_NameMoshtary()
    {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_NameTablo()
    {
        return COLUMN_NameTablo;
    }
    public static String COLUMN_Olaviat()
    {
        return COLUMN_Olaviat;
    }
    public static String COLUMN_ModateVosol()
    {
        return COLUMN_ModateVosol;
    }
    public static String COLUMN_CodeMoshtary()
    {
        return COLUMN_CodeMoshtary;
    }
    public static String COLUMN_Address()
    {
        return COLUMN_Address;
    }
    public static String COLUMN_Mobile()
    {
        return COLUMN_Mobile;
    }
    public static String COLUMN_CodeNoeVosolAzMoshtary()
    {
        return COLUMN_CodeNoeVosolAzMoshtary;
    }
    public static String COLUMN_ccForoshandeh()
    {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMasir()
    {
        return COLUMN_ccMasir;
    }
    public static String COLUMN_ToorVisit()
    {
        return COLUMN_ToorVisit;
    }
    public static String COLUMN_CodeNoeHaml()
    {
        return COLUMN_CodeNoeHaml;
    }
    public static String COLUMN_FNameMoshtary()
    {
        return COLUMN_FNameMoshtary;
    }
    public static String COLUMN_LNameMoshtary()
    {
        return COLUMN_LNameMoshtary;
    }
    public static String COLUMN_Darajeh()
    {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_NameDarajeh()
    {
        return COLUMN_NameDarajeh;
    }
    public static String COLUMN_CodeMely()
    {
        return COLUMN_CodeMely;
    }
    public static String COLUMN_CodeNoeShakhsiat()
    {
        return COLUMN_CodeNoeShakhsiat;
    }
    public static String COLUMN_ccNoeSenf()
    {
        return COLUMN_ccNoeSenf;
    }
    public static String COLUMN_ccNoeMoshtary()
    {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_ShenasehMely()
    {
        return COLUMN_ShenasehMely;
    }
    public static String COLUMN_CodeVazeiat()
    {
        return COLUMN_CodeVazeiat;
    }
    public static String COLUMN_MasahatMaghazeh()
    {
        return COLUMN_MasahatMaghazeh;
    }
    public static String COLUMN_HasAnbar()
    {
        return COLUMN_HasAnbar;
    }
    public static String COLUMN_ExtraProp_IsOld()
    {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_ExtraProp_ccMoshtaryParent()
    {
        return COLUMN_ExtraProp_ccMoshtaryParent;
    }
    public static String COLUMN_ExtraProp_IsMoshtaryAmargar()
    {
        return COLUMN_ExtraProp_IsMoshtaryAmargar;
    }
    public static String COLUMN_ExtraProp_ccPorseshnameh()
    {
        return COLUMN_ExtraProp_ccPorseshnameh;
    }
    public static String COLUMN_ExtraProp_NoeForoshandeh_First()
    {
        return COLUMN_ExtraProp_NoeForoshandeh_First;
    }
    public static String COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir()
    {
        return COLUMN_ExtraProp_MoshtaryMojazKharejAzMasir;
    }
    public static String COLUMN_ExtraProp_Olaviat()
    {
        return COLUMN_ExtraProp_Olaviat;
    }
    public static String COLUMN_DateOfMasir() 
	{
        return COLUMN_DateOfMasir;
    }
    public static String COLUMN_ControlEtebarForoshandeh()
	{
        return COLUMN_ControlEtebarForoshandeh;
    }
    public static String COLUMN_ModateNaghd()
	{
        return COLUMN_ModateNaghd;
    }
    public static String COLUMN_TarikhMoarefiMoshtary()
    {
        return COLUMN_TarikhMoarefiMoshtary;
    }



    @SerializedName("ccMoshtary")
    @Expose
    private int ccMoshtary;
    @SerializedName("CodeMoshtary")
    @Expose
    private String CodeMoshtary;
    @SerializedName("ccAfrad")
    @Expose
    private int ccAfrad;
    @SerializedName("NameMoshtary")
    @Expose
    private String NameMoshtary;
    @SerializedName("FNameMoshtary")
    @Expose
    private String FNameMoshtary;
    @SerializedName("LNameMoshtary")
    @Expose
    private String LNameMoshtary;
    @SerializedName("NameTablo")
    @Expose
    private String NameTablo;
    @SerializedName("CodeNoeVosolAzMoshtary")
    @Expose
    private int CodeNoeVosolAzMoshtary;
    @SerializedName("ModateVosol")
    @Expose
    private int ModateVosol;
    @SerializedName("CodeNoeShakhsiat")
    @Expose
    private int CodeNoeShakhsiat;
    @SerializedName("Darajeh")
    @Expose
    private int Darajeh;
    @SerializedName("NameDarajeh")
    @Expose
    private String NameDarajeh;
    @SerializedName("CodeVazeiat")
    @Expose
    private int CodeVazeiat;
    @SerializedName("CodeMely")
    @Expose
    private String CodeMely;
    @SerializedName("CodeNoeHaml")
    @Expose
    private int CodeNoeHaml;
    @SerializedName("ShenasehMely")
    @Expose
    private String ShenasehMely;
    @SerializedName("MasahatMaghazeh")
    @Expose
    private int MasahatMaghazeh;
    @SerializedName("HasAnbar")
    @Expose
    private int HasAnbar;
    @SerializedName("ccNoeMoshtary")
    @Expose
    private int ccNoeMoshtary;
	@SerializedName("ccNoeSenf")
    @Expose
    private int ccNoeSenf;
    @SerializedName("ccForoshandeh")
    @Expose
    private int ccForoshandeh;
    @SerializedName("ccMasir")
    @Expose
    private int ccMasir;
    @SerializedName("ControlEtebarForoshandeh")
    @Expose
    private int ControlEtebarForoshandeh;
    @SerializedName("ModateNaghd")
    @Expose
    private int ModateNaghd;
    @SerializedName("TarikhMoarefiMoshtary")
    @Expose
    private String TarikhMoarefiMoshtary;
	
	
    private int Olaviat;
    private String Address;
    private String Mobile;
    private int ToorVisit;
    private int ExtraProp_IsOld;
    private int ExtraProp_ccMoshtaryParent;
    private int ExtraProp_IsMoshtaryAmargar;
    private int ExtraProp_ccPorseshnameh;
    private int ExtraProp_NoeForoshandeh_First;
    private int ExtraProp_MoshtaryMojazKharejAzMasir;
    private int ExtraProp_Olaviat;
    private String DateOfMasir;

	
	
	public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }

    public int getCcAfrad()
    {
        return ccAfrad;
    }
    public void setCcAfrad(int ccAfrad)
    {
        this.ccAfrad = ccAfrad;
    }

    public void setCodeMoshtary(String CodeMoshtary){
        this.CodeMoshtary = CodeMoshtary;
    }
    public String getCodeMoshtary(){
        return this.CodeMoshtary;
    }

    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }
    public String getNameMoshtary(){
        return this.NameMoshtary;
    }

    public void setFNameMoshtary(String FNameMoshtary){
        this.FNameMoshtary = FNameMoshtary;
    }
    public String getFNameMoshtary(){
        return this.FNameMoshtary;
    }

    public void setLNameMoshtary(String LNameMoshtary){
        this.LNameMoshtary = LNameMoshtary;
    }

    public String getLNameMoshtary(){
        return this.LNameMoshtary;
    }

    public void setNameTablo(String NameTablo){
        this.NameTablo = NameTablo;
    }

    public String getNameTablo(){
        return this.NameTablo;
    }

    public void setCodeNoeVosolAzMoshtary(int CodeNoeVosolAzMoshtary){
        this.CodeNoeVosolAzMoshtary = CodeNoeVosolAzMoshtary;
    }

    public int getCodeNoeVosolAzMoshtary(){
        return this.CodeNoeVosolAzMoshtary;
    }

    public void setModateVosol(int ModateVosol){
        this.ModateVosol = ModateVosol;
    }

    public int getModateVosol(){
        return this.ModateVosol;
    }

    public void setCodeNoeShakhsiat(int CodeNoeShakhsiat){
        this.CodeNoeShakhsiat = CodeNoeShakhsiat;
    }

    public int getCodeNoeShakhsiat(){
        return this.CodeNoeShakhsiat;
    }

    public void setDarajeh(int Darajeh){
        this.Darajeh = Darajeh;
    }

    public int getDarajeh(){
        return this.Darajeh;
    }

    public void setNameDarajeh(String NameDarajeh){
        this.NameDarajeh = NameDarajeh;
    }

    public String getNameDarajeh(){
        return this.NameDarajeh;
    }

    public void setCodeVazeiat(int CodeVazeiat){
        this.CodeVazeiat = CodeVazeiat;
    }

    public int getCodeVazeiat(){
        return this.CodeVazeiat;
    }

    public void setCodeMely(String CodeMely){
        this.CodeMely = CodeMely;
    }

    public String getCodeMely(){
        return this.CodeMely;
    }

    public void setCodeNoeHaml(int CodeNoeHaml){
        this.CodeNoeHaml = CodeNoeHaml;
    }

    public int getCodeNoeHaml(){
        return this.CodeNoeHaml;
    }

    public void setShenasehMely(String ShenasehMely){
        this.ShenasehMely = ShenasehMely;
    }

    public String getShenasehMely(){
        return this.ShenasehMely;
    }

    public void setMasahatMaghazeh(int MasahatMaghazeh){
        this.MasahatMaghazeh = MasahatMaghazeh;
    }

    public int getMasahatMaghazeh(){
        return this.MasahatMaghazeh;
    }

    public void setHasAnbar(int HasAnbar){
        this.HasAnbar = HasAnbar;
    }

    public int getHasAnbar(){
        return this.HasAnbar;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary){
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcNoeMoshtary(){
        return this.ccNoeMoshtary;
    }

    public int getOlaviat() {
        return Olaviat;
    }
    public void setOlaviat(int Olaviat) {
        this.Olaviat = Olaviat;
    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getMobile()
    {
        return Mobile;
    }
    public void setMobile(String mobile)
    {
        Mobile = mobile;
    }

    public int getccForoshandeh() {
        return ccForoshandeh;
    }
    public void setccForoshandeh(int ccForoshandeh) {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcNoeSenf()
    {
        return ccNoeSenf;
    }

    public void setCcNoeSenf(int ccNoeSenf)
    {
        this.ccNoeSenf = ccNoeSenf;
    }

    public int getCcForoshandeh()
    {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh)
    {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMasir()
    {
        return ccMasir;
    }

    public void setCcMasir(int ccMasir)
    {
        this.ccMasir = ccMasir;
    }

    public int getToorVisit() {
        return ToorVisit;
    }
    public void setToorVisit(int ToorVisit) {
        this.ToorVisit = ToorVisit;
    }

    public int getExtraProp_IsOld() {
        return ExtraProp_IsOld;
    }
    public void setExtraProp_IsOld(int extraProp_IsOld) {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public int getExtraProp_ccMoshtaryParent() {
        return ExtraProp_ccMoshtaryParent;
    }
    public void setExtraProp_ccMoshtaryParent(int extraProp_ccMoshtaryParent) {
        ExtraProp_ccMoshtaryParent = extraProp_ccMoshtaryParent;
    }

    public int getExtraProp_IsMoshtaryAmargar() {
        return ExtraProp_IsMoshtaryAmargar;
    }
    public void setExtraProp_IsMoshtaryAmargar(int extraProp_IsMoshtaryAmargar) {
        ExtraProp_IsMoshtaryAmargar = extraProp_IsMoshtaryAmargar;
    }

    public int getExtraProp_ccPorseshnameh() {
        return ExtraProp_ccPorseshnameh;
    }
    public void setExtraProp_ccPorseshnameh(int extraProp_ccPorseshnameh) {
        ExtraProp_ccPorseshnameh = extraProp_ccPorseshnameh;
    }

    public int getExtraProp_NoeForoshandeh_First() {
        return ExtraProp_NoeForoshandeh_First;
    }
    public void setExtraProp_NoeForoshandeh_First(int extraProp_NoeForoshandeh_First) {
        ExtraProp_NoeForoshandeh_First = extraProp_NoeForoshandeh_First;
    }

    public int getExtraProp_MoshtaryMojazKharejAzMasir() {
        return ExtraProp_MoshtaryMojazKharejAzMasir;
    }
    public void setExtraProp_MoshtaryMojazKharejAzMasir(int extraProp_MoshtaryMojazKharejAzMasir) {
        ExtraProp_MoshtaryMojazKharejAzMasir = extraProp_MoshtaryMojazKharejAzMasir;
    }

    public int getExtraProp_Olaviat()
    {
        return ExtraProp_Olaviat;
    }
    public void setExtraProp_Olaviat(int extraProp_Olaviat)
    {
        ExtraProp_Olaviat = extraProp_Olaviat;
    }


    public String getDateOfMasir()
    {
        return DateOfMasir;
    }

    public void setDateOfMasir(String dateOfMasir)
    {
        DateOfMasir = dateOfMasir;
    }

    public int getControlEtebarForoshandeh()
    {
        return ControlEtebarForoshandeh;
    }

    public void setControlEtebarForoshandeh(int controlEtebarForoshandeh)
    {
        ControlEtebarForoshandeh = controlEtebarForoshandeh;
    }

    public int getModateNaghd()
    {
        return ModateNaghd;
    }

    public void setModateNaghd(int modateNaghd)
    {
        ModateNaghd = modateNaghd;
    }

    public String getTarikhMoarefiMoshtary()
    {
        return TarikhMoarefiMoshtary;
    }

    public void setTarikhMoarefiMoshtary(String tarikhMoarefiMoshtary)
    {
        TarikhMoarefiMoshtary = tarikhMoarefiMoshtary;
    }

    /**
     * @deprecated
     * @param moshtaryAddressModels
     * @return
     */
    public String toJsonString(ArrayList<MoshtaryAddressModel> moshtaryAddressModels, ArrayList<MoshtaryAfradModel> moshtaryAfradModels, ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels, ArrayList<MoshtaryPhotoPPCModel> moshtaryPhotoPPCModels, String ccAfrad, String ccMarkazForosh, String ccForoshandeh)
    {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        try
        {
            JSONArray jsonArrayPhoto = new JSONArray();
            for (MoshtaryPhotoPPCModel photo : moshtaryPhotoPPCModels)
            {
                JSONObject jsonObjectPhoto = new JSONObject(photo.toJsonString());
                jsonArrayPhoto.put(jsonObjectPhoto);
            }

            jsonObject.put(COLUMN_CodeMely() , CodeMely.trim());
            jsonObject.put(COLUMN_CodeMoshtary() , CodeMoshtary);
            jsonObject.put("moshtaryShomarehHesabs" , new JSONArray(gson.toJson(moshtaryShomarehHesabModels)));
            jsonObject.put("moshtaryPhotoPPC" , jsonArrayPhoto);
            jsonObject.put(COLUMN_FNameMoshtary() , FNameMoshtary);
            jsonObject.put(COLUMN_LNameMoshtary() , LNameMoshtary);
            jsonObject.put("moshtaryAfrads" , new JSONArray(gson.toJson(moshtaryAfradModels)));
            jsonObject.put("moshtaryAddresses" , new JSONArray(gson.toJson(moshtaryAddressModels)));
            jsonObject.put(COLUMN_NameMoshtary() , NameMoshtary);
            jsonObject.put(COLUMN_NameTablo() , NameTablo);
            jsonObject.put(COLUMN_ShenasehMely() , ShenasehMely);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put(COLUMN_ccMoshtary() , ccMoshtary);
            jsonObject.put("ccNoeMalekiatMoshtary" , "1");
            jsonObject.put("ccUser" , ccAfrad);
            jsonObject.put(COLUMN_ModateVosol() , ModateVosol);
            jsonObject.put(COLUMN_MasahatMaghazeh() , MasahatMaghazeh);
            jsonObject.put(COLUMN_HasAnbar() , HasAnbar);
            jsonObject.put(COLUMN_Darajeh() , Darajeh);
            jsonObject.put(COLUMN_CodeNoeVosolAzMoshtary() , CodeNoeVosolAzMoshtary);
            jsonObject.put(COLUMN_CodeNoeShakhsiat() , CodeNoeShakhsiat);
            jsonObject.put(COLUMN_CodeNoeHaml() , CodeNoeHaml);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = jsonObject.toString().replace("\\" , "");
        return jsonObject.toString().replace("\\" , "");
    }


    public String toJsonString(int ccMarkazForosh)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("NameMoshtary" , NameMoshtary);
            jsonObject.put("FNameMoshtary" , FNameMoshtary);
            jsonObject.put("LNameMoshtary" , LNameMoshtary);
            jsonObject.put("NameTablo" , NameTablo);
            jsonObject.put("CodeNoeVosolAzMoshtary" , CodeNoeVosolAzMoshtary);
            jsonObject.put("ModateVosol" , ModateVosol);
            jsonObject.put("CodeNoeShakhsiat" , CodeNoeShakhsiat);
            jsonObject.put("Darajeh" , Darajeh);
            jsonObject.put("CodeMoshtary" , CodeMoshtary);
            jsonObject.put("CodeMely" , CodeMely);
            jsonObject.put("CodeNoeHaml" , CodeNoeHaml);
            jsonObject.put("ShenasehMely" , ShenasehMely);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("MasahatMaghazeh" , MasahatMaghazeh);
            jsonObject.put("HasAnbar" , HasAnbar);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return jsonObject.toString();
    }


    public JSONObject toJsonObject(int ccMarkazForosh, int ccMarkazSazmanForosh)
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("NameMoshtary" , NameMoshtary);
            jsonObject.put("FNameMoshtary" , FNameMoshtary);
            jsonObject.put("LNameMoshtary" , LNameMoshtary);
            jsonObject.put("NameTablo" , NameTablo);
            jsonObject.put("CodeNoeVosolAzMoshtary" , CodeNoeVosolAzMoshtary);
            jsonObject.put("ModateVosol" , ModateVosol);
            jsonObject.put("CodeNoeShakhsiat" , CodeNoeShakhsiat);
            jsonObject.put("Darajeh" , Darajeh);
            jsonObject.put("CodeMoshtary" , CodeMoshtary);
            jsonObject.put("CodeMely" , CodeMely);
            jsonObject.put("CodeNoeHaml" , CodeNoeHaml);
            jsonObject.put("ShenasehMely" , ShenasehMely);
            jsonObject.put("ccMarkazForosh" , ccMarkazForosh);
            jsonObject.put("ccMarkazSazmanForosh" , ccMarkazSazmanForosh);
            jsonObject.put("MasahatMaghazeh" , MasahatMaghazeh);
            jsonObject.put("HasAnbar" , HasAnbar);
            jsonObject.put("ccNoeSenf" , ccNoeSenf);
            jsonObject.put("ccNoeMoshtary" , ccNoeMoshtary);
            jsonObject.put("ccMasir" , ccMasir);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("olaviat" , Olaviat);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MoshtaryModel{" +
                "ccMoshtary=" + ccMoshtary +
                ", CodeMoshtary='" + CodeMoshtary + '\'' +
                ", ccAfrad=" + ccAfrad +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", FNameMoshtary='" + FNameMoshtary + '\'' +
                ", LNameMoshtary='" + LNameMoshtary + '\'' +
                ", NameTablo='" + NameTablo + '\'' +
                ", CodeNoeVosolAzMoshtary=" + CodeNoeVosolAzMoshtary +
                ", ModateVosol=" + ModateVosol +
                ", CodeNoeShakhsiat=" + CodeNoeShakhsiat +
                ", Darajeh=" + Darajeh +
                ", NameDarajeh='" + NameDarajeh + '\'' +
                ", CodeVazeiat=" + CodeVazeiat +
                ", CodeMely='" + CodeMely + '\'' +
                ", CodeNoeHaml=" + CodeNoeHaml +
                ", ShenasehMely='" + ShenasehMely + '\'' +
                ", MasahatMaghazeh=" + MasahatMaghazeh +
                ", HasAnbar=" + HasAnbar +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccNoeSenf=" + ccNoeSenf +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMasir=" + ccMasir +
                ", ControlEtebarForoshandeh=" + ControlEtebarForoshandeh +
                ", ModateNaghd=" + ModateNaghd +
                ", Olaviat=" + Olaviat +
                ", Address='" + Address + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", ToorVisit=" + ToorVisit +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", ExtraProp_ccMoshtaryParent=" + ExtraProp_ccMoshtaryParent +
                ", ExtraProp_IsMoshtaryAmargar=" + ExtraProp_IsMoshtaryAmargar +
                ", ExtraProp_ccPorseshnameh=" + ExtraProp_ccPorseshnameh +
                ", ExtraProp_NoeForoshandeh_First=" + ExtraProp_NoeForoshandeh_First +
                ", ExtraProp_MoshtaryMojazKharejAzMasir=" + ExtraProp_MoshtaryMojazKharejAzMasir +
                ", ExtraProp_Olaviat=" + ExtraProp_Olaviat +
                ", DateOfMasir='" + DateOfMasir + '\'' +
                '}';
    }
}
