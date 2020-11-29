package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class AnbarakAfradModel
{

    private static final String TABLE_NAME = "AnbarakAfrad";
    private static final String COLUMN_ccAfradForoshandeh = "ccAfradForoshandeh";
    private static final String COLUMN_ccAfradMamorPakhsh = "ccAfradMamorPakhsh";
    private static final String COLUMN_ccAfradRanandeh = "ccAfradRanandeh";
    private static final String COLUMN_ccAfradForoshandehJaygozin = "ccAfradForoshandehJaygozin";
    private static final String COLUMN_ccAfradMamorPakhshJaygozin = "ccAfradMamorPakhshJaygozin";
    private static final String COLUMN_ccAfradRanandehJaygozin = "ccAfradRanandehJaygozin";
    private static final String COLUMN_ccNoeForoshandeh = "ccNoeForoshandeh";
    private static final String COLUMN_ccAnbarak = "ccAnbarak";
    private static final String COLUMN_NameAnbarak = "NameAnbarak";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccAfradForoshandeh() {
        return COLUMN_ccAfradForoshandeh;
    }
    public static String COLUMN_ccAfradMamorPakhsh() {
        return COLUMN_ccAfradMamorPakhsh;
    }
    public static String COLUMN_ccAfradRanandeh() {
        return COLUMN_ccAfradRanandeh;
    }
    public static String COLUMN_ccAfradForoshandehJaygozin() {
        return COLUMN_ccAfradForoshandehJaygozin;
    }
    public static String COLUMN_ccAfradMamorPakhshJaygozin() {
        return COLUMN_ccAfradMamorPakhshJaygozin;
    }
    public static String COLUMN_ccAfradRanandehJaygozin() {
        return COLUMN_ccAfradRanandehJaygozin;
    }
    public static String COLUMN_ccNoeForoshandeh() {
        return COLUMN_ccNoeForoshandeh;
    }
    public static String COLUMN_ccAnbarak() {
        return COLUMN_ccAnbarak;
    }
    public static String COLUMN_NameAnbarak() {
        return COLUMN_NameAnbarak;
    }





    private int Radif;
    public void setRadif(int Radif){
        this.Radif = Radif;
    }
    public int getRadif(){
        return this.Radif;
    }


    private int ccAfradForoshandeh;
    public void setCcAfradForoshandeh(int ccAfradForoshandeh){
        this.ccAfradForoshandeh = ccAfradForoshandeh;
    }
    public int getCcAfradForoshandeh(){
        return this.ccAfradForoshandeh;
    }


    private int ccAfradMamorPakhsh;
    public void setCcAfradMamorPakhsh(int ccAfradMamorPakhsh){
        this.ccAfradMamorPakhsh = ccAfradMamorPakhsh;
    }
    public int getCcAfradMamorPakhsh(){
        return this.ccAfradMamorPakhsh;
    }


    private int ccAfradRanandeh;
    public void setCcAfradRanandeh(int ccAfradRanandeh){
        this.ccAfradRanandeh = ccAfradRanandeh;
    }
    public int getCcAfradRanandeh(){
        return this.ccAfradRanandeh;
    }


    private int ccAfradForoshandehJaygozin;
    public void setCcAfradForoshandehJaygozin(int ccAfradForoshandehJaygozin){
        this.ccAfradForoshandehJaygozin = ccAfradForoshandehJaygozin;
    }
    public int getCcAfradForoshandehJaygozin(){
        return this.ccAfradForoshandehJaygozin;
    }


    private int ccAfradMamorPakhshJaygozin;
    public void setCcAfradMamorPakhshJaygozin(int ccAfradMamorPakhshJaygozin){
        this.ccAfradMamorPakhshJaygozin = ccAfradMamorPakhshJaygozin;
    }
    public int getCcAfradMamorPakhshJaygozin(){
        return this.ccAfradMamorPakhshJaygozin;
    }


    private int ccAfradRanandehJaygozin;
    public void setCcAfradRanandehJaygozin(int ccAfradRanandehJaygozin){
        this.ccAfradRanandehJaygozin = ccAfradRanandehJaygozin;
    }
    public int getCcAfradRanandehJaygozin(){
        return this.ccAfradRanandehJaygozin;
    }


    private int ccNoeForoshandeh;
    public void setCcNoeForoshandeh(int ccNoeForoshandeh){
        this.ccNoeForoshandeh = ccNoeForoshandeh;
    }
    public int getCcNoeForoshandeh(){
        return this.ccNoeForoshandeh;
    }


    private int ccAnbarak;
    public void setCcAnbarak(int ccAnbarak){
        this.ccAnbarak = ccAnbarak;
    }
    public int getCcAnbarak(){
        return this.ccAnbarak;
    }


    private String NameAnbarak;
    public void setNameAnbarak(String NameAnbarak){
        this.NameAnbarak = NameAnbarak;
    }
    public String getNameAnbarak(){
        return this.NameAnbarak;
    }


    private int ccNoeAnbarak;
    public void setCcNoeAnbarak(int ccNoeAnbarak){
        this.ccNoeAnbarak = ccNoeAnbarak;
    }
    public int getCcNoeAnbarak(){
        return this.ccNoeAnbarak;
    }


    private String NameNoeAnbarak;
    public void setNameNoeAnbarak(String NameNoeAnbarak){
        this.NameNoeAnbarak = NameNoeAnbarak;
    }
    public String getNameNoeAnbarak(){
        return this.NameNoeAnbarak;
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
        return  "Radif=" + Radif +
                ", ccAfradForoshandeh=" + ccAfradForoshandeh +
                ", ccAfradMamorPakhsh=" + ccAfradMamorPakhsh +
                ", ccAfradRanandeh=" + ccAfradRanandeh +
                ", ccAfradForoshandehJaygozin=" + ccAfradForoshandehJaygozin +
                ", ccAfradMamorPakhshJaygozin=" + ccAfradMamorPakhshJaygozin +
                ", ccAfradRanandehJaygozin=" + ccAfradRanandehJaygozin +
                ", ccNoeForoshandeh=" + ccNoeForoshandeh +
                ", ccAnbarak=" + ccAnbarak +
                ", NameAnbarak='" + NameAnbarak + '\'' +
                ", ccNoeAnbarak=" + ccNoeAnbarak +
                ", NameNoeAnbarak='" + NameNoeAnbarak + '\'' +
                ", Id=" + Id;
    }



}
