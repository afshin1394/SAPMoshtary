package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.saphamrah.Model.AdamDarkhastModel;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerAdamDarkhastModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CustomerAdamDarkhastDAO
{

    private DBHelper dbHelper;
    private Context context;



    public CustomerAdamDarkhastDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CustomerAdamDarkhastDAO" , "" , "constructor" , "");
        }
    }

    /*private String[] allColumns()
    {
        return new String[]
        {
            AdamDarkhastModel.COLUMN_ccAdamDarkhast(),
            AdamDarkhastModel.COLUMN_ccForoshandeh(),
            AdamDarkhastModel.COLUMN_ccMoshtary(),
            AdamDarkhastModel.COLUMN_ccElatAdamDarkhast(),
            AdamDarkhastModel.COLUMN_TarikhAdamDarkhast(),
            AdamDarkhastModel.COLUMN_Latitude(),
            AdamDarkhastModel.COLUMN_Longitude(),
            AdamDarkhastModel.COLUMN_IsSentToServer(),
            AdamDarkhastModel.COLUMN_AdamDarkhastImage(),
            AdamDarkhastModel.COLUMN_CodeMoshtaryTekrari(),
            MoshtaryModel.COLUMN_CodeMoshtary(),
            MoshtaryModel.COLUMN_NameMoshtary(),
            ElatAdamDarkhastModel.COLUMN_NameElatAdamDarkhast()
        };
    }*/


    public ArrayList<CustomerAdamDarkhastModel> getAllForSendToServer()
    {
        ArrayList<CustomerAdamDarkhastModel> customerAdamDarkhastModels = new ArrayList<>();
        try
        {
            /*String query = "select a.ccAdamDarkhast AS ccAdamDarkhast, a.ccForoshandeh AS ccForoshandeh, \n" +
                    " a.ccMoshtary AS ccMoshtary, a.ccElatAdamDarkhast AS ccElatAdamDarkhast, \n" +
                    " a.TarikhAdamDarkhast AS TarikhAdamDarkhast, a.Latitude AS Latitude, \n" +
                    " a.Longitude AS Longitude, a.IsSentToServer AS IsSentToServer, \n" +
                    " a.AdamDarkhastImage AS AdamDarkhastImage, a.CodeMoshtaryTekrari AS CodeMoshtaryTekrari, \n" +
                    " e.NameElatAdamDarkhast , m.NameMoshtary , m.CodeMoshtary \n" +
                    " from AdamDarkhast a inner join Moshtary m on a.ccMoshtary = m.ccMoshtary \n" +
                    " inner join ElatAdamDarkhast e on e.ccElatAdamDarkhast = a.ccElatAdamDarkhast \n" +
                    " where a.IsSentToServer = 0 group by a.ccAdamDarkhast";*/
            String query = "select a.*,e.NameElatAdamDarkhast , m.NameMoshtary , m.CodeMoshtary \n" +
                    " from AdamDarkhast a inner join Moshtary m on a.ccMoshtary = m.ccMoshtary \n" +
                    " inner join ElatAdamDarkhast e on e.ccElatAdamDarkhast = a.ccElatAdamDarkhast \n" +
                    " group by a.ccAdamDarkhast";
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerAdamDarkhastModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , "AdamDarkhast,Moshtary,ElatAdamDarkhast") + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CustomerAdamDarkhastDAO" , "" , "getAllForSendToServer" , "");
        }
        return customerAdamDarkhastModels;
    }



    private ArrayList<CustomerAdamDarkhastModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CustomerAdamDarkhastModel> customerAdamDarkhastModels = new ArrayList<>();

        String[] columns = cursor.getColumnNames();
        for (String name : columns)
        {
            Log.d("columnAdam" , "column : " + name);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CustomerAdamDarkhastModel customerAdamDarkhastModel = new CustomerAdamDarkhastModel();

            customerAdamDarkhastModel.setCcAdamDarkhast(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccAdamDarkhast())));
            customerAdamDarkhastModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccForoshandeh())));
            customerAdamDarkhastModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccMoshtary())));
            customerAdamDarkhastModel.setCcElatAdamDarkhast(cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_ccElatAdamDarkhast())));
            customerAdamDarkhastModel.setTarikhAdamDarkhast(cursor.getString(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_TarikhAdamDarkhast())));
            customerAdamDarkhastModel.setLatitude(cursor.getFloat(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_Latitude())));
            customerAdamDarkhastModel.setLongitude(cursor.getFloat(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_Longitude())));
            customerAdamDarkhastModel.setSentToServer((cursor.getInt(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_IsSentToServer())))==1);
            customerAdamDarkhastModel.setAdamDarkhastImage(cursor.getBlob(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_AdamDarkhastImage())));
            customerAdamDarkhastModel.setCodeMoshtaryTekrari(cursor.getString(cursor.getColumnIndex(AdamDarkhastModel.COLUMN_CodeMoshtaryTekrari())));
            customerAdamDarkhastModel.setNameElatAdamDarkhast(cursor.getString(cursor.getColumnIndex(ElatAdamDarkhastModel.COLUMN_NameElatAdamDarkhast())));
            customerAdamDarkhastModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameMoshtary())));
            customerAdamDarkhastModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMoshtary())));

            /*customerAdamDarkhastModel.setCcAdamDarkhast(cursor.getInt(cursor.getColumnIndex("ccAdamDarkhast")));
            customerAdamDarkhastModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex("ccForoshandeh")));
            customerAdamDarkhastModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex("ccMoshtary")));
            customerAdamDarkhastModel.setCcElatAdamDarkhast(cursor.getInt(cursor.getColumnIndex("ccElatAdamDarkhast")));
            customerAdamDarkhastModel.setTarikhAdamDarkhast(cursor.getString(cursor.getColumnIndex("TarikhAdamDarkhast")));
            customerAdamDarkhastModel.setLatitude(cursor.getFloat(cursor.getColumnIndex("Latitude")));
            customerAdamDarkhastModel.setLongitude(cursor.getFloat(cursor.getColumnIndex("Longitude")));
            customerAdamDarkhastModel.setSentToServer((cursor.getInt(cursor.getColumnIndex("IsSentToServer")))==1);
            customerAdamDarkhastModel.setAdamDarkhastImage(cursor.getBlob(cursor.getColumnIndex("AdamDarkhastImage")));
            customerAdamDarkhastModel.setCodeMoshtaryTekrari(cursor.getString(cursor.getColumnIndex("CodeMoshtaryTekrari")));
            customerAdamDarkhastModel.setNameElatAdamDarkhast(cursor.getString(cursor.getColumnIndex("NameElatAdamDarkhast")));
            customerAdamDarkhastModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex("NameMoshtary")));
            customerAdamDarkhastModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex("CodeMoshtary")));*/

            customerAdamDarkhastModels.add(customerAdamDarkhastModel);
            cursor.moveToNext();
        }
        return customerAdamDarkhastModels;
    }


}
