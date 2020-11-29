package com.saphamrah.Model;

import androidx.annotation.NonNull;

public class MessageBoxStatusModel
{

    private static final String TABLE_NAME = "MessageBoxStatus";
    private static final String COLUMN_ccMessage = "ccMessage";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMamorPakhsh = "ccMamorPakhsh";
    private static final String COLUMN_Status = "Status";
    private static final String COLUMN_Active = "Active";

    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMessage()
    {
        return COLUMN_ccMessage;
    }
    public static String COLUMN_ccForoshandeh()
    {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMamorPakhsh()
    {
        return COLUMN_ccMamorPakhsh;
    }
    public static String COLUMN_Status()
    {
        return COLUMN_Status;
    }
    public static String COLUMN_Active()
    {
        return COLUMN_Active;
    }


    private int ccMessage;
    private int ccForoshandeh;
    private int ccMamorPakhsh;
    private int Status;
    private int Active;


    public int getCcMessage()
    {
        return ccMessage;
    }

    public void setCcMessage(int ccMessage)
    {
        this.ccMessage = ccMessage;
    }

    public int getCcForoshandeh()
    {
        return ccForoshandeh;
    }

    public void setCcForoshandeh(int ccForoshandeh)
    {
        this.ccForoshandeh = ccForoshandeh;
    }

    public int getCcMamorPakhsh()
    {
        return ccMamorPakhsh;
    }

    public void setCcMamorPakhsh(int ccMamorPakhsh)
    {
        this.ccMamorPakhsh = ccMamorPakhsh;
    }

    public int getStatus()
    {
        return Status;
    }

    public void setStatus(int status)
    {
        Status = status;
    }

    public int getActive()
    {
        return Active;
    }

    public void setActive(int active)
    {
        Active = active;
    }


    @NonNull
    @Override
    public String toString()
    {
        return "MessageBoxStatusModel{" +
                "ccMessage=" + ccMessage +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMamorPakhsh=" + ccMamorPakhsh +
                ", Status=" + Status +
                ", Active=" + Active +
                '}';
    }
}
