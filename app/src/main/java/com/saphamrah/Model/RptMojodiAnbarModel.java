package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class RptMojodiAnbarModel
{

    private static final String TABLE_NAME = "Rpt_MojodiAnbar";
    private static final String COLUMN_Radif = "Radif";
    private static final String COLUMN_ccKalaCode = "ccKalaCode";
    private static final String COLUMN_CodeKala = "CodeKala";
    private static final String COLUMN_NameKala = "NameKala";
    private static final String COLUMN_MandehMojodi_Karton = "MandehMojodi_Karton";
    private static final String COLUMN_MandehMojodi_Basteh = "MandehMojodi_Basteh";
    private static final String COLUMN_MandehMojodi_Adad = "MandehMojodi_Adad";
    private static final String COLUMN_IsAdamForosh = "IsAdamForosh";
    private static final String COLUMN_ccSazmanForosh = "ccSazmanForosh";
    private static final String COLUMN_NameSazmanForosh = "NameSazmanForosh";
    private static final String COLUMN_ccDarkhastFaktorNoeForosh = "ccDarkhastFaktorNoeForosh";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif() {
        return COLUMN_Radif;
    }
    public static String COLUMN_ccKalaCode() {
        return COLUMN_ccKalaCode;
    }
    public static String COLUMN_CodeKala() {
        return COLUMN_CodeKala;
    }
    public static String COLUMN_NameKala() {
        return COLUMN_NameKala;
    }
    public static String COLUMN_MandehMojodi_Karton() {
        return COLUMN_MandehMojodi_Karton;
    }
    public static String COLUMN_MandehMojodi_Basteh() {
        return COLUMN_MandehMojodi_Basteh;
    }
    public static String COLUMN_MandehMojodi_Adad() {
        return COLUMN_MandehMojodi_Adad;
    }
    public static String COLUMN_IsAdamForosh() {
        return COLUMN_IsAdamForosh;
    }
    public static String COLUMN_ccSazmanForosh() {
        return COLUMN_ccSazmanForosh;
    }
    public static String COLUMN_NameSazmanForosh() {
        return COLUMN_NameSazmanForosh;
    }
    public static String COLUMN_ccDarkhastFaktorNoeForosh() {
        return COLUMN_ccDarkhastFaktorNoeForosh;
    }



    private int Id;
    private int Radif;
    private int ccKalaCode;
    private String CodeKala;
    private String NameKala;
    private int MandehMojodi_Karton;
    private int MandehMojodi_Basteh;
    private int MandehMojodi_Adad;
    private int IsAdamForosh;
    private int ccSazmanForosh;
    private String NameSazmanForosh;
    private int ccDarkhastFaktorNoeForosh;


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

    public void setCodeKala(String CodeKala){
        this.CodeKala = CodeKala;
    }
    public String getCodeKala(){
        return this.CodeKala;
    }

    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public String getNameKala(){
        return this.NameKala;
    }

    public void setMandehMojodi_Karton(int MandehMojodi_Karton){
        this.MandehMojodi_Karton = MandehMojodi_Karton;
    }
    public int getMandehMojodi_Karton(){
        return this.MandehMojodi_Karton;
    }

    public void setMandehMojodi_Basteh(int MandehMojodi_Basteh){
        this.MandehMojodi_Basteh = MandehMojodi_Basteh;
    }
    public int getMandehMojodi_Basteh(){
        return this.MandehMojodi_Basteh;
    }

    public void setMandehMojodi_Adad(int MandehMojodi_Adad){
        this.MandehMojodi_Adad = MandehMojodi_Adad;
    }
    public int getMandehMojodi_Adad(){
        return this.MandehMojodi_Adad;
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
    public void setIsAdamForosh(int isAdamForosh)
    {
        IsAdamForosh = isAdamForosh;
    }

    public void setCcSazmanForosh(int ccSazmanForosh){
        this.ccSazmanForosh = ccSazmanForosh;
    }
    public int getCcSazmanForosh(){
        return this.ccSazmanForosh;
    }

    public void setNameSazmanForosh(String NameSazmanForosh){
        this.NameSazmanForosh = NameSazmanForosh;
    }
    public String getNameSazmanForosh(){
        return this.NameSazmanForosh;
    }

    public int getCcDarkhastFaktorNoeForosh() {
        return ccDarkhastFaktorNoeForosh;
    }

    public void setCcDarkhastFaktorNoeForosh(int ccDarkhastFaktorNoeForosh) {
        this.ccDarkhastFaktorNoeForosh = ccDarkhastFaktorNoeForosh;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "RptMojodiAnbarModel{" +
                "Id=" + Id +
                ", Radif=" + Radif +
                ", ccKalaCode=" + ccKalaCode +
                ", CodeKala='" + CodeKala + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", MandehMojodi_Karton=" + MandehMojodi_Karton +
                ", MandehMojodi_Basteh=" + MandehMojodi_Basteh +
                ", MandehMojodi_Adad=" + MandehMojodi_Adad +
                ", IsAdamForosh=" + IsAdamForosh +
                ", CcSazmanForosh=" + ccSazmanForosh +
                ", NameSazmanForosh=" + NameSazmanForosh +
                ", ccDarkhastFaktorNoeForosh=" + ccDarkhastFaktorNoeForosh +
                '}';
    }


}
