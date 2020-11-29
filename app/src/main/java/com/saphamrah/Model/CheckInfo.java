package com.saphamrah.Model;

public class CheckInfo
{

    private int noeShakhsiat;
    private String nameNoeShakhsiat;
    private String nameSahebHesab;
    private String iban;
    private String branchCode;
    private String sayyadId;
    private String checkNumber;

    public CheckInfo()
    {
        noeShakhsiat = 0;
        nameNoeShakhsiat = "";
        nameSahebHesab = "";
        iban = "";
        branchCode = "";
        sayyadId = "";
        checkNumber = "";
    }

    public int getNoeShakhsiat()
    {
        return noeShakhsiat;
    }
    public void setNoeShakhsiat(int noeShakhsiat)
    {
        this.noeShakhsiat = noeShakhsiat;
    }

    public String getNameNoeShakhsiat()
    {
        return nameNoeShakhsiat;
    }
    public void setNameNoeShakhsiat(String nameNoeShakhsiat)
    {
        this.nameNoeShakhsiat = nameNoeShakhsiat;
    }

    public String getNameSahebHesab()
    {
        return nameSahebHesab;
    }
    public void setNameSahebHesab(String nameSahebHesab)
    {
        this.nameSahebHesab = nameSahebHesab;
    }

    public String getIban()
    {
        return iban;
    }
    public void setIban(String iban)
    {
        this.iban = iban;
    }

    public String getBranchCode()
    {
        return branchCode;
    }
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public String getSayyadId()
    {
        return sayyadId;
    }
    public void setSayyadId(String sayyadId)
    {
        this.sayyadId = sayyadId;
    }

    public String getCheckNumber()
    {
        return checkNumber;
    }
    public void setCheckNumber(String checkNumber)
    {
        this.checkNumber = checkNumber;
    }
}
