package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.Model.KalaMojodiModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.Model.VarizBeBankModel;
import com.saphamrah.Network.RetrofitResponse;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.WebService.APIServiceGet;
import com.saphamrah.WebService.ApiClientGlobal;
import com.saphamrah.WebService.ServiceResponse.GetVarizBeBankResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestDAO
{

    private DBHelper dbHelper;
    private Context context;
    private SuggestModel modelTABLE_NAME = new SuggestModel();

    public SuggestDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "SuggestDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
                modelTABLE_NAME.getCOLUMN_ccSuggest(),
                modelTABLE_NAME.getCOLUMN_ccNoePishnehad(),
                modelTABLE_NAME.getCOLUMN_ccForoshandeh(),
                modelTABLE_NAME.getCOLUMN_ccMoshtary(),
                modelTABLE_NAME.getCOLUMN_ccAfrad(),
                modelTABLE_NAME.getCOLUMN_ccAmargar(),
                modelTABLE_NAME.getCOLUMN_ccMamorPakhsh(),
                modelTABLE_NAME.getCOLUMN_Description(),
                modelTABLE_NAME.getCOLUMN_DescriptionPishnehad(),
                modelTABLE_NAME.getCOLUMN_Date(),
                modelTABLE_NAME.getCOLUMN_ExtraProp_IsSend()
        };
    }



    public boolean insertGroup(ArrayList<SuggestModel> models)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try
        {
            db.beginTransaction();
            for (SuggestModel model : models)
            {
                ContentValues contentValues = modelToContentvalue(model);
                db.insertOrThrow(SuggestModel.TABLE_NAME , null , contentValues);
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
            String message = context.getResources().getString(R.string.errorGroupInsert , VarizBeBankModel.TABLE_NAME) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "insertGroup" , "");
            return false;
        }
    }

    public boolean insert(SuggestModel suggestModel)
    {
        try
        {
            ContentValues contentValues = modelToContentvalue(suggestModel);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insertOrThrow(SuggestModel.TABLE_NAME , null , contentValues);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorInsert , KalaMojodiModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "insert" , "");
            return false;
        }
    }

    public ArrayList<SuggestModel> getAll()
    {
        ArrayList<SuggestModel> models = new ArrayList<>();
        String query = "SELECT * FROM Suggest";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "getAll" , "");
        }
        return models;
    }
    public ArrayList<SuggestModel> getAllSuggestIsNotSend()
    {
        ArrayList<SuggestModel> models = new ArrayList<>();
        String query = "SELECT * FROM Suggest WHERE ExtraProp_IsSend = 0";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "getAll" , "");
        }
        return models;
    }

    public ArrayList<SuggestModel> getSuggest(int ccMoshtary)
    {
        ArrayList<SuggestModel> models = new ArrayList<>();
        String query = " Select s.ccSuggest , s.ccNoePishnehad,s.ccForoshandeh,s.ccMoshtary,s.Description,s.DescriptionPishnehad,s.Date,s.ExtraProp_IsSend,n.NameNoePishnehad From suggest s left join noepishnahad n where ccMoshtary = "+ ccMoshtary +" and s.ccNoePishnehad = n.ccNoePishnehad";

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    models = cursorToModelShow(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "getSuggest" , "");
        }
        return models;
    }


    /**
     * update is send when send is success
     * @param ccSuggest
     * @return
     */
    public boolean updateIsSend(int ccSuggest)
    {
        ContentValues values = new ContentValues();
        boolean update = false;


        try
        {
            values.put("ExtraProp_IsSend", 1);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.update(SuggestModel.TABLE_NAME, values, "ccSuggest = " + ccSuggest,null);
            update = true;
            db.close();
        }
        catch (Exception e)
        {
            update = false;
            throw new RuntimeException(e);
        }

        return update;
    }

    public boolean deleteAll()
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(SuggestModel.TABLE_NAME, null, null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , BargashtyModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "deleteAll" , "");
            return false;
        }
    }

    public boolean deleteByccSuggest(int ccSuggest)
    {
        String query = "DELETE FROM Suggest WHERE ccSuggest = " + ccSuggest + " AND ExtraProp_IsSend = 0";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "deleteByccSuggest" , "");
            return false;
        }
    }
    public boolean deleteIsSend()
    {
        String query = "DELETE FROM Suggest WHERE  ExtraProp_IsSend = 1";
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , AdamDarkhastModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "SuggestDAO" , "" , "deleteByccSuggest" , "");
            return false;
        }
    }

    private static ContentValues modelToContentvalue(SuggestModel model)
    {
        ContentValues contentValues = new ContentValues();


        contentValues.put(model.getCOLUMN_ccNoePishnehad() , model.getCcNoePishnehad());
        contentValues.put(model.getCOLUMN_ccForoshandeh() , model.getCcForoshandeh());
        contentValues.put(model.getCOLUMN_ccMoshtary() , model.getCcMoshtary());
        contentValues.put(model.getCOLUMN_ccAfrad() , model.getCcAfrad());
        contentValues.put(model.getCOLUMN_ccAmargar() , model.getCcAmargar());
        contentValues.put(model.getCOLUMN_ccMamorPakhsh() , model.getCcMamorPakhsh());
        contentValues.put(model.getCOLUMN_Description() , model.getDescription());
        contentValues.put(model.getCOLUMN_DescriptionPishnehad() , model.getDescriptionPishnehad());
        contentValues.put(model.getCOLUMN_Date() , model.getDate());
        contentValues.put(model.getCOLUMN_ExtraProp_IsSend(),model.getExtraProp_IsSend());



        return contentValues;
    }

    private ArrayList<SuggestModel> cursorToModel(Cursor cursor)
    {
        ArrayList<SuggestModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            SuggestModel model = new SuggestModel();

            model.setCcSuggest(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccSuggest())));
            model.setCcNoePishnehad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccNoePishnehad())));
            model.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccForoshandeh())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccMoshtary())));
            model.setCcAfrad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccAfrad())));
            model.setCcAmargar(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccAmargar())));
            model.setCcMamorPakhsh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccMamorPakhsh())));
            model.setDescription(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Description())));
            model.setDescriptionPishnehad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_DescriptionPishnehad())));
            model.setDate(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Date())));
            model.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_IsSend())));


            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }

    private ArrayList<SuggestModel> cursorToModelShow(Cursor cursor)
    {
        ArrayList<SuggestModel> models = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            SuggestModel model = new SuggestModel();

            model.setCcSuggest(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccSuggest())));
            model.setCcNoePishnehad(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccNoePishnehad())));
            model.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccForoshandeh())));
            model.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ccMoshtary())));
            model.setDescription(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Description())));
            model.setDescriptionPishnehad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_DescriptionPishnehad())));
            model.setDate(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_Date())));
            model.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_IsSend())));
            model.setExtraProp_NameNoePishnehad(cursor.getString(cursor.getColumnIndex(modelTABLE_NAME.getCOLUMN_ExtraProp_NameNoePishnehad())));


            models.add(model);
            cursor.moveToNext();
        }
        return models;
    }


}
