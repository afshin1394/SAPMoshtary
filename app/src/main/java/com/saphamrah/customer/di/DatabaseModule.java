/*
package com.saphamrah.customer.di;

import androidx.room.Room;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.data.local.db.SapDatabase;
import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {
    @Provides
    @Singleton
    SapDatabase provideRoomDatabase(Application application) {
        return Room
                .databaseBuilder(application, SapDatabase.class, "sap_moshtarian.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    BankDao provideBankDao(SapDatabase db) {
        return db.bankDao();
    }

    @Singleton
    @Provides
    CompanyDao provideCompanyDao(SapDatabase db) {
        return db.companyDao();
    }

    @Singleton
    @Provides
    MoshtarySazmanForoshAddressDao provideMoshtarySazmanForoshAddressDao(SapDatabase db) {
        return db.moshtarySazmanForoshAddressDao();
    }
}
*/
