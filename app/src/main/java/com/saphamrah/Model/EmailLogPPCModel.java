package com.saphamrah.Model;

public class EmailLogPPCModel
{

    public static final String MAIL_HOST = "smtp.gmail.com";
    public static final String MAIL_PORT = "465";
    public static final String MAIL_RECEIVER = "Erp_logppc@mihan-dairy.com";
	
    private static final String TABLE_NAME = "EmailLogPPC";
    private static final String COLUMN_ccEmailLogPPC = "ccEmailLogPPC";
    private static final String COLUMN_Email = "Email";
    private static final String COLUMN_Password = "Password";


    public static String TableName() {
        return TABLE_NAME;
    }
    public static String COLUMN_ccEmailLogPPC() {
        return COLUMN_ccEmailLogPPC;
    }
    public static String COLUMN_Email() {
        return COLUMN_Email;
    }
    public static String COLUMN_Password() {
        return COLUMN_Password;
    }



    private int ccEmailLogPPC;
    private String email;
    private String password;


    public int getCcEmailLogPPC() {
        return ccEmailLogPPC;
    }

    public void setCcEmailLogPPC(int ccEmailLogPPC) {
        this.ccEmailLogPPC = ccEmailLogPPC;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
