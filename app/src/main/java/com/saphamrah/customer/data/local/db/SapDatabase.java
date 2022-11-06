package com.saphamrah.customer.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;
import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.entity.Company;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Company.class, Bank.class, MoshtarySazmanForoshAddress.class}, version = 1)
public abstract class SapDatabase extends RoomDatabase {

    public static volatile SapDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract CompanyDao companyDao();
    public abstract BankDao bankDao();
    public abstract MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao();


    public static SapDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SapDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SapDatabase.class, "sap_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
