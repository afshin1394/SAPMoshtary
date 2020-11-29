package com.saphamrah.Model;

import android.util.Log;

import androidx.annotation.NonNull;

public class TakhfifHajmiSatrModel
{

    private static final String TABLE_NAME = "TakhfifHajmiSatr";
    private static final String COLUMN_ccTakhfifHajmiSatr = "ccTakhfifHajmiSatr";
    private static final String COLUMN_ccTakhfifHajmi = "ccTakhfifHajmi";
    private static final String COLUMN_NameNoeField = "NameNoeField";
    private static final String COLUMN_ccNoeField = "ccNoeField";
    private static final String COLUMN_Az = "Az";
    private static final String COLUMN_Ta = "Ta";
    private static final String COLUMN_CodeNoeBastehBandy = "CodeNoeBastehBandy";
    private static final String COLUMN_BeEza = "BeEza";
    private static final String COLUMN_CodeNoeBastehBandyBeEza = "CodeNoeBastehBandyBeEza";
    private static final String COLUMN_DarsadTakhfif = "DarsadTakhfif";
    private static final String COLUMN_GheymatForosh = "GheymatForosh";
    private static final String COLUMN_MinTedadAghlam = "MinTedadAghlam";
    private static final String COLUMN_MinRial = "MinRial";
    private static final String COLUMN_ccGorohMohasebeh = "ccGorohMohasebeh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccTakhfifHajmiSatr() {
        return COLUMN_ccTakhfifHajmiSatr;
    }
    public static String COLUMN_ccTakhfifHajmi() {
        return COLUMN_ccTakhfifHajmi;
    }
    public static String COLUMN_NameNoeField() {
        return COLUMN_NameNoeField;
    }
    public static String COLUMN_ccNoeField() {
        return COLUMN_ccNoeField;
    }
    public static String COLUMN_Az() {
        return COLUMN_Az;
    }
    public static String COLUMN_Ta() {
        return COLUMN_Ta;
    }
    public static String COLUMN_CodeNoeBastehBandy() {
        return COLUMN_CodeNoeBastehBandy;
    }
    public static String COLUMN_BeEza() {
        return COLUMN_BeEza;
    }
    public static String COLUMN_CodeNoeBastehBandyBeEza() {
        return COLUMN_CodeNoeBastehBandyBeEza;
    }
    public static String COLUMN_DarsadTakhfif() {
        return COLUMN_DarsadTakhfif;
    }
    public static String COLUMN_GheymatForosh() {
        return COLUMN_GheymatForosh;
    }
    public static String COLUMN_MinTedadAghlam() {
        return COLUMN_MinTedadAghlam;
    }
    public static String COLUMN_MinRial() {
        return COLUMN_MinRial;
    }
    public static String COLUMN_ccGorohMohasebeh() {
        return COLUMN_ccGorohMohasebeh;
    }


    private int ccTakhfifHajmi;
    public void setCcTakhfifHajmi(int ccTakhfifHajmi){
        this.ccTakhfifHajmi = ccTakhfifHajmi;
    }
    public int getCcTakhfifHajmi(){
        return this.ccTakhfifHajmi;
    }


    private int ccTakhfifHajmiSatr;
    public void setCcTakhfifHajmiSatr(int ccTakhfifHajmiSatr){
        this.ccTakhfifHajmiSatr = ccTakhfifHajmiSatr;
    }
    public int getCcTakhfifHajmiSatr(){
        return this.ccTakhfifHajmiSatr;
    }


    private int NameNoeField;
    public void setNameNoeField(int NameNoeField){
        this.NameNoeField = NameNoeField;
    }
    public int getNameNoeField(){
        return this.NameNoeField;
    }


    private String txtNameNoeField;
    public void setTxtNameNoeField(String txtNameNoeField){
        this.txtNameNoeField = txtNameNoeField;
    }
    public String getTxtNameNoeField(){
        return this.txtNameNoeField;
    }


    private int ccNoeField;
    public void setCcNoeField(int ccNoeField){
        this.ccNoeField = ccNoeField;
    }
    public int getCcNoeField(){
        return this.ccNoeField;
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


    private String NameKala;
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }


    private String ccBrand;
    public void setCcBrand(String ccBrand){
        this.ccBrand = ccBrand;
    }
    public String getCcBrand(){
        return this.ccBrand;
    }


    private String NameBrand;
    public void setNameBrand(String NameBrand){
        this.NameBrand = NameBrand;
    }
    public String getNameBrand(){
        return this.NameBrand;
    }


    private String ccGoroh;
    public void setCcGoroh(String ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public String getCcGoroh(){
        return this.ccGoroh;
    }


    private String NameGoroh;
    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }
    public String getNameGoroh(){
        return this.NameGoroh;
    }


    private String ccTaminKonandeh;
    public void setCcTaminKonandeh(String ccTaminKonandeh){
        this.ccTaminKonandeh = ccTaminKonandeh;
    }
    public String getCcTaminKonandeh(){
        return this.ccTaminKonandeh;
    }


    private String NameTaminKonandeh;
    public void setNameTaminKonandeh(String NameTaminKonandeh){
        this.NameTaminKonandeh = NameTaminKonandeh;
    }
    public String getNameTaminKonandeh(){
        return this.NameTaminKonandeh;
    }


    private double Az;
    public void setAz(double Az)
    {
        this.Az = Az;
    }
    public double getAz()
    {
        return this.Az;
    }


    private double Ta;
    public void setTa(double Ta)
    {
        this.Ta = Ta;
    }
    public double getTa()
    {
        return this.Ta;
    }


    private int CodeNoeBastehBandy;
    public void setCodeNoeBastehBandy(int CodeNoeBastehBandy){
        this.CodeNoeBastehBandy = CodeNoeBastehBandy;
    }
    public int getCodeNoeBastehBandy(){
        return this.CodeNoeBastehBandy;
    }


    private String txtCodeNoeBastehBandy;
    public void setTxtCodeNoeBastehBandy(String txtCodeNoeBastehBandy){
        this.txtCodeNoeBastehBandy = txtCodeNoeBastehBandy;
    }
    public String getTxtCodeNoeBastehBandy(){
        return this.txtCodeNoeBastehBandy;
    }


    private double BeEza;
    public void setBeEza(double BeEza){
        this.BeEza = BeEza;
    }
    public double getBeEza(){
        return this.BeEza;
    }


    private int CodeNoeBastehBandyBeEza;
    public void setCodeNoeBastehBandyBeEza(int CodeNoeBastehBandyBeEza){
        this.CodeNoeBastehBandyBeEza = CodeNoeBastehBandyBeEza;
    }
    public int getCodeNoeBastehBandyBeEza(){
        return this.CodeNoeBastehBandyBeEza;
    }


    private String txtCodeNoeBastehBandyBeEza;
    public void setTxtCodeNoeBastehBandyBeEza(String txtCodeNoeBastehBandyBeEza){
        this.txtCodeNoeBastehBandyBeEza = txtCodeNoeBastehBandyBeEza;
    }
    public String getTxtCodeNoeBastehBandyBeEza(){
        return this.txtCodeNoeBastehBandyBeEza;
    }


    private double DarsadTakhfif;
    public void setDarsadTakhfif(double DarsadTakhfif){
        this.DarsadTakhfif = DarsadTakhfif;
    }
    public double getDarsadTakhfif(){
        return this.DarsadTakhfif;
    }


    private double GheymatForosh;
    public double getGheymatForosh()
    {
        return GheymatForosh;
    }
    public void setGheymatForosh(double gheymatForosh)
    {
        GheymatForosh = gheymatForosh;
    }


    private int MinTedadAghlam;
    public int getMinTedadAghlam()
    {
        return MinTedadAghlam;
    }
    public void setMinTedadAghlam(int minTedadAghlam)
    {
        MinTedadAghlam = minTedadAghlam;
    }

    private double MinRial;
    public double getMinRial()
    {
        return MinRial;
    }
    public void setMinRial(double minRial)
    {
        MinRial = minRial;
    }

    private int ccGorohMohasebeh;
    public int getCcGorohMohasebeh()
    {
        return ccGorohMohasebeh;
    }
    public void setCcGorohMohasebeh(int ccGorohMohasebeh)
    {
        this.ccGorohMohasebeh = ccGorohMohasebeh;
    }

    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }





    @NonNull
    @Override
    public String toString()
    {
        return "TakhfifHajmiSatrModel{" +
                "ccTakhfifHajmi=" + ccTakhfifHajmi +
                ", ccTakhfifHajmiSatr=" + ccTakhfifHajmiSatr +
                ", NameNoeField=" + NameNoeField +
                ", txtNameNoeField='" + txtNameNoeField + '\'' +
                ", ccNoeField=" + ccNoeField +
                ", ccKala=" + ccKala +
                ", ccKalaCode=" + ccKalaCode +
                ", NameKala='" + NameKala + '\'' +
                ", ccBrand='" + ccBrand + '\'' +
                ", NameBrand='" + NameBrand + '\'' +
                ", ccGoroh='" + ccGoroh + '\'' +
                ", NameGoroh='" + NameGoroh + '\'' +
                ", ccTaminKonandeh='" + ccTaminKonandeh + '\'' +
                ", NameTaminKonandeh='" + NameTaminKonandeh + '\'' +
                ", Az=" + Az +
                ", Ta=" + Ta +
                ", CodeNoeBastehBandy=" + CodeNoeBastehBandy +
                ", txtCodeNoeBastehBandy='" + txtCodeNoeBastehBandy + '\'' +
                ", BeEza=" + BeEza +
                ", CodeNoeBastehBandyBeEza=" + CodeNoeBastehBandyBeEza +
                ", txtCodeNoeBastehBandyBeEza='" + txtCodeNoeBastehBandyBeEza + '\'' +
                ", DarsadTakhfif=" + DarsadTakhfif +
                ", GheymatForosh=" + GheymatForosh +
                ", MinTedadAghlam=" + MinTedadAghlam +
                ", MinRial=" + MinRial +
                ", Id=" + Id +
                ", ccGorohMohasebeh=" + ccGorohMohasebeh +
                '}';
    }
}
