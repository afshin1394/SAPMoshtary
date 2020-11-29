package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class JayezehEntekhabiModel
{

    private static final String TABLE_NAME = "JayezehEntekhabi";
    private static final String COLUMN_ccJayezeh = "ccJayezeh";
    private static final String COLUMN_ccJayezehSatr = "ccJayezehSatr";
    private static final String COLUMN_ccJayezehSatrKala = "ccJayezehSatrKala";
    private static final String COLUMN_ccKala = "ccKala";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKalaOld = "CodeKalaOld";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_ccMarkazForosh = "ccMarkazForosh";
    private static final String COLUMN_MablaghForosh = "MablaghForosh";
    private static final String COLUMN_ExtraProp_Tedad = "ExtraProp_Tedad";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_ccTakhfifHajmi = "ccTakhfifHajmi";
    private static final String COLUMN_ccKalaCodeAsli = "ccKalaCodeAsli";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccJayezeh() {
        return COLUMN_ccJayezeh;
    }
    public static String COLUMN_ccJayezehSatr() {
        return COLUMN_ccJayezehSatr;
    }
    public static String COLUMN_ccJayezehSatrKala() {
        return COLUMN_ccJayezehSatrKala;
    }
    public static String COLUMN_ccKala() {
        return COLUMN_ccKala;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKalaOld() {
        return COLUMN_CodeKalaOld;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_ccMarkazForosh() {
        return COLUMN_ccMarkazForosh;
    }
    public static String COLUMN_MablaghForosh() {
        return COLUMN_MablaghForosh;
    }
    public static String COLUMN_ExtraProp_Tedad() {
        return COLUMN_ExtraProp_Tedad;
    }
    public static String COLUMN_ccNoeMoshtary() {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_ccTakhfifHajmi() {
        return COLUMN_ccTakhfifHajmi;
    }
    public static String COLUMN_ccKalaCodeAsli() {
        return COLUMN_ccKalaCodeAsli;
    }



    private int ccJayezeh;
    public void setCcJayezeh(int ccJayezeh){
        this.ccJayezeh = ccJayezeh;
    }
    public int getCcJayezeh(){
        return this.ccJayezeh;
    }


    private int ccJayezehSatr;
    public void setCcJayezehSatr(int ccJayezehSatr){
        this.ccJayezehSatr = ccJayezehSatr;
    }
    public int getCcJayezehSatr(){
        return this.ccJayezehSatr;
    }


    private int ccJayezehSatrKala;
    public void setCcJayezehSatrKala(int ccJayezehSatrKala){
        this.ccJayezehSatrKala = ccJayezehSatrKala;
    }
    public int getCcJayezehSatrKala(){
        return this.ccJayezehSatrKala;
    }


    private int ccKala;
    public void setCcKala(int ccKala){
        this.ccKala = ccKala;
    }
    public int getCcKala(){
        return this.ccKala;
    }


    private int ccKalaCode;
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }


    private int ccKalaCodeAsli;
    public void setCcKalaCodeAsli(int ccKalaCodeAsli){
        this.ccKalaCodeAsli = ccKalaCodeAsli;
    }
    public int getCcKalaCodeAsli(){
        return this.ccKalaCodeAsli;
    }


    private String CodeKala;
    public void setCodeKala(String CodeKala){
        this.CodeKala = CodeKala;
    }
    public String getCodeKala(){
        return this.CodeKala;
    }


    private String CodeKalaOld;
    public void setCodeKalaOld(String CodeKalaOld){
        this.CodeKalaOld = CodeKalaOld;
    }
    public String getCodeKalaOld(){
        return this.CodeKalaOld;
    }


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private int ccMarkazForosh;
    public void setCcMarkazForosh(int ccMarkazForosh){
        this.ccMarkazForosh = ccMarkazForosh;
    }
    public int getCcMarkazForosh(){
        return this.ccMarkazForosh;
    }


    private float MablaghForosh;
    public void setMablaghForosh(float MablaghForosh){
        this.MablaghForosh = MablaghForosh;
    }
    public float getMablaghForosh(){
        return this.MablaghForosh;
    }


    private int ccNoeMoshtary;
    public void setCcNoeMoshtary(int ccNoeMoshtary){
        this.ccNoeMoshtary = ccNoeMoshtary;
    }
    public int getCcNoeMoshtary(){
        return this.ccNoeMoshtary;
    }


    private int CodeNoe;
    public void setCodeNoe(int CodeNoe){
        this.CodeNoe = CodeNoe;
    }
    public int getCodeNoe(){
        return this.CodeNoe;
    }


    private int ccTakhfifHajmi;
    public void setCcTakhfifHajmi(int ccTakhfifHajmi){
        this.ccTakhfifHajmi = ccTakhfifHajmi;
    }
    public int getCcTakhfifHajmi(){
        return this.ccTakhfifHajmi;
    }


    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    private int ExtraProp_Tedad;
    public int getExtraProp_Tedad() {
        return ExtraProp_Tedad;
    }
    public void setExtraProp_Tedad(int extraProp_Tedad) {
        ExtraProp_Tedad = extraProp_Tedad;
    }


    @NonNull
    @Override
    public String toString()
    {
        return  "ccJayezeh=" + ccJayezeh +
                ", ccJayezehSatr=" + ccJayezehSatr +
                ", ccJayezehSatrKala=" + ccJayezehSatrKala +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", ccKalaCodeAsli=" + ccKalaCodeAsli +
                ", CodeKala='" + CodeKala + '\'' +
                ", CodeKalaOld='" + CodeKalaOld + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccMarkazForosh=" + ccMarkazForosh +
                ", MablaghForosh=" + MablaghForosh +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", CodeNoe=" + CodeNoe +
                ", ccTakhfifHajmi=" + ccTakhfifHajmi +
                ", Id=" + Id +
                ", ExtraProp_Tedad=" + ExtraProp_Tedad;
    }



}
