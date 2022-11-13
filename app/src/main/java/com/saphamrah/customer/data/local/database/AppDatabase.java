package com.saphamrah.customer.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.saphamrah.customer.data.local.database.dao.MoshtaryDao;
import com.saphamrah.customer.data.local.database.dao.MoshtarySazmanForoshDao;
import com.saphamrah.customer.data.local.database.dao.MoshtaryShomarehHesabDao;
import com.saphamrah.customer.data.local.database.entity.MoshtarySazmanForoshTable;
import com.saphamrah.customer.data.local.database.entity.MoshtaryShomarehHesabTable;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;

@Database(entities = {MoshtaryTable.class, MoshtarySazmanForoshTable.class, MoshtaryShomarehHesabTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_name = "app_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    DB_name
            ).build();
        }
        return instance;
    }

    public abstract MoshtaryDao moshtaryDao();
    public abstract MoshtarySazmanForoshDao moshtarySazmanForoshDao();
    public abstract MoshtaryShomarehHesabDao moshtaryShomarehHesabDao();
}