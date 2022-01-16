package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MandehMojodyMashinModel
{

    private static final String TABLE_NAME = "MandehMojodyMashin";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeNoeKala = "CodeNoeKala";
    private static final String COLUMN_Mojody = "Mojody";
    private static final String COLUMN_ShomarehBach = "ShomarehBach";
    private static final String COLUMN_TarikhTolid = "TarikhTolid";
    private static final String COLUMN_TarikhEngheza = "TarikhEngheza";
    private static final String COLUMN_GheymatMasrafKonandeh = "GheymatMasrafKonandeh";
    private static final String COLUMN_GheymatForosh = "GheymatForosh";
    private static final String COLUMN_ccTaminKonandeh = "ccTaminKonandeh";
    private static final String COLUMN_Max_Mojody = "Max_Mojody";
    private static final String COLUMN_Max_MojodyByShomarehBach = "Max_MojodyByShomarehBach";
    private static final String COLUMN_IsAdamForosh = "IsAdamForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeNoeKala() {
        return COLUMN_CodeNoeKala;
    }
    public static String COLUMN_Mojody() {
        return COLUMN_Mojody;
    }
    public static String COLUMN_ShomarehBach() {
        return COLUMN_ShomarehBach;
    }
    public static String COLUMN_TarikhTolid() {
        return COLUMN_TarikhTolid;
    }
    public static String COLUMN_TarikhEngheza() {
        return COLUMN_TarikhEngheza;
    }
    public static String COLUMN_GheymatMasrafKonandeh() {
        return COLUMN_GheymatMasrafKonandeh;
    }
    public static String COLUMN_GheymatForosh() {
        return COLUMN_GheymatForosh;
    }
    public static String COLUMN_ccTaminKonandeh() {
        return COLUMN_ccTaminKonandeh;
    }
    public static String COLUMN_Max_Mojody() {
        return COLUMN_Max_Mojody;
    }
    public static String COLUMN_Max_MojodyByShomarehBach() {
        return COLUMN_Max_MojodyByShomarehBach;
    }
    public static String COLUMN_IsAdamForosh() {
        return COLUMN_IsAdamForosh;
    }


    private int Id;
    private int Radif;
    private int ccKalaCode;
    private int CodeNoeKala;
    private int Mojody;
    private String ShomarehBach;
    private String TarikhTolid;
    private String TarikhEngheza;
    private int GheymatMasrafKonandeh;
    private int GheymatForosh;
    private int ccTaminKonandeh;
    private int Max_Mojody;
    private int Max_MojodyByShomarehBach;
    private int IsAdamForosh;


    public void setRadif(int Radif){
        this.Radif = Radif;
    }
    public int getRadif(){
        return this.Radif;
    }

    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }

    public void setCodeNoeKala(int CodeNoeKala){
        this.CodeNoeKala = CodeNoeKala;
    }
    public int getCodeNoeKala(){
        return this.CodeNoeKala;
    }

    public void setMojody(int Mojody){
        this.Mojody = Mojody;
    }
    public int getMojody(){
        return this.Mojody;
    }

    public void setShomarehBach(String ShomarehBach){
        this.ShomarehBach = ShomarehBach;
    }
    public String getShomarehBach(){
        return this.ShomarehBach;
    }

    public void setTarikhTolid(String TarikhTolid){
        this.TarikhTolid = TarikhTolid;
    }
    public String getTarikhTolid(){
        return this.TarikhTolid;
    }

    public void setTarikhEngheza(String TarikhEngheza){
        this.TarikhEngheza = TarikhEngheza;
    }
    public String getTarikhEngheza(){
        return this.TarikhEngheza;
    }

    public void setGheymatMasrafKonandeh(int GheymatMasrafKonandeh){
        this.GheymatMasrafKonandeh = GheymatMasrafKonandeh;
    }
    public int getGheymatMasrafKonandeh(){
        return this.GheymatMasrafKonandeh;
    }

    public void setGheymatForosh(int GheymatForosh){
        this.GheymatForosh = GheymatForosh;
    }
    public int getGheymatForosh(){
        return this.GheymatForosh;
    }

    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }
    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }

    public int getMaxMojody() {
        return Max_Mojody;
    }
    public void setMaxMojody(int max_Mojody) {
        Max_Mojody = max_Mojody;
    }

    public int getMax_MojodyByShomarehBach()
    {
        return Max_MojodyByShomarehBach;
    }
    public void setMax_MojodyByShomarehBach(int max_MojodyByShomarehBach)
    {
        Max_MojodyByShomarehBach = max_MojodyByShomarehBach;
    }

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }

    public int getIsAdamForosh()
    {
        return IsAdamForosh;
    }
    public void setIsAdamForosh(int IsAdamForosh)
    {
        this.IsAdamForosh = IsAdamForosh;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MandehMojodyMashinModel{" +
                "Id=" + Id +
                ", Radif=" + Radif +
                ", ccKalaCode=" + ccKalaCode +
                ", CodeNoeKala=" + CodeNoeKala +
                ", Mojody=" + Mojody +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", GheymatMasrafKonandeh=" + GheymatMasrafKonandeh +
                ", GheymatForosh=" + GheymatForosh +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", Max_Mojody=" + Max_Mojody +
                ", Max_MojodyByShomarehBach=" + Max_MojodyByShomarehBach +
                ", IsAdamForosh=" + IsAdamForosh +
                '}';
    }

}
