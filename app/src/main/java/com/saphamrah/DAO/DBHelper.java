package com.saphamrah.DAO;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.saphamrah.PubFunc.PubFunc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper
{

    private static String DB_NAME = "DataBase.sqlite";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 89;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;


    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;

        DB_PATH = mContext.getApplicationInfo().dataDir + "/databases/";

        boolean dbexist = checkDataBase();
        if (dbexist)
        {
            openDataBase();
        }
        else
        {
            createDataBase();
        }
    }

    private void createDataBase()
    {
        boolean dbexist = checkDataBase();
        if (!dbexist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.sendLogToServer(mContext, e.toString(), "DBHelper" , "" , "createDataBase" , "copyDataBase");
            }
        }
    }

    private boolean checkDataBase()
    {
        boolean checkdb = false;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.sendLogToServer(mContext, e.toString() + "\n dbFile address : " + DB_PATH + DB_NAME, "DBHelper" , "" , "checkDataBase" , "");
        }
        return checkdb;
    }

    private void copyDataBase() throws IOException
    {
        InputStream myinput = mContext.getAssets().open("G2DcfxJZdu/" + DB_NAME);
        OutputStream myoutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0)
        {
            myoutput.write(buffer, 0, length);
        }
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private void openDataBase() throws SQLException
    {
        String mypath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close()
    {
        if (mDataBase != null)
        {
            mDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion > oldVersion)
        {
            mNeedUpdate = true;
            try
            {
                updateDataBase();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }


    private void updateDataBase() throws IOException
    {
        if (mNeedUpdate)
        {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
            {
                dbFile.delete();
            }

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }



}
