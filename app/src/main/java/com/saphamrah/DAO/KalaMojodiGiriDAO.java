package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MojoodiGiriModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaMojodiGiriModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KalaMojodiGiriDAO
{

    private DBHelper dbHelper;
    private Context context;


    public KalaMojodiGiriDAO(Context context)
    {
        this.context = context;
        try
        {
            dbHelper = new DBHelper(context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "KalaMojodiGiriDAO" , "" , "constructor" , "");
        }
    }

    /*private String[] allColumns()
    {
        return new String[]
        {
            MojoodiGiriModel.COLUMN_ccMojoodiGiri(),
            MojoodiGiriModel.COLUMN_ccKalaCode(),
            MojoodiGiriModel.COLUMN_ccForoshandeh(),
            MojoodiGiriModel.COLUMN_ccMoshtary(),
            MojoodiGiriModel.COLUMN_Tarikh(),
            MojoodiGiriModel.COLUMN_TedadMojoodiGiri(),
            KalaModel.COLUMN_ccBrand(),
            KalaModel.COLUMN_CodeKala(),
            KalaModel.COLUMN_NameKala()
        };
    }*/


    /**
     * همه رکوردهایی که در جدول موجودی گیری برای مشتری و فروشنده موردنظر در تاریخ امروز ثبت شده است را از دو جدول موجودی گیری و کالا برمیگرداند
     * @param ccMoshtary - current customer
     * @param ccForoshandeh - current foroshandeh
     * @return - Arraylist of KalaMojodiGiriModel
     */
    public ArrayList<KalaMojodiGiriModel> getAll(int ccMoshtary , int ccForoshandeh)
    {
        ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels = new ArrayList<>();
        /*String query = "select " +
                "MojoodiGiri.ccMojoodiGiri, MojoodiGiri.ccKalaCode, MojoodiGiri.ccForoshandeh, MojoodiGiri.ccMoshtary," +
                "MojoodiGiri.Tarikh, MojoodiGiri.TedadMojoodiGiri, Kala.ccBrand, Kala.CodeKala, Kala.NameKala \n" +
                "from MojoodiGiri inner join Kala on MojoodiGiri.ccKalaCode = Kala.ccKalaCode " +
                "where date('now') = strftime('%Y-%m-%d' , Tarikh) and MojoodiGiri.ccForoshandeh = " + ccForoshandeh +
                " and MojoodiGiri.ccMoshtary = " + ccMoshtary;*/
        String query = "select " +
                "MojoodiGiri.ccMojoodiGiri, MojoodiGiri.ccKalaCode, MojoodiGiri.ccForoshandeh, MojoodiGiri.ccMoshtary," +
                "MojoodiGiri.Tarikh, MojoodiGiri.TedadMojoodiGiri, Kala.ccBrand, Kala.CodeKala, Kala.NameKala \n" +
                "from MojoodiGiri inner join Kala on MojoodiGiri.ccKalaCode = Kala.ccKalaCode " +
                "where MojoodiGiri.ccForoshandeh = " + ccForoshandeh +
                " and MojoodiGiri.ccMoshtary = " + ccMoshtary +
                " and MojoodiGiri.InsertInPPC = 1 group by Kala.ccKalaCode";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    kalaMojodiGiriModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "KalaMojodiGiriModel") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiGiriDAO" , "" , "getAll" , "");
        }
        return kalaMojodiGiriModels;
    }


    /**
     * کالاهای موجودی گیری شده با تعداد را بر میگرداند
     * @param ccMoshtary شناسه مشتری
     * @param ccForoshandeh شناسه فروشنده
     * @return یک مپ که کلید آن کد کالا و مقدار آن تعداد برای آن کالا می باشد
     */
    public Map<Integer,Integer> getccKalaCodeAndTedad(int ccMoshtary , int ccForoshandeh)
    {
        Map<Integer,Integer> map = new HashMap<>();
        String query = "select ccKalaCode, TedadMojoodiGiri from MojoodiGiri "+
                " where MojoodiGiri.ccForoshandeh = " + ccForoshandeh + " and MojoodiGiri.ccMoshtary = " + ccMoshtary +
                " group by ccKalaCode";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast())
                    {
                        int key = cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccKalaCode()));
                        int value = cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_TedadMojoodiGiri()));
                        map.put(key, value);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "KalaMojodiGiriModel") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "KalaMojodiGiriDAO" , "" , "getccKalaCodeAndTedad" , "");
        }
        return map;
    }


    /**
     * همه رکوردهایی که در جدول موجودی گیری برای مشتری و فروشنده موردنظر در تاریخ امروز ثبت شده است را برمیگرداند
     * @param ccMoshtary - current customer
     * @param ccForoshandeh - current foroshandeh
     * @return - ccKalaCode Of Kala that concatenate with comma -> 532,540
     */
    public String getAllWithCommaSeperator(int ccMoshtary , int ccForoshandeh)
    {
        String result = "";
        /*String query = "select " +
                "MojoodiGiri.ccMojoodiGiri, MojoodiGiri.ccKalaCode, MojoodiGiri.ccForoshandeh, MojoodiGiri.ccMoshtary," +
                "MojoodiGiri.Tarikh, MojoodiGiri.TedadMojoodiGiri, Kala.ccBrand, Kala.CodeKala, Kala.NameKala \n" +
                "from MojoodiGiri inner join Kala on MojoodiGiri.ccKalaCode = Kala.ccKalaCode " +
                "where date('now') = strftime('%Y-%m-%d' , Tarikh) and MojoodiGiri.ccForoshandeh = " + ccForoshandeh +
                " and MojoodiGiri.ccMoshtary = " + ccMoshtary;*/
        String query = "select group_concat(ccKalaCode, ',')" +
                " from MojoodiGiri where MojoodiGiri.ccForoshandeh = " + ccForoshandeh + " and MojoodiGiri.ccMoshtary = " + ccMoshtary +
                " and MojoodiGiri.InsertInPPC = 1 group by ccKalaCode";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    result = cursor.getString(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "KalaMojodiGiriModel") + "\n" + exception.toString();
            logger.insertLogToDB(context, LogPPCModel.LOG_EXCEPTION, message, "KalaMojodiGiriDAO" , "" , "getAllWithCommaSeperator" , "");
        }
        return result;
    }
	
    private ArrayList<KalaMojodiGiriModel> cursorToModel(Cursor cursor)
    {
        ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            KalaMojodiGiriModel kalaMojodiGiriModel = new KalaMojodiGiriModel();

            kalaMojodiGiriModel.setCcMojoodiGiri(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccMojoodiGiri())));
            kalaMojodiGiriModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccKalaCode())));
            kalaMojodiGiriModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccForoshandeh())));
            kalaMojodiGiriModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_ccMoshtary())));
            kalaMojodiGiriModel.setTarikh(cursor.getString(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_Tarikh())));
            kalaMojodiGiriModel.setTedadMojoodiGiri(cursor.getFloat(cursor.getColumnIndex(MojoodiGiriModel.COLUMN_TedadMojoodiGiri())));
            kalaMojodiGiriModel.setCcBrand(cursor.getInt(cursor.getColumnIndex(KalaModel.COLUMN_ccBrand())));
            kalaMojodiGiriModel.setCodeKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_CodeKala())));
            kalaMojodiGiriModel.setNameKala(cursor.getString(cursor.getColumnIndex(KalaModel.COLUMN_NameKala())));

            kalaMojodiGiriModels.add(kalaMojodiGiriModel);
            cursor.moveToNext();
        }
        return kalaMojodiGiriModels;
    }

}
