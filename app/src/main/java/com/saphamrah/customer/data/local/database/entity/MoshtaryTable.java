package com.saphamrah.customer.data.local.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoshtaryTable {
    @PrimaryKey
    public int ccMoshtary;

    private String nameMoshtary;
    private String nameTablo;
    private String codeEghtesady;
    private String codeMely;
    private String shenasehMeli;
    private String mobile;
    private String telephone;
    private String tarikhTavalod;
    private String email;
    private String codeMoshtary;
    private String companyTitle;

    private int codeNoeShakhsiat;
    private int codeJesiat;
    private int ccCompany;
    private int isValid;

    public MoshtaryTable(int ccMoshtary, String nameMoshtary, String nameTablo, String codeEghtesady, String codeMely, String shenasehMeli, String mobile, String telephone, String tarikhTavalod, String email, String codeMoshtary, String companyTitle, int codeNoeShakhsiat, int codeJesiat, int ccCompany, int isValid) {
        this.ccMoshtary = ccMoshtary;
        this.nameMoshtary = nameMoshtary;
        this.nameTablo = nameTablo;
        this.codeEghtesady = codeEghtesady;
        this.codeMely = codeMely;
        this.shenasehMeli = shenasehMeli;
        this.mobile = mobile;
        this.telephone = telephone;
        this.tarikhTavalod = tarikhTavalod;
        this.email = email;
        this.codeMoshtary = codeMoshtary;
        this.companyTitle = companyTitle;
        this.codeNoeShakhsiat = codeNoeShakhsiat;
        this.codeJesiat = codeJesiat;
        this.ccCompany = ccCompany;
        this.isValid = isValid;
    }

    public int getCcMoshtary() {
        return ccMoshtary;
    }

    public void setCcMoshtary(int ccMoshtary) {
        this.ccMoshtary = ccMoshtary;
    }

    public String getNameMoshtary() {
        return nameMoshtary;
    }

    public void setNameMoshtary(String nameMoshtary) {
        this.nameMoshtary = nameMoshtary;
    }

    public String getNameTablo() {
        return nameTablo;
    }

    public void setNameTablo(String nameTablo) {
        this.nameTablo = nameTablo;
    }

    public String getCodeEghtesady() {
        return codeEghtesady;
    }

    public void setCodeEghtesady(String codeEghtesady) {
        this.codeEghtesady = codeEghtesady;
    }

    public String getCodeMely() {
        return codeMely;
    }

    public void setCodeMely(String codeMely) {
        this.codeMely = codeMely;
    }

    public String getShenasehMeli() {
        return shenasehMeli;
    }

    public void setShenasehMeli(String shenasehMeli) {
        this.shenasehMeli = shenasehMeli;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTarikhTavalod() {
        return tarikhTavalod;
    }

    public void setTarikhTavalod(String tarikhTavalod) {
        this.tarikhTavalod = tarikhTavalod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeMoshtary() {
        return codeMoshtary;
    }

    public void setCodeMoshtary(String codeMoshtary) {
        this.codeMoshtary = codeMoshtary;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public int getCodeNoeShakhsiat() {
        return codeNoeShakhsiat;
    }

    public void setCodeNoeShakhsiat(int codeNoeShakhsiat) {
        this.codeNoeShakhsiat = codeNoeShakhsiat;
    }

    public int getCodeJesiat() {
        return codeJesiat;
    }

    public void setCodeJesiat(int codeJesiat) {
        this.codeJesiat = codeJesiat;
    }

    public int getCcCompany() {
        return ccCompany;
    }

    public void setCcCompany(int ccCompany) {
        this.ccCompany = ccCompany;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
