package com.saphamrah.customer.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.entity.Bank;
import com.saphamrah.customer.data.local.db.entity.Company;

@Database(entities = {Company.class, Bank.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CompanyDao companyDao();
    public abstract BankDao bankDao();

}
