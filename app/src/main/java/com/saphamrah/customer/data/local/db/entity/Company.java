package com.saphamrah.customer.data.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Company")
public class Company {
    @PrimaryKey
    @ColumnInfo(name = "ccCompany")
    public int ccCompany;

    @ColumnInfo(name = "companyTitle")
    public String companyTitle;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;
}
