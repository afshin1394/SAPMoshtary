package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.RptVisitForoshandehForoshandehModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class RptVisitForoshandehForoshandehDAO 
{

    private DBHelper dbHelper;
    private Context context;


    public RptVisitForoshandehForoshandehDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "RptVisitForoshandehForoshandehDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            RptVisitForoshandehForoshandehModel.COLUMN_Radif(),
            RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MoshtaryRooz(),
            RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehShodeh(),
            RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehNashodeh(),
            RptVisitForoshandehForoshandehModel.COLUMN_Tedad_VisitMosbat(),
            RptVisitForoshandehForoshandehModel.COLUMN_DarsadMorajeh(),
            RptVisitForoshandehForoshandehModel.COLUMN_Tedad_Faktor(),
            RptVisitForoshandehForoshandehModel.COLUMN_DarsadMosbat(),
            RptVisitForoshandehForoshandehModel.COLUMN_MablaghForosh(),
            RptVisitForoshandehForoshandehModel.COLUMN_MotevasetRialFaktor(),
            RptVisitForoshandehForoshandehModel.COLUMN_MotevasetTedadAghlamForosh()
        };
    }


    public boolean insertGroup(ArrayList<RptVisitForoshandehForoshandehModel> rptVisitForoshandehForoshandehModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (RptVisitForoshandehForoshandehModel rptVisitForoshandehForoshandehModel : rptVisitForoshandehForoshandehModels)
            {
                ContentValues contentValues = modelToContentvalue(rptVisitForoshandehForoshandehModel);
                db.insertOrThrow(RptVisitForoshandehForoshandehModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (db.inTransaction())
            {
                db.endTransaction();
            }
            if (db.isOpen())
            {
                db.close();
            }
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , RptVisitForoshandehForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehForoshandehDAO" , "" , "insertGroup" , "");
            return false;
        }
    }


    public ArrayList<RptVisitForoshandehForoshandehModel> getAll()
    {
        ArrayList<RptVisitForoshandehForoshandehModel> rptVisitForoshandehForoshandehModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(RptVisitForoshandehForoshandehModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    rptVisitForoshandehForoshandehModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , RptVisitForoshandehForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehForoshandehDAO" , "" , "getAll" , "");
        }
        return rptVisitForoshandehForoshandehModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(RptVisitForoshandehForoshandehModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , RptVisitForoshandehForoshandehModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "RptVisitForoshandehForoshandehDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(RptVisitForoshandehForoshandehModel rptVisitForoshandehForoshandehModel)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Radif() , rptVisitForoshandehForoshandehModel.getRadif());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MoshtaryRooz() , rptVisitForoshandehForoshandehModel.getTedad_MoshtaryRooz());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehShodeh() , rptVisitForoshandehForoshandehModel.getTedad_MorajehShodeh());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehNashodeh() , rptVisitForoshandehForoshandehModel.getTedad_MorajehNashodeh());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_VisitMosbat() , rptVisitForoshandehForoshandehModel.getTedad_VisitMosbat());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_DarsadMorajeh() , rptVisitForoshandehForoshandehModel.getDarsadMorajeh());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_Faktor() , rptVisitForoshandehForoshandehModel.getTedad_Faktor());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_DarsadMosbat() , rptVisitForoshandehForoshandehModel.getDarsadMosbat());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_MablaghForosh() , rptVisitForoshandehForoshandehModel.getMablaghForosh());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_MotevasetRialFaktor() , rptVisitForoshandehForoshandehModel.getMotevasetRialFaktor());
        contentValues.put(RptVisitForoshandehForoshandehModel.COLUMN_MotevasetTedadAghlamForosh() , rptVisitForoshandehForoshandehModel.getMotevasetTedadAghlamForosh());

        return contentValues;
    }


    private ArrayList<RptVisitForoshandehForoshandehModel> cursorToModel(Cursor cursor)
    {
        ArrayList<RptVisitForoshandehForoshandehModel> rptVisitForoshandehForoshandehModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            RptVisitForoshandehForoshandehModel rptVisitForoshandehForoshandehModel = new RptVisitForoshandehForoshandehModel();

            rptVisitForoshandehForoshandehModel.setRadif(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Radif())));
            rptVisitForoshandehForoshandehModel.setTedad_MoshtaryRooz(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MoshtaryRooz())));
            rptVisitForoshandehForoshandehModel.setTedad_MorajehShodeh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehShodeh())));
            rptVisitForoshandehForoshandehModel.setTedad_MorajehNashodeh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_MorajehNashodeh())));
            rptVisitForoshandehForoshandehModel.setTedad_VisitMosbat(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_VisitMosbat())));
            rptVisitForoshandehForoshandehModel.setDarsadMorajeh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_DarsadMorajeh())));
            rptVisitForoshandehForoshandehModel.setTedad_Faktor(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_Tedad_Faktor())));
            rptVisitForoshandehForoshandehModel.setDarsadMosbat(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_DarsadMosbat())));
            rptVisitForoshandehForoshandehModel.setMablaghForosh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_MablaghForosh())));
            rptVisitForoshandehForoshandehModel.setMotevasetRialFaktor(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_MotevasetRialFaktor())));
            rptVisitForoshandehForoshandehModel.setMotevasetTedadAghlamForosh(cursor.getInt(cursor.getColumnIndex(RptVisitForoshandehForoshandehModel.COLUMN_MotevasetTedadAghlamForosh())));

            rptVisitForoshandehForoshandehModels.add(rptVisitForoshandehForoshandehModel);
            cursor.moveToNext();
        }
        return rptVisitForoshandehForoshandehModels;
    }



}
