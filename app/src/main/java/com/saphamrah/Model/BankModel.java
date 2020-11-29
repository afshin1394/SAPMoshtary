package com.saphamrah.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankModel
{

    private static final String TABLE_NAME = "Bank";
    private static final String COLUMN_ccBank = "ccBank";
	private static final String COLUMN_CodeBankInSheba = "CodeBankInSheba";
    private static final String COLUMN_NameBank = "NameBank";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccBank() {
        return COLUMN_ccBank;
    }
    public static String COLUMN_CodeBankInSheba() {
        return COLUMN_CodeBankInSheba;
    }
    public static String COLUMN_NameBank() {
        return COLUMN_NameBank;
    }



    @SerializedName("ccBank")
    @Expose
    private Integer ccBank;
    @SerializedName("NameBank")
    @Expose
    private String NameBank;
    @SerializedName("CodeBankInsheba")
    @Expose
    private String CodeBankInsheba;


    public Integer getCcBank() {
        return ccBank;
    }

    public void setCcBank(Integer ccBank) {
        this.ccBank = ccBank;
    }

    public String getNameBank() {
        return NameBank;
    }

    public void setNameBank(String nameBank) {
        NameBank = nameBank;
    }

	public String getCodeBankInsheba()
    {
        return CodeBankInsheba;
    }

    public void setCodeBankInsheba(String codeBankInsheba)
    {
        CodeBankInsheba = codeBankInsheba;
    }

									
						  
	 

    @Override
    public String toString()
    {
        return "BankModel{" +
                "ccBank=" + ccBank +
                ", NameBank='" + NameBank + '\'' +
                ", CodeBankInsheba='" + CodeBankInsheba + '\'' +
                '}';
    }
	

}
