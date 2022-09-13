package com.saphamrah.Model;

public class TafkikKalaMovazeDataModel {

    private int tedad;
    private String NameKala;
    private int ccKalaCode;
    private String CodeKala;
    private float VaznKhales;
    private float VaznNaKhales;
    private double Hajm;
    private double Tol;
    private double Arz;
    private double Ertefa;
    private int TedadDarKarton;
    private int TedadDarBasteh;
    private int Adad;



    public void setTedadDarKarton(int TedadDarKarton){
        this.TedadDarKarton = TedadDarKarton;
    }
    public void setTedadDarBasteh(int TedadDarBasteh){
        this.TedadDarBasteh = TedadDarBasteh;
    }
    public void setAdad(int Adad){
        this.Adad = Adad;
    }
    public void setTol(double Tol){
        this.Tol = Tol;
    }
    public void setArz(double Arz){
        this.Arz = Arz;
    }
    public void setErtefa(double Ertefa){
        this.Ertefa = Ertefa;
    }
    public void setNameKala(String NameKala){
        this.NameKala = NameKala;
    }
    public void setCcKalaCode(int ccKalaCode){
        this.ccKalaCode = ccKalaCode;
    }
    public void setCodeKala(String CodeKala){
        this.CodeKala = CodeKala;
    }
    public void setTedad(int tedad) {
        this.tedad = tedad;
    }
    public void setVaznKhales(float VaznNaKhales){
        this.VaznKhales = VaznNaKhales;
    }

    public int getTedadDarKarton(){
        return this.TedadDarKarton;
    }
    public int getTedadDarBasteh(){
        return this.TedadDarBasteh;
    }
    public int getAdad(){
        return this.Adad;
    }
    public double getTol(){
        return this.Tol;
    }
    public double getArz(){
        return this.Arz;
    }
    public double getErtefa(){
        return this.Ertefa;
    }
    public String getNameKala(){
        return this.NameKala;
    }
    public int getCcKalaCode(){
        return this.ccKalaCode;
    }
    public String getCodeKala(){
        return this.CodeKala;
    }
    public float getVaznKhales(){
        return this.VaznKhales;
    }
    public int getTedad3() {
        return tedad;
    }
    public double getHajm() {
        return Hajm;
    }

    public void setHajm(double hajm) {
        Hajm = hajm;
    }

    public float getVaznNaKhales() {
        return VaznNaKhales;
    }

    public void setVaznNaKhales(float vaznNaKhales) {
        VaznNaKhales = vaznNaKhales;
    }

    public double getHajmByDimention(){
        return Tol  *  Arz  *  Ertefa ;
    }
}
