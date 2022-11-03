package com.saphamrah.customer.data.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bank")
public class Bank {
    @PrimaryKey
    @ColumnInfo(name = "ccBank")
    public int ccBank;

    @ColumnInfo(name = "bankTitle")
    public String bankTitle;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;
}
