package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.MoshtaryFaktorModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MoshtaryFaktorDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MoshtaryFaktorDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MoshtaryFaktorDAO" , "" , "constructor" , "");
        }
    }

    public ArrayList<MoshtaryFaktorModel> getAllWithSum()
    {
        ArrayList<MoshtaryFaktorModel> moshtaryFaktorModels = new ArrayList<>();
        try
        {
            String query = "SELECT 0 as id, DarkhastFaktor.ccMoshtary AS ccMoshtary, Moshtary.CodeMoshtary As CodeMoshtary, NameMoshtary , \n" +
                    "      COUNT(ccDarkhastFaktor) AS TedadFaktor, Sum(MablaghKhalesFaktor) AS MablaghKhalesFaktor, \n" +
                    "      Round((COUNT(ccDarkhastFaktor) * 1.0 / (90/ToorVisit )) * 100,1) AS DarsadVisitMosbat \n" +
                    " FROM   Moshtary LEFT JOIN DarkhastFaktor ON DarkhastFaktor.ccMoshtary= Moshtary.ccMoshtary \n" +
                    " WHERE TarikhFaktor >= (DATETIME('now', '-90 day')) \n" +
                    " GROUP BY DarkhastFaktor.ccMoshtary, Moshtary.CodeMoshtary, NameMoshtary \n" +
                    " HAVING COUNT(ccDarkhastFaktor) > 0 \n" +
                    " UNION ALL \n" +
                    " SELECT 1 as id, 0, '', 'جمع' , COUNT(ccDarkhastFaktor) AS TedadFaktor, Sum(MablaghKhalesFaktor) AS MablaghKhalesFaktor, \n" +
                    "       0 AS DarsadVisitMosbat \n" +
                    " FROM   Moshtary LEFT JOIN DarkhastFaktor ON DarkhastFaktor.ccMoshtary= Moshtary.ccMoshtary \n" +
                    " WHERE TarikhFaktor >= (DATETIME('now', '-90 day')) \n" +
                    " order by id";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    moshtaryFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "Moshtary Left Join DarkhastFaktor") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "BrandDAO" , "" , "getAll" , "");
        }
        return moshtaryFaktorModels;
    }


    private ArrayList<MoshtaryFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MoshtaryFaktorModel> moshtaryFaktorModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MoshtaryFaktorModel moshtaryFaktorModel = new MoshtaryFaktorModel();

            moshtaryFaktorModel.setId(cursor.getInt(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_id())));
            moshtaryFaktorModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_ccMoshtary())));
            moshtaryFaktorModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_CodeMoshtary())));
            moshtaryFaktorModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_NameMoshtary())));
            moshtaryFaktorModel.setTedadFaktor(cursor.getInt(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_TedadFaktor())));
            moshtaryFaktorModel.setMablaghKhalesFaktor(cursor.getDouble(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_MablaghKhalesFaktor())));
            moshtaryFaktorModel.setDarsadVisitMosbat(cursor.getInt(cursor.getColumnIndex(MoshtaryFaktorModel.COLUMN_DarsadVisitMosbat())));

            moshtaryFaktorModels.add(moshtaryFaktorModel);
            cursor.moveToNext();
        }
        return moshtaryFaktorModels;
    }

}
