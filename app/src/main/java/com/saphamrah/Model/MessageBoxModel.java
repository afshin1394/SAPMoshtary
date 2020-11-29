package com.saphamrah.Model;

import androidx.annotation.NonNull;

import org.json.JSONObject;


public class MessageBoxModel
{

    private static final String TABLE_NAME = "MessageBox";
    private static final String COLUMN_ccMessage = "ccMessage";
    private static final String COLUMN_Title = "Title";
    private static final String COLUMN_Message = "Message";
    private static final String COLUMN_NoeMessage = "NoeMessage";
    private static final String COLUMN_ccUser = "ccUser";
    private static final String COLUMN_Active = "Active";
    private static final String COLUMN_Tarikh = "Tarikh";
    private static final String COLUMN_ccForoshandeh = "ccForoshandeh";
    private static final String COLUMN_ccMamorPakhsh = "ccMamorPakhsh";
    private static final String COLUMN_StatusForoshandeh = "StatusForoshandeh";
    private static final String COLUMN_ActiveForoshandeh = "ActiveForoshandeh";
    private static final String COLUMN_NotificationForoshandeh = "NotificationForoshandeh";


    public static String TableName()
    {
        return TABLE_NAME;
    }
    public static String COLUMN_ccMessage()
    {
        return COLUMN_ccMessage;
    }
    public static String COLUMN_Title()
    {
        return COLUMN_Title;
    }
    public static String COLUMN_Message()
    {
        return COLUMN_Message;
    }
    public static String COLUMN_NoeMessage()
    {
        return COLUMN_NoeMessage;
    }
    public static String COLUMN_ccUser()
    {
        return COLUMN_ccUser;
    }
    public static String COLUMN_Active()
    {
        return COLUMN_Active;
    }
    public static String COLUMN_Tarikh()
    {
        return COLUMN_Tarikh;
    }
    public static String COLUMN_ccForoshandeh()
    {
        return COLUMN_ccForoshandeh;
    }
    public static String COLUMN_ccMamorPakhsh()
    {
        return COLUMN_ccMamorPakhsh;
    }
    public static String COLUMN_StatusForoshandeh()
    {
        return COLUMN_StatusForoshandeh;
    }
    public static String COLUMN_ActiveForoshandeh()
    {
        return COLUMN_ActiveForoshandeh;
    }
    public static String COLUMN_NotificationForoshandeh()
    {
        return COLUMN_NotificationForoshandeh;
    }



    private int ccMessage;
    private String Title;
    private String Message;
    private int NoeMessage;
    private int ccUser;
    private int Active;
    private String Tarikh;
    private int ccForoshandeh;
    private int ccMamorPakhsh;
    private int StatusForoshandeh;
    private int ActiveForoshandeh;
    private int NotificationForoshandeh;


    public int getCcMessage()
    {
        return ccMessage;
    }

    public void setCcMessage(int ccMessage)
    {
        this.ccMessage = ccMessage;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String title)
    {
        Title = title;
    }

    public String getMessage()
    {
        return Message;
    }

    public void setMessage(String message)
    {
        Message = message;
    }

    public int getNoeMessage()
    {
        return NoeMessage;
    }

    public void setNoeMessage(int noeMessage)
    {
        NoeMessage = noeMessage;
    }

    public int getCcUser()
    {
        return ccUser;
    }

    public void setCcUser(int ccUser)
    {
        this.ccUser = ccUser;
    }

    public int getActive()
    {
        return Active;
    }

    public void setActive(int active)
    {
        Active = active;
    }

    public String getTarikh()
    {
        return Tarikh;
    }

    public void setTarikh(String tarikh)
    {
        Tarikh = tarikh;
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

    public int getStatusForoshandeh()
    {
        return StatusForoshandeh;
    }

    public void setStatusForoshandeh(int statusForoshandeh)
    {
        StatusForoshandeh = statusForoshandeh;
    }

    public int getActiveForoshandeh()
    {
        return ActiveForoshandeh;
    }

    public void setActiveForoshandeh(int activeForoshandeh)
    {
        ActiveForoshandeh = activeForoshandeh;
    }

    public int getNotificationForoshandeh()
    {
        return NotificationForoshandeh;
    }

    public void setNotificationForoshandeh(int notificationForoshandeh)
    {
        NotificationForoshandeh = notificationForoshandeh;
    }



    public String toJsonString()
    {
        JSONObject jsonObject = new JSONObject();
        try
        {
            jsonObject.put("ccMessage" , ccMessage);
            jsonObject.put("Title" , Title);
            jsonObject.put("Message" , Message);
            jsonObject.put("NoeMessage" , NoeMessage);
            jsonObject.put("ccUser" , ccUser);
            jsonObject.put("Active" , Active);
            jsonObject.put("ccForoshandeh" , ccForoshandeh);
            jsonObject.put("ccMamorPakhsh" , ccMamorPakhsh);
            jsonObject.put("StatusForoshandeh" , StatusForoshandeh);
            jsonObject.put("ActiveForoshandeh" , ActiveForoshandeh);
            jsonObject.put("NotificationForoshandeh" , NotificationForoshandeh);
            return jsonObject.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }



    @NonNull
    @Override
    public String toString()
    {
        return "MessageBoxModel{" +
                "ccMessage=" + ccMessage +
                ", Title='" + Title + '\'' +
                ", Message='" + Message + '\'' +
                ", NoeMessage=" + NoeMessage +
                ", ccUser=" + ccUser +
                ", Active=" + Active +
                ", Tarikh='" + Tarikh + '\'' +
                ", ccForoshandeh=" + ccForoshandeh +
                ", ccMamorPakhsh=" + ccMamorPakhsh +
                ", StatusForoshandeh=" + StatusForoshandeh +
                ", ActiveForoshandeh=" + ActiveForoshandeh +
                ", NotificationForoshandeh=" + NotificationForoshandeh +
                '}';
    }
}
