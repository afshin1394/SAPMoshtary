package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class TakhfifSenfiModel
{

    private static final String TABLE_NAME = "TakhfifSenfi";
    public static final String COLUMN_Radif ="Radif";
    private static final String COLUMN_ccTakhfifSenfi = "ccTakhfifSenfi";
    private static final String COLUMN_CodeNoe = "CodeNoe";
    private static final String COLUMN_SharhTakhfif = "SharhTakhfif";
    private static final String COLUMN_NoeTedadRial = "NoeTedadRial";
    private static final String COLUMN_NameNoeFieldMoshtary = "NameNoeFieldMoshtary";
    private static final String COLUMN_ccNoeFieldMoshtary = "ccNoeFieldMoshtary";
    private static final String COLUMN_Darajeh = "Darajeh";
    private static final String COLUMN_ccMarkazSazmanForosh = "ccMarkazSazmanForosh";
    private static final String COLUMN_ccNoeSenf = "ccNoeSenf";
    private static final String COLUMN_NoeVosol = "NoeVosol";
    private static final String COLUMN_NameNoeSenf = "NameNoeSenf";
    private static final String COLUMN_NoeGheymat="NoeGheymat";
    private static final String COLUMN_ccMoshtaryGharardad="ccMoshtaryGharardad";
    private static final String COLUMN_ccMantagheh = "ccMantagheh";
    private static final String COLUMN_IsPelekani = "IsPelekani";
    private static final String COLUMN_Olaviat = "Olaviat";
    private static final String COLUMN_ccGorohTakidi = "ccGorohTakidi";
    private static final String COLUMN_txtNoeVosol = "txtNoeVosol";
    private static final String COLUMN_ForJayezeh = "ForJayezeh";
    private static final String COLUMN_CodeNoeHaml = "CodeNoeHaml";
    private static final String COLUMN_NameNoeField = "NameNoeField";



    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_Radif(){
        return COLUMN_Radif;
    }
    public static String COLUMN_ccTakhfifSenfi() {
        return COLUMN_ccTakhfifSenfi;
    }
    public static String COLUMN_CodeNoe() {
        return COLUMN_CodeNoe;
    }
    public static String COLUMN_SharhTakhfif() {
        return COLUMN_SharhTakhfif;
    }
    public static String COLUMN_NoeTedadRial() {
        return COLUMN_NoeTedadRial;
    }
    public static String COLUMN_NameNoeFieldMoshtary() {
        return COLUMN_NameNoeFieldMoshtary;
    }
    public static String COLUMN_ccNoeFieldMoshtary() {
        return COLUMN_ccNoeFieldMoshtary;
    }
    public static String COLUMN_Darajeh() {
        return COLUMN_Darajeh;
    }
    public static String COLUMN_ccMarkazSazmanForosh() {
        return COLUMN_ccMarkazSazmanForosh;
    }
    public static String COLUMN_ccNoeSenf() {
        return COLUMN_ccNoeSenf;
    }
    public static String COLUMN_NameNoeSenf(){return COLUMN_NameNoeSenf; }
    public static String COLUMN_NoeGheymat() {
        return COLUMN_NoeGheymat;
    }
    public static String COLUMN_ccMoshtaryGharardad() {
        return COLUMN_ccMoshtaryGharardad;
    }

    public static String COLUMN_NoeVosol() {
        return COLUMN_NoeVosol;
    }


    public static String COLUMN_ccMantagheh() {
        return COLUMN_ccMantagheh;
    }

    public static String COLUMN_IsPelekani() {
        return COLUMN_IsPelekani;
    }

    public static String COLUMN_Olaviat() {
        return COLUMN_Olaviat;
    }


    public static String COLUMN_txtNoeVosol() {
        return COLUMN_txtNoeVosol;
    }

    public static String COLUMN_ForJayezeh() {
        return COLUMN_ForJayezeh;
    }

    public static String COLUMN_CodeNoeHaml() {
        return COLUMN_CodeNoeHaml;
    }

    public static String COLUMN_NameNoeField() {
        return COLUMN_NameNoeField;
    }

    public static String COLUMN_ccGorohTakidi() {
        return COLUMN_ccGorohTakidi;
    }

    private int Radif;

    public int getRadif() {
        return Radif;
    }

    public void setRadif(int radif) {
        Radif = radif;
    }

    private int ccTakhfifSenfi;
    public int getCcTakhfifSenfi() {
        return ccTakhfifSenfi;
    }
    public void setCcTakhfifSenfi(int ccTakhfifSenfi) {
        this.ccTakhfifSenfi = ccTakhfifSenfi;
    }


    private int CodeNoe;
    public int getCodeNoe() {
        return CodeNoe;
    }
    public void setCodeNoe(int codeNoe) {
        CodeNoe = codeNoe;
    }


    private String SharhTakhfif;
    public String getSharhTakhfif() {
        return SharhTakhfif;
    }
    public void setSharhTakhfif(String sharhTakhfif) {
        SharhTakhfif = sharhTakhfif;
    }


    private int NoeTedadRial;
    public int getNoeTedadRial() {
        return NoeTedadRial;
    }
    public void setNoeTedadRial(int noeTedadRial) {
        NoeTedadRial = noeTedadRial;
    }


    private int NameNoeFieldMoshtary;
    public int getNameNoeFieldMoshtary() {
        return NameNoeFieldMoshtary;
    }
    public void setNameNoeFieldMoshtary(int nameNoeFieldMoshtary) {
        NameNoeFieldMoshtary = nameNoeFieldMoshtary;
    }


    private int ccNoeFieldMoshtary;
    public int getCcNoeFieldMoshtary() {
        return ccNoeFieldMoshtary;
    }
    public void setCcNoeFieldMoshtary(int ccNoeFieldMoshtary) {
        this.ccNoeFieldMoshtary = ccNoeFieldMoshtary;
    }


    private int Darajeh;
    public int getDarajeh() {
        return Darajeh;
    }
    public void setDarajeh(int darajeh) {
        Darajeh = darajeh;
    }


    private int ccMarkazSazmanForosh;
    public int getCcMarkazSazmanForosh()
    {
        return ccMarkazSazmanForosh;
    }
    public void setCcMarkazSazmanForosh(int ccMarkazSazmanForosh)
    {
        this.ccMarkazSazmanForosh = ccMarkazSazmanForosh;
    }

    private int ccNoeSenf;
    public int getCcNoeSenf()
    {
        return ccNoeSenf;
    }
    public void setCcNoeSenf(int ccNoeSenf)
    {
        this.ccNoeSenf = ccNoeSenf;
    }


    private String NameNoeSenf;
    public String getNameNoeSenf() {
        return NameNoeSenf;
    }
    public void setNameNoeSenf(String NameNoeSenf) {
        this.NameNoeSenf = NameNoeSenf;
    }


    private int ccMoshtaryGharardad;

    public int getCcMoshtrayGharardad() {
        return ccMoshtaryGharardad;
    }

    public void setCcMoshtrayGharardad(int ccMoshtrayGharardad) {
        this.ccMoshtaryGharardad = ccMoshtrayGharardad;
    }



    private int Olaviat;
    public int getOlaviat() {
        return Olaviat;
    }


    public void setOlaviat(int olaviat) {
        this.Olaviat = olaviat;
    }

    private int NoeGheymat;

    public int getNoeGheymat() {
        return NoeGheymat;
    }


    public void setNoeGheymat(int NoeGheymat) {
        this.NoeGheymat = NoeGheymat;
    }



    private int NoeVosol;

    public int getNoeVosol() {
        return NoeVosol;
    }

    public void setNoeVosol(int noeVosol) {
        NoeVosol = noeVosol;
    }

    private int nameNoeField;
    public int getNameNoeField() {
        return nameNoeField;
    }
    public void setNameNoeField(int nameNoeField) {
        this.nameNoeField = nameNoeField;
    }



    private int CodeNoeTakhfif;
    public void setCodeNoeTakhfif(int CodeNoeTakhfif){
        this.CodeNoeTakhfif = CodeNoeTakhfif;
    }
    public int getCodeNoeTakhfif(){
        return this.CodeNoeTakhfif;
    }







    private String FromDate;
    public void setFromDate(String FromDate){
        this.FromDate = FromDate;
    }
    public String getFromDate(){
        return this.FromDate;
    }


    private String FromDateWithoutSlash;
    public void setFromDateWithoutSlash(String FromDateWithoutSlash){
        this.FromDateWithoutSlash = FromDateWithoutSlash;
    }
    public String getFromDateWithoutSlash(){
        return this.FromDateWithoutSlash;
    }


    private String EndDate;
    public void setEndDate(String EndDate){
        this.EndDate = EndDate;
    }
    public String getEndDate(){
        return this.EndDate;
    }


    private String EndDateWithoutSlash;
    public void setEndDateWithoutSlash(String EndDateWithoutSlash){
        this.EndDateWithoutSlash = EndDateWithoutSlash;
    }
    public String getEndDateWithoutSlash(){
        return this.EndDateWithoutSlash;
    }





    private String txtNoeTedadRial;
    public void setTxtNoeTedadRial(String txtNoeTedadRial){
        this.txtNoeTedadRial = txtNoeTedadRial;
    }
    public String getTxtNoeTedadRial(){
        return this.txtNoeTedadRial;
    }





    private String txtNameNoeFieldMoshtary;
    public void setTxtNameNoeFieldMoshtary(String txtNameNoeFieldMoshtary){
        this.txtNameNoeFieldMoshtary = txtNameNoeFieldMoshtary;
    }
    public String getTxtNameNoeFieldMoshtary(){
        return this.txtNameNoeFieldMoshtary;
    }





    private int ccMoshtary;
    public void setCcMoshtary(int ccMoshtary){
        this.ccMoshtary = ccMoshtary;
    }
    public int getCcMoshtary(){
        return this.ccMoshtary;
    }


    private String NameMoshtary;
    public void setNameMoshtary(String NameMoshtary){
        this.NameMoshtary = NameMoshtary;
    }
    public String getNameMoshtary(){
        return this.NameMoshtary;
    }


    private int ccGoroh;
    public void setCcGoroh(int ccGoroh){
        this.ccGoroh = ccGoroh;
    }
    public int getCcGoroh(){
        return this.ccGoroh;
    }


    private String NameGoroh;
    public void setNameGoroh(String NameGoroh){
        this.NameGoroh = NameGoroh;
    }
    public String getNameGoroh(){
        return this.NameGoroh;
    }


    private String NameGorohText;
    public void setNameGorohText(String NameGorohText){
        this.NameGorohText = NameGorohText;
    }
    public String getNameGorohText(){
        return this.NameGorohText;
    }


    private int CodeNoeHaml;
    public void setCodeNoeHaml(int CodeNoeHaml){
        this.CodeNoeHaml = CodeNoeHaml;
    }
    public int getCodeNoeHaml(){
        return this.CodeNoeHaml;
    }


    private int BeHesab;
    public void setBeHesab(int BeHesab){
        this.BeHesab = BeHesab;
    }
    public int getBeHesab(){
        return this.BeHesab;
    }


    private String NameBeHesab;
    public void setNameBeHesab(String NameBeHesab){
        this.NameBeHesab = NameBeHesab;
    }
    public String getNameBeHesab(){
        return this.NameBeHesab;
    }


    private int SahmHesab;
    public void setSahmHesab(int SahmHesab){
        this.SahmHesab = SahmHesab;
    }
    public int getSahmHesab(){
        return this.SahmHesab;
    }





    private int ForJayezeh;
    public void setForJayezeh(int ForJayezeh){
        this.ForJayezeh = ForJayezeh;
    }
    public int getForJayezeh(){
        return this.ForJayezeh;
    }


    private int ForReport;
    public void setForReport(int ForReport){
        this.ForReport = ForReport;
    }
    public int getForReport(){
        return this.ForReport;
    }





    private String txtNoeVosol;
    public void setTxtNoeVosol(String txtNoeVosol){
        this.txtNoeVosol = txtNoeVosol;
    }
    public String getTxtNoeVosol(){
        return this.txtNoeVosol;
    }


    private int Id;
    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }


    private int ccGorohTakidi;
    public int getCcGorohTakidi()
    {
        return ccGorohTakidi;
    }
    public void setCcGorohTakidi(int ccGorohTakidi)
    {
        this.ccGorohTakidi = ccGorohTakidi;
    }



    private int IsPelekani;
    public int getIsPelekani()
    {
        return IsPelekani;
    }
    public void setIsPelekani(int isPelekani)
    {
        IsPelekani = isPelekani;
    }


    private int ccMantagheh;
    public int getCcMantagheh()
    {
        return ccMantagheh;
    }
    public void setCcMantagheh(int ccMantagheh)
    {
        this.ccMantagheh = ccMantagheh;
    }


    @Override
    public String toString() {
        return "TakhfifSenfiModel{" +
                "Radif=" + Radif +
                ", ccTakhfifSenfi=" + ccTakhfifSenfi +
                ", CodeNoe=" + CodeNoe +
                ", SharhTakhfif='" + SharhTakhfif + '\'' +
                ", NoeTedadRial=" + NoeTedadRial +
                ", NameNoeFieldMoshtary=" + NameNoeFieldMoshtary +
                ", ccNoeFieldMoshtary=" + ccNoeFieldMoshtary +
                ", Darajeh=" + Darajeh +
                ", ccMarkazSazmanForosh=" + ccMarkazSazmanForosh +
                ", ccNoeSenf=" + ccNoeSenf +
                ", NameNoeSenf='" + NameNoeSenf + '\'' +
                ", ccMoshtaryGharardad=" + ccMoshtaryGharardad +
                ", NoeGheymat=" + NoeGheymat +
                ", NoeVosol=" + NoeVosol +
                '}';
    }
}
