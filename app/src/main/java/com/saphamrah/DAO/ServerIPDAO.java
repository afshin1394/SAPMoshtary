package com.saphamrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.saphamrah.Model.ServerIpModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.util.ArrayList;

public class ServerIPDAO
{

    private DBHelper dbHelper;
    private Context context;



    public ServerIPDAO(Context context)
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
            logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "ServerIPDAO" , "" , "constructor" , "");
        }
    }

    private String[] allColumns()
    {
        return new String[]
        {
            ServerIpModel.COLUMN_ccServerIP(),
            ServerIpModel.COLUMN_ServerIP(),
            ServerIpModel.COLUMN_PortServerIP(),
            ServerIpModel.COLUMN_NameServerIP(),
            ServerIpModel.COLUMN_IsTest(),
            ServerIpModel.COLUMN_Server_Type(),
            ServerIpModel.COLUMN_NameServerPersian()
        };
    }

    public boolean insert(ServerIpModel serverIpModel)
    {
        boolean result = false;
        ContentValues value = modelToContentValue(serverIpModel);
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(ServerIpModel.TableName() , null , value);
            result = true;
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorGroupInsert , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ServerIPDAO" , "" , "insert" , "");
            result = false;
        }
        return result;
    }


    public ArrayList<ServerIpModel> getAll()
    {
        //String query = "select * from " + ServerIpModel.TableName();
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(ServerIpModel.TableName(), allColumns(), null, null, null, null, null);
            if (cursor != null)
            {
                arrayListServerIPs = cursorToList(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ServerIPDAO" , "" , "getAllServerIP" , "");
        }
        return arrayListServerIPs;
    }

    public ArrayList<ServerIpModel> getAllServerIPOrderByPort()
    {
        //String query = "select * from " + ServerIpModel.TableName() + " order by " + COLUMN_PORT_SERVER_IP;
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //Cursor cursor = db.rawQuery(query , null);
            Cursor cursor = db.query(ServerIpModel.TableName(),null,null,null,null,null, ServerIpModel.COLUMN_PortServerIP());
            if (cursor != null)
            {
                arrayListServerIPs = cursorToList(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ServerIPDAO" , "" , "getAllServerIPOrderByPort" , "");
        }
        return arrayListServerIPs;
    }

    public int getCountServerType(int server)
    {
        int count=0;
        String query = "select count(distinct servertype) from serverip where istest= " + server;

        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
            if (cursor != null)
            {
                if (cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ServerIPDAO" , "" , "getCountServerType" , "");
        }
        return count;
    }


    public ArrayList<ServerIpModel> getServerIPwithIsTestFilter(int filter)
    {
        String query = "SELECT DISTINCT ServerIP , NameServerPersian FROM " + ServerIpModel.TableName() + " WHERE " + ServerIpModel.COLUMN_IsTest() + " = " + filter ;
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
//            Cursor cursor = db.query(ServerIpModel.TableName(), allColumns(), ServerIpModel.COLUMN_IsTest() + " = " + filter , null , null, null, null, null);
            if (cursor != null)
            {
                arrayListServerIPs = cursorToListServer(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(),message + "\n serverIPs count : " + arrayListServerIPs.size(), "ServerIPDAO" , "" , "getServerIPwithIsTestFilter" , "");
        }
        return arrayListServerIPs;
    }

    public ArrayList<ServerIpModel> getServerISelected(int filter , String ip)
    {
        String query = "SELECT DISTINCT * FROM " + ServerIpModel.TableName() + " WHERE " + ServerIpModel.COLUMN_ServerIP() + " = '" + ip + "' AND " + ServerIpModel.COLUMN_IsTest() + " = " + filter;
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        try
        {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(query , null);
//            Cursor cursor = db.query(ServerIpModel.TableName(), allColumns(), ServerIpModel.COLUMN_IsTest() + " = " + filter , null , null, null, null, null);
            if (cursor != null)
            {
                arrayListServerIPs = cursorToList(cursor);
                cursor.close();
            }
            db.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorSelectAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(),message + "\n serverIPs count : " + arrayListServerIPs.size(), "ServerIPDAO" , "" , "getServerIPwithIsTestFilter" , "");
        }
        return arrayListServerIPs;
    }

    public boolean deleteAll()
    {
        String query = "delete from " + ServerIpModel.TableName();
        boolean result = false;
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(query);
            db.close();
            result = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            String message = context.getResources().getString(R.string.errorDeleteAll , ServerIpModel.TableName()) + "\n" + exception.toString();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), message, "ServerIPDAO" , "" , "deleteAllServerIP" , "");
        }
        return result;
    }

    public boolean disable(int serverIPid)
    {
        try
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues value = new ContentValues();
            value.put(ServerIpModel.COLUMN_IsTest() , 1);
            db.update(ServerIpModel.TableName(), value, ServerIpModel.COLUMN_ccServerIP() + "=" + serverIPid , null);
            db.close();
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "ServerIPDAO" , "" , "disableServerIP" , "");
            return false;
        }
    }


    private ContentValues modelToContentValue(ServerIpModel model)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ServerIpModel.COLUMN_ccServerIP() , model.getId());
        contentValues.put(ServerIpModel.COLUMN_ServerIP() , model.getServerIp());
        contentValues.put(ServerIpModel.COLUMN_PortServerIP() , model.getPort());
        contentValues.put(ServerIpModel.COLUMN_NameServerIP() , model.getLocal());
        contentValues.put(ServerIpModel.COLUMN_IsTest() , model.getTest());
        contentValues.put(ServerIpModel.COLUMN_Server_Type() , model.getServerType());
        contentValues.put(ServerIpModel.COLUMN_NameServerPersian() , model.getNameServerPersian());

        return contentValues;
    }

    private ArrayList<ServerIpModel> cursorToList(Cursor cursor)
    {
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ServerIpModel serverIpModel = new ServerIpModel();
            serverIpModel.setId(cursor.getInt(cursor.getColumnIndex(ServerIpModel.COLUMN_ccServerIP())));
            serverIpModel.setServerIp(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_ServerIP())));
            serverIpModel.setPort(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_PortServerIP())));
            serverIpModel.setLocal(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_NameServerIP())));
            serverIpModel.setTest(cursor.getInt(cursor.getColumnIndex(ServerIpModel.COLUMN_IsTest())));
            serverIpModel.setServerType(cursor.getInt(cursor.getColumnIndex(ServerIpModel.COLUMN_Server_Type())));
            serverIpModel.setNameServerPersian(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_NameServerPersian())));

            arrayListServerIPs.add(serverIpModel);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayListServerIPs;
    }

    private ArrayList<ServerIpModel> cursorToListServer(Cursor cursor)
    {
        ArrayList<ServerIpModel> arrayListServerIPs = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            ServerIpModel serverIpModel = new ServerIpModel();
            serverIpModel.setServerIp(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_ServerIP())));
            serverIpModel.setNameServerPersian(cursor.getString(cursor.getColumnIndex(ServerIpModel.COLUMN_NameServerPersian())));

            arrayListServerIPs.add(serverIpModel);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayListServerIPs;
    }


}
