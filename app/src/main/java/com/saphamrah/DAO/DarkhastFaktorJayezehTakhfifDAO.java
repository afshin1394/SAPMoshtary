package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.DarkhastFaktorJayezehTakhfifModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class DarkhastFaktorJayezehTakhfifDAO
{

    private DBHelper dbHelper;
    private Context context;


    public DarkhastFaktorJayezehTakhfifDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "DarkhastFaktorJayezehTakhfifDAO" , "" , "constructor" , "");
        }
    }

    /*private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorJayezehTakhfifModel.COLUMN_NoeJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_ccDarkhastFaktorJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_ccJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_SharhJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_CodeNoeJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_MablaghJayezehTakhfif(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_ccKalaJayezeh(),
            DarkhastFaktorJayezehTakhfifModel.COLUMN_TedadJayezeh()
        };
    }*/

    public ArrayList<DarkhastFaktorJayezehTakhfifModel> getByccDarkhastFaktorccTakhfif(long ccDarkhastFaktor , String ccTakhfifs)
    {
        ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "select 2 AS NoeJayezehTakhfif, 0 AS ExtraProp_ccJayezehSatr, ccDarkhastFaktorTakhfif AS ccDarkhastFaktorJayezehTakhfif, ccDarkhastFaktor, \n" +
                    " ccTakhfif AS ccJayezehTakhfif, SharhTakhfif AS SharhJayezehTakhfif, CodeNoeTakhfif AS CodeNoeJayezehTakhfif, \n" +
                    " MablaghTakhfif AS MablaghJayezehTakhfif, '-1' AS ccKalaJayezeh, -1 AS TedadJayezeh \n" +
                    " from DarkhastFaktorTakhfif \n" +
                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " and CodeNoeTakhfif = 5 and ExtraProp_ForJayezeh = 1 and ccTakhfif in (" + ccTakhfifs + ")" +
                    " UNION ALL \n" +
                    " select 1 AS NoeJayezehTakhfif, ExtraProp_ccJayezehSatr, ccDarkhastFaktorJayezeh, ccDarkhastFaktor, ccJayezeh, Sharh, ExtraProp_CodeNoeJayezeh, -1, ccKala, Tedad \n" +
                    " from DarkhastFaktorJayezeh \n" +
                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " and ExtraProp_CodeNoeJayezeh = " + DarkhastFaktorJayezehModel.CodeNoeJayezehEntekhabi()+
                    " UNION ALL " +
                    " select 4 AS NoeJayezehTakhfif, ExtraProp_ccJayezehTakhfif As ExtraProp_ccJayezehSatr, ccDarkhastFaktorTakhfif AS ccDarkhastFaktorJayezehTakhfif, ccDarkhastFaktor, \n" +
                    " ccTakhfif AS ccJayezehTakhfif, SharhTakhfif AS SharhJayezehTakhfif, CodeNoeTakhfif AS CodeNoeJayezehTakhfif, \n" +
                    " MablaghTakhfif AS MablaghJayezehTakhfif, '-1' AS ccKalaJayezeh, -1 AS TedadJayezeh \n" +
                    " from DarkhastFaktorTakhfif \n" +
                    " where ccDarkhastFaktor = " + ccDarkhastFaktor + " and CodeNoeTakhfif = 4 and  ExtraProp_ForJayezeh = 1 and ccTakhfif in (" + ccTakhfifs + ")";
            Log.d("bonus", "Jayezeh getByccDarkhastFaktorccTakhfif query : " + query);
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    darkhastFaktorJayezehTakhfifModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "DarkhastFaktorTakhfif , DarkhastFaktorJayezeh") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "DarkhastFaktorJayezehTakhfifDAO" , "" , "getAll" , "");
        }
        return darkhastFaktorJayezehTakhfifModels;
    }


    private ArrayList<DarkhastFaktorJayezehTakhfifModel> cursorToModel(Cursor cursor)
    {
        ArrayList<DarkhastFaktorJayezehTakhfifModel> darkhastFaktorJayezehTakhfifModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DarkhastFaktorJayezehTakhfifModel darkhastFaktorJayezehTakhfifModel = new DarkhastFaktorJayezehTakhfifModel();

            darkhastFaktorJayezehTakhfifModel.setNoeJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_NoeJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setCcDarkhastFaktorJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_ccDarkhastFaktorJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_ccDarkhastFaktor())));
            darkhastFaktorJayezehTakhfifModel.setCcJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_ccJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setExtraProp_ccJayezehSatr(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_ExtraProp_ccJayezehSatr())));
            darkhastFaktorJayezehTakhfifModel.setSharhJayezehTakhfif(cursor.getString(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_SharhJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setCodeNoeJayezehTakhfif(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_CodeNoeJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setMablaghJayezehTakhfif(cursor.getFloat(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_MablaghJayezehTakhfif())));
            darkhastFaktorJayezehTakhfifModel.setCcKalaJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_ccKalaJayezeh())));
            darkhastFaktorJayezehTakhfifModel.setTedadJayezeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorJayezehTakhfifModel.COLUMN_TedadJayezeh())));

            darkhastFaktorJayezehTakhfifModels.add(darkhastFaktorJayezehTakhfifModel);
            cursor.moveToNext();
        }
        return darkhastFaktorJayezehTakhfifModels;
    }

}
