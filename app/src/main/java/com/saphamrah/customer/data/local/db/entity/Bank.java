package com.saphamrah.customer.data.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bank")
public class Bank {

    @PrimaryKey
    @ColumnInfo(name = "ccBank")
    int ccBank;


    @ColumnInfo(name = "bankTitle")
    String bankTitle;

    /*@JvmField
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray*/
}
