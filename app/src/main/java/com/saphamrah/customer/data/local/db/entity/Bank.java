package com.saphamrah.customer.data.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bank")
public class Bank {

    public Bank(int ccBank, String bankTitle) {
        this.ccBank = ccBank;
        this.bankTitle = bankTitle;
    }

    @PrimaryKey
    @ColumnInfo(name = "ccBank")
    public int ccBank;


    @ColumnInfo(name = "bankTitle")
    public String bankTitle;

    /*@JvmField
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray*/
}
