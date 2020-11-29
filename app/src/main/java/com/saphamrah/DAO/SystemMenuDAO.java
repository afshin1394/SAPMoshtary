package com.saphamrah.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class SystemMenuDAO
{
    private DBHelper dbHelper;
    private Context context;



    public SystemMenuDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "SystemMenuDAO" , "" , "constructor" , "");
        }
    }


    public ArrayList<SystemMenuModel> getMenu(int noeMasouliat)
    {
        String query = " select m.ccSystemMenu ,m.Name , m.Parent , m.IconName ,m.ccSystemMenuLinkType ,m.Link ,l.TypeName ,m.Sort\n" +
                " from systemMenu m " +
                " left join SystemMenuLinkType l on m.ccSystemMenuLinkType = l.ccSystemMenuLinkType\n" +
                " inner join SystemPermission p on m.ccSystemMenu = p.ccSystemMenu\n" +
                " where p.NoeForoshandehMamorPakhsh =  \n" + noeMasouliat +
                " order by Parent , m.Sort";

        ArrayList<SystemMenuModel> arrayListMenu = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                arrayListMenu = cursorToList(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "SystemMenuDAO" , "" , "getMenu" , "");
        }
        return arrayListMenu;
    }


    public ArrayList<SystemMenuModel> getMenuByParent(int noeForoshandehMamorPakhsh , String parentsId)
    {
        String query = "select m.ccSystemMenu, m.Name, m.Parent, m.IconName,\n" +
                "       m.ccSystemMenuLinkType,m.Link, l.TypeName,m.Sort\n" +
                "from SystemMenu m left join SystemMenuLinkType l on m.ccSystemMenuLinkType = l.ccSystemMenuLinkType\n" +
                "inner join SystemPermission p on m.ccSystemMenu = p.ccSystemMenu\n" +
                "where p.NoeForoshandehMamorPakhsh = " + noeForoshandehMamorPakhsh + " and m.Parent in ("+ parentsId +")\n" +
                "order by Parent, m.Sort";
        ArrayList<SystemMenuModel> arrayListMenu = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            //Log.d("query" , "query : " + query);
            if (cursor != null)
            {
                arrayListMenu = cursorToList(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "SystemMenuDAO" , "" , "getMenu" , "");
        }
        return arrayListMenu;
    }

    private ArrayList<SystemMenuModel> cursorToList(Cursor cursor)
    {
        ArrayList<SystemMenuModel> arrayListMenu = new ArrayList<>();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                SystemMenuModel systemMenuModel = new SystemMenuModel();
                systemMenuModel.setId(cursor.getInt(cursor.getColumnIndex(SystemMenuModel.COLUMN_ccSystemMenu())));
                systemMenuModel.setMenuName(cursor.getString(cursor.getColumnIndex(SystemMenuModel.COLUMN_Name())));
                systemMenuModel.setParent(cursor.getInt(cursor.getColumnIndex(SystemMenuModel.COLUMN_Parent())));
                systemMenuModel.setIconName(cursor.getString(cursor.getColumnIndex(SystemMenuModel.COLUMN_IconName())));
                systemMenuModel.setLink(cursor.getString(cursor.getColumnIndex(SystemMenuModel.COLUMN_Link())));
                systemMenuModel.setLinkTypeId(cursor.getInt(cursor.getColumnIndex(SystemMenuModel.COLUMN_ccSystemMenuLinkType())));
                systemMenuModel.setLinkTypeName(cursor.getString(cursor.getColumnIndex(SystemMenuModel.COLUMN_TypeName())));
                systemMenuModel.setSort(cursor.getInt(cursor.getColumnIndex(SystemMenuModel.COLUMN_Sort())));

                arrayListMenu.add(systemMenuModel);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return arrayListMenu;
    }



}
