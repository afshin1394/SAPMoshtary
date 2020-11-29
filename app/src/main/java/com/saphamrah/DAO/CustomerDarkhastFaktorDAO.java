package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.CustomerDarkhastFaktorModel;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class CustomerDarkhastFaktorDAO
{

    private DBHelper dbHelper;
    private Context context;



    public CustomerDarkhastFaktorDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "CustomerDarkhastFaktorDAO" , "" , "constructor" , "");
        }
    }

    /*private String[] allColumns()
    {
        return new String[]
        {
            DarkhastFaktorModel.COLUMN_ccDarkhastFaktor(),
            DarkhastFaktorModel.COLUMN_TarikhFaktor(),
            DarkhastFaktorModel.COLUMN_ShomarehDarkhast(),
            DarkhastFaktorModel.COLUMN_ShomarehFaktor(),
            DarkhastFaktorModel.COLUMN_ccForoshandeh(),
            DarkhastFaktorModel.COLUMN_ccMoshtary(),
            DarkhastFaktorModel.COLUMN_TarikhErsal(),
            DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor(),
            DarkhastFaktorModel.COLUMN_CodeVazeiat(),
            DarkhastFaktorModel.COLUMN_ExtraProp_IsOld(),
            DarkhastFaktorModel.COLUMN_ExtraProp_IsSend(),
            MoshtaryModel.COLUMN_NameMoshtary(),
            MoshtaryModel.COLUMN_CodeMoshtary()
        };
    }*/


    public ArrayList<CustomerDarkhastFaktorModel> getAllDarkhastFaktor()
    {
        ArrayList<CustomerDarkhastFaktorModel> customerDarkhastFaktorModels = new ArrayList<>();
        String query = "select dm.* , Have_FaktorImage , LENGTH(de.EmzaImage) as HaveEmzaImage  \n" +
                " from (select d.ccDarkhastFaktor, d.TarikhFaktor , d.ShomarehDarkhast, d.ShomarehFaktor, d.ccForoshandeh, \n" +
                " d.ccMoshtary, d.TarikhErsal, d.MablaghKhalesFaktor, d.CodeVazeiat, d.ExtraProp_IsOld, \n" +
                " d.ExtraProp_IsSend, d.UniqID_Tablet, m.NameMoshtary , m.CodeMoshtary \n" +
                " from darkhastfaktor d inner join moshtary m on d.ccMoshtary = m.ccMoshtary \n" +
                " where d.ShomarehFaktor = 0 and d.ExtraProp_InsertInPPC = 1 order by d.TarikhFaktor) dm \n" +
                " left join DarkhastFaktor_EmzaMoshtary de \n" +
                " on dm.ccDarkhastFaktor = de.ccDarkhastFaktor";
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    customerDarkhastFaktorModels = cursorToModel(cursor);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , DarkhastFaktorModel.TableName() + "," + MoshtaryModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), message, "CustomerDarkhastFaktorDAO" , "" , "getAll" , "");
        }
        return customerDarkhastFaktorModels;
    }


    private ArrayList<CustomerDarkhastFaktorModel> cursorToModel(Cursor cursor)
    {
        ArrayList<CustomerDarkhastFaktorModel> customerDarkhastFaktorModels = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            CustomerDarkhastFaktorModel customerDarkhastFaktorModel = new CustomerDarkhastFaktorModel();

            customerDarkhastFaktorModel.setCcDarkhastFaktor(cursor.getLong(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccDarkhastFaktor())));
            customerDarkhastFaktorModel.setTarikhFaktor(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TarikhFaktor())));
            customerDarkhastFaktorModel.setShomarehDarkhast(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ShomarehDarkhast())));
            customerDarkhastFaktorModel.setShomarehFaktor(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ShomarehFaktor())));
            customerDarkhastFaktorModel.setCcForoshandeh(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccForoshandeh())));
            customerDarkhastFaktorModel.setCcMoshtary(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ccMoshtary())));
            customerDarkhastFaktorModel.setTarikhErsal(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_TarikhErsal())));
            customerDarkhastFaktorModel.setMablaghKhalesFaktor(cursor.getDouble(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_MablaghKhalesFaktor())));
            customerDarkhastFaktorModel.setCodeVazeiat(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_CodeVazeiat())));
            customerDarkhastFaktorModel.setExtraProp_IsOld(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_IsOld())));
            customerDarkhastFaktorModel.setExtraProp_IsSend(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_ExtraProp_IsSend())));
            customerDarkhastFaktorModel.setUniqID_Tablet(cursor.getString(cursor.getColumnIndex(DarkhastFaktorModel.COLUMN_UniqID_Tablet())));
            customerDarkhastFaktorModel.setFullNameMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_NameMoshtary())));
            customerDarkhastFaktorModel.setCodeMoshtary(cursor.getString(cursor.getColumnIndex(MoshtaryModel.COLUMN_CodeMoshtary())));
            customerDarkhastFaktorModel.setHaveEmzaImage(cursor.getInt(cursor.getColumnIndex("HaveEmzaImage")) > 0);
            customerDarkhastFaktorModel.setHaveFaktorImage(cursor.getInt(cursor.getColumnIndex(DarkhastFaktorEmzaMoshtaryModel.COLUMN_Have_FaktorImage())) > 0);

            customerDarkhastFaktorModels.add(customerDarkhastFaktorModel);
            cursor.moveToNext();
        }
        return customerDarkhastFaktorModels;
    }



}
