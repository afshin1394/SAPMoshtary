package com.saphamrah.UIModel;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class KalaMojodiZaribModel implements Comparable<KalaMojodiZaribModel>
{

    private int ccKalaCode;
    private int ccKalaMojodi;
    private String CodeKala;
    private String BarCode;
    private String NameBrand;
    private String NameKala;
    private int ccTaminKonandeh;
    private int TedadDarKarton;
    private int TedadDarBasteh;
    private int TedadMojodyGhabelForosh;
    private int Adad;
    private int Tedad;
    private int GheymatForosh;
    private int MablaghMasrafKonandeh;
    private int ZaribForosh;
    private String TarikhTolid;
    private String TarikhEngheza;
    private int Max_MojodyByShomarehBach;
    private String ShomarehBach;
    private float VaznKhales;
    private float VaznKarton;
    private String NameVahedVazn;
    private String NameVahedSize;
    private float Tol;
    private float Arz;
    private float Ertefa;
    private int mashmolMaliatAvarez;
    private int olaviat;
    private boolean kalaAsasi;
    private int tedadMojodiGiri;
    private int tedadPishnahadi;
    private boolean kalaYekSetareh;
    private boolean kalaDoSetareh;
    private float GheymatMasrafKonandehAsli;
    private float GheymatForoshAsli;
    private Bitmap imageDb;



    public int getCcKalaCode() {
        return ccKalaCode;
    }
    public void setCcKalaCode(int ccKalaCode) {
        this.ccKalaCode = ccKalaCode;
    }
    public int getCcKalaMojodi() {
        return ccKalaMojodi;
    }
    public void setCcKalaMojodi(int ccKalaMojodi) {
        this.ccKalaMojodi = ccKalaMojodi;
    }
    public String getCodeKala() {
        return CodeKala;
    }
    public void setCodeKala(String codeKala) {
        CodeKala = codeKala;
    }
    public String getBarCode() {
        return BarCode;
    }
    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
    public String getNameBrand() {
        return NameBrand;
    }
    public void setNameBrand(String nameBrand) {
        NameBrand = nameBrand;
    }
    public String getNameKala() {
        return NameKala;
    }
    public void setNameKala(String nameKala) {
        NameKala = nameKala;
    }
    public int getCcTaminKonandeh() {
        return ccTaminKonandeh;
    }
    public void setCcTaminKonandeh(int ccTaminKonandeh) {
        this.ccTaminKonandeh = ccTaminKonandeh;
    }
    public int getTedadDarKarton() {
        return TedadDarKarton;
    }
    public void setTedadDarKarton(int tedadDarKarton) {
        TedadDarKarton = tedadDarKarton;
    }
    public int getTedadDarBasteh() {
        return TedadDarBasteh;
    }
    public void setTedadDarBasteh(int tedadDarBasteh) {
        TedadDarBasteh = tedadDarBasteh;
    }
    public int getTedadMojodyGhabelForosh() {
        return TedadMojodyGhabelForosh;
    }
    public void setTedadMojodyGhabelForosh(int tedadMojodyGhabelForosh) {
        TedadMojodyGhabelForosh = tedadMojodyGhabelForosh;
    }
    public int getAdad() {
        return Adad;
    }
    public void setAdad(int adad) {
        Adad = adad;
    }
    public int getTedad() {
        return Tedad;
    }
    public void setTedad(int tedad) {
        Tedad = tedad;
    }
    public int getGheymatForosh() {
        return GheymatForosh;
    }
    public void setGheymatForosh(int gheymatForosh) {
        GheymatForosh = gheymatForosh;
    }
    public int getMablaghMasrafKonandeh() {
        return MablaghMasrafKonandeh;
    }
    public void setMablaghMasrafKonandeh(int mablaghMasrafKonandeh) {
        MablaghMasrafKonandeh = mablaghMasrafKonandeh;
    }
    public int getZaribForosh() {
        return ZaribForosh;
    }
    public void setZaribForosh(int zaribForosh) {
        ZaribForosh = zaribForosh;
    }
    public String getTarikhTolid() {
        return TarikhTolid;
    }
    public void setTarikhTolid(String tarikhTolid) {
        TarikhTolid = tarikhTolid;
    }
    public String getTarikhEngheza() {
        return TarikhEngheza;
    }
    public void setTarikhEngheza(String tarikhEngheza) {
        TarikhEngheza = tarikhEngheza;
    }

    public int getMax_MojodyByShomarehBach()
    {
        return Max_MojodyByShomarehBach;
    }
    public void setMax_MojodyByShomarehBach(int max_MojodyByShomarehBach)
    {
        Max_MojodyByShomarehBach = max_MojodyByShomarehBach;
    }

    public String getShomarehBach() {
        return ShomarehBach;
    }
    public void setShomarehBach(String shomarehBach) {
        ShomarehBach = shomarehBach;
    }
    public float getVaznKhales() {
        return VaznKhales;
    }
    public void setVaznKhales(float vaznKhales) {
        VaznKhales = vaznKhales;
    }
    public float getVaznKarton() {
        return VaznKarton;
    }
    public void setVaznKarton(float vaznKarton) {
        VaznKarton = vaznKarton;
    }
    public String getNameVahedVazn() {
        return NameVahedVazn;
    }
    public void setNameVahedVazn(String nameVahedVazn) {
        NameVahedVazn = nameVahedVazn;
    }
    public String getNameVahedSize() {
        return NameVahedSize;
    }
    public void setNameVahedSize(String nameVahedSize) {
        NameVahedSize = nameVahedSize;
    }
    public void setTol(float Tol){
        this.Tol = Tol;
    }
    public float getTol(){
        return this.Tol;
    }
    public void setArz(float Arz){
        this.Arz = Arz;
    }
    public float getArz(){
        return this.Arz;
    }
    public void setErtefa(float Ertefa){
        this.Ertefa = Ertefa;
    }
    public float getErtefa(){
        return this.Ertefa;
    }
    public int getMashmolMaliatAvarez() {
        return mashmolMaliatAvarez;
    }
    public void setMashmolMaliatAvarez(int mashmolMaliatAvarez) {
        this.mashmolMaliatAvarez = mashmolMaliatAvarez;
    }

    public int getOlaviat()
    {
        return olaviat;
    }
    public void setOlaviat(int olaviat)
    {
        this.olaviat = olaviat;
    }

    public boolean getKalaAsasi()
    {
        return kalaAsasi;
    }
    public void setKalaAsasi(boolean kalaAsasi)
    {
        this.kalaAsasi = kalaAsasi;
    }
    public int getTedadMojodiGiri()
    {
        return tedadMojodiGiri;
    }
    public void setTedadMojodiGiri(int tedadMojodiGiri)
    {
        this.tedadMojodiGiri = tedadMojodiGiri;
    }
    public int getTedadPishnahadi()
    {
        return tedadPishnahadi;
    }
    public void setTedadPishnahadi(int tedadPishnahadi)
    {
        this.tedadPishnahadi = tedadPishnahadi;
    }
    public boolean isKalaYekSetareh()
    {
        return kalaYekSetareh;
    }
    public void setKalaYekSetareh(boolean kalaYekSetareh)
    {
        this.kalaYekSetareh = kalaYekSetareh;
    }
    public boolean isKalaDoSetareh()
    {
        return kalaDoSetareh;
    }
    public void setKalaDoSetareh(boolean kalaDoSetareh)
    {
        this.kalaDoSetareh = kalaDoSetareh;
    }


    public float getGheymatMasrafKonandehAsli() {
        return GheymatMasrafKonandehAsli;
    }

    public void setGheymatMasrafKonandehAsli(float gheymatMasrafKonandehAsli) {
        GheymatMasrafKonandehAsli = gheymatMasrafKonandehAsli;
    }

    public float getGheymatForoshAsli() {
        return GheymatForoshAsli;
    }

    public void setGheymatForoshAsli(float gheymatForoshAsli) {
        GheymatForoshAsli = gheymatForoshAsli;
    }

    public Bitmap getImageDb() {
        return imageDb;
    }

    public void setImageDb(Bitmap imageDb) {
        this.imageDb = imageDb;
    }

    @Override
    public int compareTo(KalaMojodiZaribModel o)
    {
        if (this.getTedadPishnahadi() > o.getTedadPishnahadi())
        {
            return -1;
        }
        else if (this.getTedadPishnahadi() == o.getTedadPishnahadi())
        {
            if (this.getKalaAsasi() && !o.getKalaAsasi())
            {
                return -1;
            }
            else if (this.getKalaAsasi() && o.getKalaAsasi())
            {
                if (this.getOlaviat() > o.getOlaviat())
                {
                    return -1;
                }
                else if (this.getOlaviat() == o.getOlaviat())
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
            else if (!this.getKalaAsasi() && o.getKalaAsasi())
            {
                return 1;
            }
        }
        else if (this.getTedadPishnahadi() < o.getTedadPishnahadi())
        {
            return 1;
        }
        return 0;
    }

    /*@Override
    public int compare(KalaMojodiZaribModel o1, KalaMojodiZaribModel o2)
    {
        if (o1.getTedadPishnahadi() > o2.getTedadPishnahadi())
        {
            return 1;
        }
        else if (o1.getTedadPishnahadi() == o2.getTedadPishnahadi())
        {
            if (o1.getKalaAsasi() && !o2.getKalaAsasi())
            {
                return 1;
            }
            else if (o1.getKalaAsasi() && o2.getKalaAsasi())
            {
                if (o1.getOlaviat() > o2.getOlaviat())
                {
                    return 1;
                }
                else if (o1.getOlaviat() == o2.getOlaviat())
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
            else if (!o1.getKalaAsasi() && o2.getKalaAsasi())
            {
                return -1;
            }
        }
        else if (o1.getTedadPishnahadi() < o2.getTedadPishnahadi())
        {
            return -1;
        }
        return 0;
    }*/


    @NonNull
    @Override
    public String toString()
    {
        return "KalaMojodiZaribModel{" +
                "ccKalaCode=" + ccKalaCode +
                ", ccKalaMojodi=" + ccKalaMojodi +
                ", CodeKala='" + CodeKala + '\'' +
                ", BarCode='" + BarCode + '\'' +
                ", NameBrand='" + NameBrand + '\'' +
                ", NameKala='" + NameKala + '\'' +
                ", ccTaminKonandeh=" + ccTaminKonandeh +
                ", TedadDarKarton=" + TedadDarKarton +
                ", TedadDarBasteh=" + TedadDarBasteh +
                ", TedadMojodyGhabelForosh=" + TedadMojodyGhabelForosh +
                ", Adad=" + Adad +
                ", Tedad=" + Tedad +
                ", GheymatForosh=" + GheymatForosh +
                ", MablaghMasrafKonandeh=" + MablaghMasrafKonandeh +
                ", ZaribForosh=" + ZaribForosh +
                ", TarikhTolid='" + TarikhTolid + '\'' +
                ", TarikhEngheza='" + TarikhEngheza + '\'' +
                ", Max_MojodyByShomarehBach=" + Max_MojodyByShomarehBach +
                ", ShomarehBach='" + ShomarehBach + '\'' +
                ", VaznKhales=" + VaznKhales +
                ", VaznKarton=" + VaznKarton +
                ", NameVahedVazn='" + NameVahedVazn + '\'' +
                ", NameVahedSize='" + NameVahedSize + '\'' +
                ", Tol=" + Tol +
                ", Arz=" + Arz +
                ", Ertefa=" + Ertefa +
                ", mashmolMaliatAvarez=" + mashmolMaliatAvarez +
                ", olaviat=" + olaviat +
                ", kalaAsasi=" + kalaAsasi +
                ", tedadMojodiGiri=" + tedadMojodiGiri +
                ", tedadPishnahadi=" + tedadPishnahadi +
                ", kalaYekSetareh=" + kalaYekSetareh +
                ", kalaDoSetareh=" + kalaDoSetareh +
                ", GheymatMasrafKonandehAsli=" + GheymatMasrafKonandehAsli +
                ", GheymatForoshAsli=" + GheymatForoshAsli +
                '}';
    }

}
