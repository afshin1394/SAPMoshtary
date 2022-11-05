package com.saphamrah.customer.data.local.db.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Bank")
data class Bank (
    @PrimaryKey
    @ColumnInfo(name = "ccBank")
    var ccBank: Int = 0,

    @JvmField
    @ColumnInfo(name = "bankTitle")
    var bankTitle: String? = null,

    /*@JvmField
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    var image: ByteArray*/
)