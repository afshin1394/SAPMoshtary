package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class PorseshnamehModel
{

    private static final String TABLE_NAME = "Porseshnameh";
    private static final String COLUMN_ccPorseshnameh = "ccPorseshnameh";
    private static final String COLUMN_ccAmargar = "ccAmargar";
    private static final String COLUMN_TarikhPorseshnameh = "TarikhPorseshnameh";
    private static final String COLUMN_ccMahal = "ccMahal";
    private static final String COLUMN_IsMoshtary = "IsMoshtary";
    private static final String COLUMN_HasDelpazir = "HasDelpazir";
    private static final String COLUMN_NameMoshtary = "NameMoshtary";
    private static final String COLUMN_NameMaghazeh = "NameMaghazeh";
    private static final String COLUMN_ccNoeMoshtary = "ccNoeMoshtary";
    private static final String COLUMN_ccSenfMoshtary = "ccSenfMoshtary";
    private static final String COLUMN_NameMahaleh = "NameMahaleh";
    private static final String COLUMN_Address = "Address";
    private static final String COLUMN_Telephone = "Telephone";
    private static final String COLUMN_Mobile = "Mobile";
    private static final String COLUMN_Pelak = "Pelak";
    private static final String COLUMN_CodePosty = "CodePosty";
    private static final String COLUMN_ccRotbeh = "ccRotbeh";
    private static final String COLUMN_ccPorseshnamehTozihat = "ccPorseshnamehTozihat";
    private static final String COLUMN_ccNoeTablighat = "ccNoeTablighat";
    private static final String COLUMN_ExtraProp_IsOld = "ExtraProp_IsOld";
    private static final String COLUMN_Longitude_x = "Longitude_x";
    private static final String COLUMN_Latitude_y = "Latitude_y";
    private static final String COLUMN_ZamanSabt = "ZamanSabt";
    private static final String COLUMN_MasahatMaghazeh = "MasahatMaghazeh";
    private static final String COLUMN_HasAnbar = "HasAnbar";
    private static final String COLUMN_ccMoshtary = "ccMoshtary";
    private static final String COLUMN_CodeVazeiat = "CodeVazeiat";
    private static final String COLUMN_KhiabanAsli = "KhiabanAsli";
    private static final String COLUMN_KhiabanFarei1 = "KhiabanFarei1";
    private static final String COLUMN_KhiabanFarei2 = "KhiabanFarei2";
    private static final String COLUMN_KoocheAsli = "KoocheAsli";
    private static final String COLUMN_KoocheFarei1 = "KoocheFarei1";
    private static final String COLUMN_KoocheFarei2 = "KoocheFarei2";
    private static final String COLUMN_FNameMoshtary = "FNameMoshtary";
    private static final String COLUMN_LNameMoshtary = "LNameMoshtary";
    private static final String COLUMN_CodeMely = "CodeMely";
    private static final String COLUMN_ccMasir = "ccMasir";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccPorseshnameh()
    {
        return COLUMN_ccPorseshnameh;
    }
    public static String COLUMN_ccAmargar()
    {
        return COLUMN_ccAmargar;
    }
    public static String COLUMN_TarikhPorseshnameh()
    {
        return COLUMN_TarikhPorseshnameh;
    }
    public static String COLUMN_ccMahal()
    {
        return COLUMN_ccMahal;
    }
    public static String COLUMN_IsMoshtary()
    {
        return COLUMN_IsMoshtary;
    }
    public static String COLUMN_HasDelpazir()
    {
        return COLUMN_HasDelpazir;
    }
    public static String COLUMN_NameMoshtary()
    {
        return COLUMN_NameMoshtary;
    }
    public static String COLUMN_NameMaghazeh()
    {
        return COLUMN_NameMaghazeh;
    }
    public static String COLUMN_ccNoeMoshtary()
    {
        return COLUMN_ccNoeMoshtary;
    }
    public static String COLUMN_ccSenfMoshtary()
    {
        return COLUMN_ccSenfMoshtary;
    }
    public static String COLUMN_NameMahaleh()
    {
        return COLUMN_NameMahaleh;
    }
    public static String COLUMN_Address()
    {
        return COLUMN_Address;
    }
    public static String COLUMN_Telephone()
    {
        return COLUMN_Telephone;
    }
    public static String COLUMN_Mobile()
    {
        return COLUMN_Mobile;
    }
    public static String COLUMN_Pelak()
    {
        return COLUMN_Pelak;
    }
    public static String COLUMN_CodePosty()
    {
        return COLUMN_CodePosty;
    }
    public static String COLUMN_ccRotbeh()
    {
        return COLUMN_ccRotbeh;
    }
    public static String COLUMN_ccPorseshnamehTozihat()
    {
        return COLUMN_ccPorseshnamehTozihat;
    }
    public static String COLUMN_ccNoeTablighat()
    {
        return COLUMN_ccNoeTablighat;
    }
    public static String COLUMN_ExtraProp_IsOld()
    {
        return COLUMN_ExtraProp_IsOld;
    }
    public static String COLUMN_Longitude_x()
    {
        return COLUMN_Longitude_x;
    }
    public static String COLUMN_Latitude_y()
    {
        return COLUMN_Latitude_y;
    }
    public static String COLUMN_ZamanSabt()
    {
        return COLUMN_ZamanSabt;
    }
    public static String COLUMN_MasahatMaghazeh()
    {
        return COLUMN_MasahatMaghazeh;
    }
    public static String COLUMN_HasAnbar()
    {
        return COLUMN_HasAnbar;
    }
    public static String COLUMN_ccMoshtary()
    {
        return COLUMN_ccMoshtary;
    }
    public static String COLUMN_CodeVazeiat()
    {
        return COLUMN_CodeVazeiat;
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
    public static String COLUMN_FNameMoshtary()
    {
        return COLUMN_FNameMoshtary;
    }
    public static String COLUMN_LNameMoshtary()
    {
        return COLUMN_LNameMoshtary;
    }
    public static String COLUMN_CodeMely()
    {
        return COLUMN_CodeMely;
    }
    public static String COLUMN_ccMasir()
    {
        return COLUMN_ccMasir;
    }




    private int ccPorseshnameh;
    private int ccAmargar;
    private String TarikhPorseshnameh;
    private int ccMahal;
    private int IsMoshtary;
    private int HasDelpazir;
    private String NameMoshtary;
    private String NameMaghazeh;
    private int ccNoeMoshtary;
    private int ccSenfMoshtary;
    private String NameMahaleh;
    private String Address;
    private String Telephone;
    private String Mobile;
    private String Pelak;
    private String CodePosty;
    private int ccRotbeh;
    private int ccPorseshnamehTozihat;
    private int ccNoeTablighat;
    private int ExtraProp_IsOld;
    private double Longitude_x;
    private double Latitude_y;
    private String ZamanSabt;
    private int MasahatMaghazeh;
    private int HasAnbar;
    private int ccMoshtary;
    private int CodeVazeiat;
    private String KhiabanAsli;
    private String KhiabanFarei1;
    private String KhiabanFarei2;
    private String KoocheAsli;
    private String KoocheFarei1;
    private String KoocheFarei2;
    private String FNameMoshtary;
    private String LNameMoshtary;
    private String CodeMely;
    private int ccMasir;


    public int getCcPorseshnameh()
    {
        return ccPorseshnameh;
    }

    public void setCcPorseshnameh(int ccPorseshnameh)
    {
        this.ccPorseshnameh = ccPorseshnameh;
    }

    public int getCcAmargar()
    {
        return ccAmargar;
    }

    public void setCcAmargar(int ccAmargar)
    {
        this.ccAmargar = ccAmargar;
    }

    public String getTarikhPorseshnameh()
    {
        return TarikhPorseshnameh;
    }

    public void setTarikhPorseshnameh(String tarikhPorseshnameh)
    {
        TarikhPorseshnameh = tarikhPorseshnameh;
    }

    public int getCcMahal()
    {
        return ccMahal;
    }

    public void setCcMahal(int ccMahal)
    {
        this.ccMahal = ccMahal;
    }

    public int getIsMoshtary()
    {
        return IsMoshtary;
    }

    public void setIsMoshtary(int isMoshtary)
    {
        IsMoshtary = isMoshtary;
    }

    public int getHasDelpazir()
    {
        return HasDelpazir;
    }

    public void setHasDelpazir(int hasDelpazir)
    {
        HasDelpazir = hasDelpazir;
    }

    public String getNameMoshtary()
    {
        return NameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary)
    {
        NameMoshtary = nameMoshtary;
    }

    public String getNameMaghazeh()
    {
        return NameMaghazeh;
    }

    public void setNameMaghazeh(String nameMaghazeh)
    {
        NameMaghazeh = nameMaghazeh;
    }

    public int getCcNoeMoshtary()
    {
        return ccNoeMoshtary;
    }

    public void setCcNoeMoshtary(int ccNoeMoshtary)
    {
        this.ccNoeMoshtary = ccNoeMoshtary;
    }

    public int getCcSenfMoshtary()
    {
        return ccSenfMoshtary;
    }

    public void setCcSenfMoshtary(int ccSenfMoshtary)
    {
        this.ccSenfMoshtary = ccSenfMoshtary;
    }

    public String getNameMahaleh()
    {
        return NameMahaleh;
    }

    public void setNameMahaleh(String nameMahaleh)
    {
        NameMahaleh = nameMahaleh;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setAddress(String address)
    {
        Address = address;
    }

    public String getTelephone()
    {
        return Telephone;
    }

    public void setTelephone(String telephone)
    {
        Telephone = telephone;
    }

    public String getMobile()
    {
        return Mobile;
    }

    public void setMobile(String mobile)
    {
        Mobile = mobile;
    }

    public String getPelak()
    {
        return Pelak;
    }

    public void setPelak(String pelak)
    {
        Pelak = pelak;
    }

    public String getCodePosty()
    {
        return CodePosty;
    }

    public void setCodePosty(String codePosty)
    {
        CodePosty = codePosty;
    }

    public int getCcRotbeh()
    {
        return ccRotbeh;
    }

    public void setCcRotbeh(int ccRotbeh)
    {
        this.ccRotbeh = ccRotbeh;
    }

    public int getCcPorseshnamehTozihat()
    {
        return ccPorseshnamehTozihat;
    }

    public void setCcPorseshnamehTozihat(int ccPorseshnamehTozihat)
    {
        this.ccPorseshnamehTozihat = ccPorseshnamehTozihat;
    }

    public int getCcNoeTablighat()
    {
        return ccNoeTablighat;
    }

    public void setCcNoeTablighat(int ccNoeTablighat)
    {
        this.ccNoeTablighat = ccNoeTablighat;
    }

    public int getExtraProp_IsOld()
    {
        return ExtraProp_IsOld;
    }

    public void setExtraProp_IsOld(int extraProp_IsOld)
    {
        ExtraProp_IsOld = extraProp_IsOld;
    }

    public double getLongitude_x()
    {
        return Longitude_x;
    }

    public void setLongitude_x(double longitude_x)
    {
        Longitude_x = longitude_x;
    }

    public double getLatitude_y()
    {
        return Latitude_y;
    }

    public void setLatitude_y(double latitude_y)
    {
        Latitude_y = latitude_y;
    }

    public String getZamanSabt()
    {
        return ZamanSabt;
    }

    public void setZamanSabt(String zamanSabt)
    {
        ZamanSabt = zamanSabt;
    }

    public int getMasahatMaghazeh()
    {
        return MasahatMaghazeh;
    }

    public void setMasahatMaghazeh(int masahatMaghazeh)
    {
        MasahatMaghazeh = masahatMaghazeh;
    }

    public int getHasAnbar()
    {
        return HasAnbar;
    }

    public void setHasAnbar(int hasAnbar)
    {
        HasAnbar = hasAnbar;
    }

    public int getCcMoshtary()
    {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary)
    {
        this.ccMoshtary = ccMoshtary;
    }

    public int getCodeVazeiat()
    {
        return CodeVazeiat;
    }

    public void setCodeVazeiat(int codeVazeiat)
    {
        CodeVazeiat = codeVazeiat;
    }

    public String getKhiabanAsli()
    {
        return KhiabanAsli;
    }

    public void setKhiabanAsli(String khiabanAsli)
    {
        KhiabanAsli = khiabanAsli;
    }

    public String getKhiabanFarei1()
    {
        return KhiabanFarei1;
    }

    public void setKhiabanFarei1(String khiabanFarei1)
    {
        KhiabanFarei1 = khiabanFarei1;
    }

    public String getKhiabanFarei2()
    {
        return KhiabanFarei2;
    }

    public void setKhiabanFarei2(String khiabanFarei2)
    {
        KhiabanFarei2 = khiabanFarei2;
    }

    public String getKoocheAsli()
    {
        return KoocheAsli;
    }

    public void setKoocheAsli(String koocheAsli)
    {
        KoocheAsli = koocheAsli;
    }

    public String getKoocheFarei1()
    {
        return KoocheFarei1;
    }

    public void setKoocheFarei1(String koocheFarei1)
    {
        KoocheFarei1 = koocheFarei1;
    }

    public String getKoocheFarei2()
    {
        return KoocheFarei2;
    }

    public void setKoocheFarei2(String koocheFarei2)
    {
        KoocheFarei2 = koocheFarei2;
    }

    public String getFNameMoshtary()
    {
        return FNameMoshtary;
    }

    public void setFNameMoshtary(String FNameMoshtary)
    {
        this.FNameMoshtary = FNameMoshtary;
    }

    public String getLNameMoshtary()
    {
        return LNameMoshtary;
    }

    public void setLNameMoshtary(String LNameMoshtary)
    {
        this.LNameMoshtary = LNameMoshtary;
    }

    public String getCodeMely()
    {
        return CodeMely;
    }

    public void setCodeMely(String codeMely)
    {
        CodeMely = codeMely;
    }

    public int getCcMasir()
    {
        return ccMasir;
    }

    public void setCcMasir(int ccMasir)
    {
        this.ccMasir = ccMasir;
    }

    public JSONObject toJsonObject(String version)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_ccPorseshnameh(), ccPorseshnameh);
            jsonObject.put(COLUMN_ccAmargar(), ccAmargar);
            jsonObject.put(COLUMN_TarikhPorseshnameh(), TarikhPorseshnameh);
            jsonObject.put(COLUMN_ccMahal(), ccMahal);
            jsonObject.put(COLUMN_IsMoshtary(), IsMoshtary);
            jsonObject.put(COLUMN_HasDelpazir(), HasDelpazir);
            jsonObject.put(COLUMN_NameMoshtary(), NameMoshtary);
            jsonObject.put(COLUMN_CodeMely(), CodeMely);
            jsonObject.put(COLUMN_NameMaghazeh(), NameMaghazeh);
            jsonObject.put(COLUMN_ccNoeMoshtary(), ccNoeMoshtary);
            jsonObject.put(COLUMN_ccSenfMoshtary(), ccSenfMoshtary);
            jsonObject.put(COLUMN_NameMahaleh(), NameMahaleh);
            jsonObject.put(COLUMN_Address(), Address);
            jsonObject.put(COLUMN_Telephone(), Telephone);
            jsonObject.put(COLUMN_Mobile(), Mobile);
            jsonObject.put(COLUMN_Pelak(), Pelak);
            jsonObject.put(COLUMN_CodePosty(), CodePosty);
            jsonObject.put(COLUMN_ccRotbeh(), ccRotbeh);
            jsonObject.put(COLUMN_ccPorseshnamehTozihat(), ccPorseshnamehTozihat);
            jsonObject.put(COLUMN_Longitude_x(), Longitude_x);
            jsonObject.put(COLUMN_Latitude_y(), Latitude_y);
            jsonObject.put(COLUMN_ZamanSabt(), ZamanSabt);
            jsonObject.put(COLUMN_MasahatMaghazeh(), MasahatMaghazeh);
            jsonObject.put(COLUMN_HasAnbar(), HasAnbar);
            jsonObject.put(COLUMN_ccMoshtary(), ccMoshtary);
            jsonObject.put(COLUMN_CodeVazeiat(), CodeVazeiat);
            jsonObject.put(COLUMN_KhiabanAsli(), KhiabanAsli);
            jsonObject.put(COLUMN_KhiabanFarei1(), KhiabanFarei1);
            jsonObject.put(COLUMN_KhiabanFarei2(), KhiabanFarei2);
            jsonObject.put(COLUMN_KoocheAsli(), KoocheAsli);
            jsonObject.put(COLUMN_KoocheFarei1(), KoocheFarei1);
            jsonObject.put(COLUMN_KoocheFarei2(), KoocheFarei2);
            jsonObject.put(COLUMN_FNameMoshtary(), FNameMoshtary);
            jsonObject.put(COLUMN_LNameMoshtary(), LNameMoshtary);
            jsonObject.put(COLUMN_ccMasir(), ccMasir);
            jsonObject.put("PPC_VersionNumber", version);

            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    @NonNull
    @Override
    public String toString()
    {
        return "PorseshnamehModel{" +
                "ccPorseshnameh=" + ccPorseshnameh +
                ", ccAmargar=" + ccAmargar +
                ", TarikhPorseshnameh='" + TarikhPorseshnameh + '\'' +
                ", ccMahal=" + ccMahal +
                ", IsMoshtary=" + IsMoshtary +
                ", HasDelpazir=" + HasDelpazir +
                ", NameMoshtary='" + NameMoshtary + '\'' +
                ", NameMaghazeh='" + NameMaghazeh + '\'' +
                ", ccNoeMoshtary=" + ccNoeMoshtary +
                ", ccSenfMoshtary=" + ccSenfMoshtary +
                ", NameMahaleh='" + NameMahaleh + '\'' +
                ", Address='" + Address + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Pelak='" + Pelak + '\'' +
                ", CodePosty='" + CodePosty + '\'' +
                ", ccRotbeh=" + ccRotbeh +
                ", ccPorseshnamehTozihat=" + ccPorseshnamehTozihat +
                ", ccNoeTablighat=" + ccNoeTablighat +
                ", ExtraProp_IsOld=" + ExtraProp_IsOld +
                ", Longitude_x=" + Longitude_x +
                ", Latitude_y=" + Latitude_y +
                ", ZamanSabt='" + ZamanSabt + '\'' +
                ", MasahatMaghazeh=" + MasahatMaghazeh +
                ", HasAnbar=" + HasAnbar +
                ", ccMoshtary=" + ccMoshtary +
                ", CodeVazeiat=" + CodeVazeiat +
                ", KhiabanAsli='" + KhiabanAsli + '\'' +
                ", KhiabanFarei1='" + KhiabanFarei1 + '\'' +
                ", KhiabanFarei2='" + KhiabanFarei2 + '\'' +
                ", KoocheAsli='" + KoocheAsli + '\'' +
                ", KoocheFarei1='" + KoocheFarei1 + '\'' +
                ", KoocheFarei2='" + KoocheFarei2 + '\'' +
                ", FNameMoshtary='" + FNameMoshtary + '\'' +
                ", LNameMoshtary='" + LNameMoshtary + '\'' +
                ", CodeMely='" + CodeMely + '\'' +
                ", ccMasir=" + ccMasir +
                '}';
    }
}
