package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.MarjoeeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class MarjoeeDAO
{

    private DBHelper dbHelper;
    private Context context;


    public MarjoeeDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "MarjoeeDAO" , "" , "constructor" , "");
        }
    }


    private String[] allColumns()
    {
        return new String[]
        {
            MarjoeeModel.COLUMN_ccMarjoee(),
            MarjoeeModel.COLUMN_ccDarkhastFaktor(),
            MarjoeeModel.COLUMN_ccMarkazAnbar(),
            MarjoeeModel.COLUMN_ccGorohForosh(),
            MarjoeeModel.COLUMN_ccForoshandeh(),
            MarjoeeModel.COLUMN_ccMoshtary(),
            MarjoeeModel.COLUMN_NameMoshtary(),
            MarjoeeModel.COLUMN_CodeMoshtaryOld(),
            MarjoeeModel.COLUMN_ShomarehFaktor(),
            MarjoeeModel.COLUMN_TarikhFaktor(),
            MarjoeeModel.COLUMN_TarikhErsal(),
            MarjoeeModel.COLUMN_ccKalaCode(),
            MarjoeeModel.COLUMN_CodeKalaT(),
            MarjoeeModel.COLUMN_NameKala(),
            MarjoeeModel.COLUMN_Tedad(),
            MarjoeeModel.COLUMN_Mablagh(),
            MarjoeeModel.COLUMN_ccElat(),
            MarjoeeModel.COLUMN_Tarikh()
        };
    }

    public void insertGroup(ArrayList<MarjoeeModel> marjoeeModels)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (MarjoeeModel marjoeeModel : marjoeeModels)
            {
                ContentValues contentValues = modelToContentvalue(marjoeeModel);
                db.insertOrThrow(MarjoeeModel.TableName() , null , contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();
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
            String message = context.getResources().getString(R.string.errorGroupInsert , MarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeDAO" , "" , "insertGroup" , "");
        }
    }


    public ArrayList<MarjoeeModel> getAll()
    {
        ArrayList<MarjoeeModel> marjoeeModels = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(MarjoeeModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    marjoeeModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , MarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeDAO" , "" , "getAll" , "");
        }
        return marjoeeModels;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(MarjoeeModel.TableName(), null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , MarjoeeModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "MarjoeeDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(MarjoeeModel marjoeeModel)
    {
        ContentValues contentValues = new ContentValues();

        if (marjoeeModel.getCcMarjoee() != null && marjoeeModel.getCcMarjoee() > 0)
        {
            contentValues.put(MarjoeeModel.COLUMN_ccMarjoee() , marjoeeModel.getCcMarjoee());
        }
        contentValues.put(MarjoeeModel.COLUMN_ccDarkhastFaktor() , marjoeeModel.getCcDarkhastFaktor());
        contentValues.put(MarjoeeModel.COLUMN_ccMarkazAnbar() , marjoeeModel.getCcMarkazAnbar());
        contentValues.put(MarjoeeModel.COLUMN_ccGorohForosh() , marjoeeModel.getCcGorohForosh());
        contentValues.put(MarjoeeModel.COLUMN_ccForoshandeh() , marjoeeModel.getCcForoshandeh());
        contentValues.put(MarjoeeModel.COLUMN_ccMoshtary() , marjoeeModel.getCcMoshtary());
        contentValues.put(MarjoeeModel.COLUMN_NameMoshtary() , marjoeeModel.getNameMoshtary());
        contentValues.put(MarjoeeModel.COLUMN_CodeMoshtaryOld() , marjoeeModel.getCodeMoshtaryOld());
        contentValues.put(MarjoeeModel.COLUMN_ShomarehFaktor() , marjoeeModel.getShomarehFaktor());
        contentValues.put(MarjoeeModel.COLUMN_TarikhFaktor() , marjoeeModel.getTarikhFaktor());
        contentValues.put(MarjoeeModel.COLUMN_TarikhErsal() , marjoeeModel.getTarikhErsal());
        contentValues.put(MarjoeeModel.COLUMN_ccKalaCode() , marjoeeModel.getCcKalaCode());
        contentValues.put(MarjoeeModel.COLUMN_CodeKalaT() , marjoeeModel.getCodeKalaT());
        contentValues.put(MarjoeeModel.COLUMN_NameKala() , marjoeeModel.getNameKala());
        contentValues.put(MarjoeeModel.COLUMN_Tedad() , marjoeeModel.getTedad());
        contentValues.put(MarjoeeModel.COLUMN_Mablagh() , marjoeeModel.getMablagh());
        contentValues.put(MarjoeeModel.COLUMN_ccElat() , marjoeeModel.getCcElat());
        contentValues.put(MarjoeeModel.COLUMN_Tarikh() , marjoeeModel.getTarikh());

        return contentValues;
    }


    private ArrayList<MarjoeeModel> cursorToModel(Cursor cursor)
    {
        ArrayList<MarjoeeModel> marjoeeModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            MarjoeeModel marjoeeModel = new MarjoeeModel();

            marjoeeModel.setCcMarjoee(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccMarjoee())));
            marjoeeModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccDarkhastFaktor())));
            marjoeeModel.setCcMarkazAnbar(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccMarkazAnbar())));
            marjoeeModel.setCcGorohForosh(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccGorohForosh())));
            marjoeeModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccForoshandeh())));
            marjoeeModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccMoshtary())));
            marjoeeModel.setNameMoshtary(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_NameMoshtary())));
            marjoeeModel.setCodeMoshtaryOld(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_CodeMoshtaryOld())));
            marjoeeModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ShomarehFaktor())));
            marjoeeModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_TarikhFaktor())));
            marjoeeModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_TarikhErsal())));
            marjoeeModel.setCcKalaCode(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccKalaCode())));
            marjoeeModel.setCodeKalaT(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_CodeKalaT())));
            marjoeeModel.setNameKala(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_NameKala())));
            marjoeeModel.setTedad(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_Tedad())));
            marjoeeModel.setMablagh(cursor.getFloat(cursor.getColumnIndex(MarjoeeModel.COLUMN_Mablagh())));
            marjoeeModel.setCcElat(cursor.getInt(cursor.getColumnIndex(MarjoeeModel.COLUMN_ccElat())));
            marjoeeModel.setTarikh(cursor.getString(cursor.getColumnIndex(MarjoeeModel.COLUMN_Tarikh())));

            marjoeeModels.add(marjoeeModel);
            cursor.moveToNext();
        }
        return marjoeeModels;
    }

}
