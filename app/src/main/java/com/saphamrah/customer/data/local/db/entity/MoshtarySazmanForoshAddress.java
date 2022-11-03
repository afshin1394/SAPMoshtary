package com.saphamrah.customer.data.local.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MoshtarySazmanForoshAddress")
public class MoshtarySazmanForoshAddress {

    @PrimaryKey
    @ColumnInfo(name = "ccMoshtarySazmanForoshAddress")
    public int ccMoshtarySazmanForoshAddress;

    @ColumnInfo(name = "ccMoshtarySazmanForosh")
    public int ccMoshtarySazmanForosh;

    @ColumnInfo(name = "ccMoshtaryAddress")
    public int ccMoshtaryAddress;

    @ColumnInfo(name = "ccAddress")
    public int ccAddress;

    @ColumnInfo(name = "codeNoeAddress")
    public int codeNoeAddress;

    @ColumnInfo(name = "noeAddressTitle")
    public int noeAddressTitle;

    @ColumnInfo(name = "ccMahal")
    public int ccMahal;

    @ColumnInfo(name = "ccMantaghe")
    public int ccMantaghe;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "khiabanAsli")
    public String khiabanAsli;

    @ColumnInfo(name = "khiabanFarei1")
    public String khiabanFarei1;

    @ColumnInfo(name = "khiabanFarei2")
    public String khiabanFarei2;

    @ColumnInfo(name = "koocheAsli")
    public String koocheAsli;

    @ColumnInfo(name = "koocheFarei1")
    public String koocheFarei1;

    @ColumnInfo(name = "koocheFarei2")
    public String koocheFarei2;

    @ColumnInfo(name = "pelak")
    public int pelak;

    @ColumnInfo(name = "codePosty")
    public int codePosty;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

}
