package com.saphamrah.customer.data.local.db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress;
import io.reactivex.Observable;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MoshtarySazmanForoshAddressDao_Impl extends MoshtarySazmanForoshAddressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MoshtarySazmanForoshAddress> __insertionAdapterOfMoshtarySazmanForoshAddress;

  private final EntityDeletionOrUpdateAdapter<MoshtarySazmanForoshAddress> __deletionAdapterOfMoshtarySazmanForoshAddress;

  private final EntityDeletionOrUpdateAdapter<MoshtarySazmanForoshAddress> __updateAdapterOfMoshtarySazmanForoshAddress;

  public MoshtarySazmanForoshAddressDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMoshtarySazmanForoshAddress = new EntityInsertionAdapter<MoshtarySazmanForoshAddress>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MoshtarySazmanForoshAddress` (`ccMoshtarySazmanForoshAddress`,`ccMoshtarySazmanForosh`,`ccMoshtaryAddress`,`ccAddress`,`codeNoeAddress`,`noeAddressTitle`,`ccMahal`,`ccMantaghe`,`address`,`khiabanAsli`,`khiabanFarei1`,`khiabanFarei2`,`koocheAsli`,`koocheFarei1`,`koocheFarei2`,`pelak`,`codePosty`,`latitude`,`longitude`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MoshtarySazmanForoshAddress value) {
        stmt.bindLong(1, value.ccMoshtarySazmanForoshAddress);
        stmt.bindLong(2, value.ccMoshtarySazmanForosh);
        stmt.bindLong(3, value.ccMoshtaryAddress);
        stmt.bindLong(4, value.ccAddress);
        stmt.bindLong(5, value.codeNoeAddress);
        stmt.bindLong(6, value.noeAddressTitle);
        stmt.bindLong(7, value.ccMahal);
        stmt.bindLong(8, value.ccMantaghe);
        if (value.address == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.address);
        }
        if (value.khiabanAsli == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.khiabanAsli);
        }
        if (value.khiabanFarei1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.khiabanFarei1);
        }
        if (value.khiabanFarei2 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.khiabanFarei2);
        }
        if (value.koocheAsli == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.koocheAsli);
        }
        if (value.koocheFarei1 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.koocheFarei1);
        }
        if (value.koocheFarei2 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.koocheFarei2);
        }
        stmt.bindLong(16, value.pelak);
        stmt.bindLong(17, value.codePosty);
        stmt.bindDouble(18, value.latitude);
        stmt.bindDouble(19, value.longitude);
      }
    };
    this.__deletionAdapterOfMoshtarySazmanForoshAddress = new EntityDeletionOrUpdateAdapter<MoshtarySazmanForoshAddress>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MoshtarySazmanForoshAddress` WHERE `ccMoshtarySazmanForoshAddress` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MoshtarySazmanForoshAddress value) {
        stmt.bindLong(1, value.ccMoshtarySazmanForoshAddress);
      }
    };
    this.__updateAdapterOfMoshtarySazmanForoshAddress = new EntityDeletionOrUpdateAdapter<MoshtarySazmanForoshAddress>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `MoshtarySazmanForoshAddress` SET `ccMoshtarySazmanForoshAddress` = ?,`ccMoshtarySazmanForosh` = ?,`ccMoshtaryAddress` = ?,`ccAddress` = ?,`codeNoeAddress` = ?,`noeAddressTitle` = ?,`ccMahal` = ?,`ccMantaghe` = ?,`address` = ?,`khiabanAsli` = ?,`khiabanFarei1` = ?,`khiabanFarei2` = ?,`koocheAsli` = ?,`koocheFarei1` = ?,`koocheFarei2` = ?,`pelak` = ?,`codePosty` = ?,`latitude` = ?,`longitude` = ? WHERE `ccMoshtarySazmanForoshAddress` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MoshtarySazmanForoshAddress value) {
        stmt.bindLong(1, value.ccMoshtarySazmanForoshAddress);
        stmt.bindLong(2, value.ccMoshtarySazmanForosh);
        stmt.bindLong(3, value.ccMoshtaryAddress);
        stmt.bindLong(4, value.ccAddress);
        stmt.bindLong(5, value.codeNoeAddress);
        stmt.bindLong(6, value.noeAddressTitle);
        stmt.bindLong(7, value.ccMahal);
        stmt.bindLong(8, value.ccMantaghe);
        if (value.address == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.address);
        }
        if (value.khiabanAsli == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.khiabanAsli);
        }
        if (value.khiabanFarei1 == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.khiabanFarei1);
        }
        if (value.khiabanFarei2 == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.khiabanFarei2);
        }
        if (value.koocheAsli == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.koocheAsli);
        }
        if (value.koocheFarei1 == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.koocheFarei1);
        }
        if (value.koocheFarei2 == null) {
          stmt.bindNull(15);
        } else {
          stmt.bindString(15, value.koocheFarei2);
        }
        stmt.bindLong(16, value.pelak);
        stmt.bindLong(17, value.codePosty);
        stmt.bindDouble(18, value.latitude);
        stmt.bindDouble(19, value.longitude);
        stmt.bindLong(20, value.ccMoshtarySazmanForoshAddress);
      }
    };
  }

  @Override
  public void insert(final MoshtarySazmanForoshAddress entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMoshtarySazmanForoshAddress.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final List<MoshtarySazmanForoshAddress> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMoshtarySazmanForoshAddress.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final MoshtarySazmanForoshAddress entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMoshtarySazmanForoshAddress.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final List<MoshtarySazmanForoshAddress> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfMoshtarySazmanForoshAddress.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final MoshtarySazmanForoshAddress entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMoshtarySazmanForoshAddress.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final List<MoshtarySazmanForoshAddress> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMoshtarySazmanForoshAddress.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Observable<List<MoshtarySazmanForoshAddress>> getAll() {
    final String _sql = "SELECT `moshtarysazmanforoshaddress`.`ccMoshtarySazmanForoshAddress` AS `ccMoshtarySazmanForoshAddress`, `moshtarysazmanforoshaddress`.`ccMoshtarySazmanForosh` AS `ccMoshtarySazmanForosh`, `moshtarysazmanforoshaddress`.`ccMoshtaryAddress` AS `ccMoshtaryAddress`, `moshtarysazmanforoshaddress`.`ccAddress` AS `ccAddress`, `moshtarysazmanforoshaddress`.`codeNoeAddress` AS `codeNoeAddress`, `moshtarysazmanforoshaddress`.`noeAddressTitle` AS `noeAddressTitle`, `moshtarysazmanforoshaddress`.`ccMahal` AS `ccMahal`, `moshtarysazmanforoshaddress`.`ccMantaghe` AS `ccMantaghe`, `moshtarysazmanforoshaddress`.`address` AS `address`, `moshtarysazmanforoshaddress`.`khiabanAsli` AS `khiabanAsli`, `moshtarysazmanforoshaddress`.`khiabanFarei1` AS `khiabanFarei1`, `moshtarysazmanforoshaddress`.`khiabanFarei2` AS `khiabanFarei2`, `moshtarysazmanforoshaddress`.`koocheAsli` AS `koocheAsli`, `moshtarysazmanforoshaddress`.`koocheFarei1` AS `koocheFarei1`, `moshtarysazmanforoshaddress`.`koocheFarei2` AS `koocheFarei2`, `moshtarysazmanforoshaddress`.`pelak` AS `pelak`, `moshtarysazmanforoshaddress`.`codePosty` AS `codePosty`, `moshtarysazmanforoshaddress`.`latitude` AS `latitude`, `moshtarysazmanforoshaddress`.`longitude` AS `longitude` FROM moshtarysazmanforoshaddress";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createObservable(__db, false, new String[]{"moshtarysazmanforoshaddress"}, new Callable<List<MoshtarySazmanForoshAddress>>() {
      @Override
      public List<MoshtarySazmanForoshAddress> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCcMoshtarySazmanForoshAddress = 0;
          final int _cursorIndexOfCcMoshtarySazmanForosh = 1;
          final int _cursorIndexOfCcMoshtaryAddress = 2;
          final int _cursorIndexOfCcAddress = 3;
          final int _cursorIndexOfCodeNoeAddress = 4;
          final int _cursorIndexOfNoeAddressTitle = 5;
          final int _cursorIndexOfCcMahal = 6;
          final int _cursorIndexOfCcMantaghe = 7;
          final int _cursorIndexOfAddress = 8;
          final int _cursorIndexOfKhiabanAsli = 9;
          final int _cursorIndexOfKhiabanFarei1 = 10;
          final int _cursorIndexOfKhiabanFarei2 = 11;
          final int _cursorIndexOfKoocheAsli = 12;
          final int _cursorIndexOfKoocheFarei1 = 13;
          final int _cursorIndexOfKoocheFarei2 = 14;
          final int _cursorIndexOfPelak = 15;
          final int _cursorIndexOfCodePosty = 16;
          final int _cursorIndexOfLatitude = 17;
          final int _cursorIndexOfLongitude = 18;
          final List<MoshtarySazmanForoshAddress> _result = new ArrayList<MoshtarySazmanForoshAddress>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MoshtarySazmanForoshAddress _item;
            _item = new MoshtarySazmanForoshAddress();
            _item.ccMoshtarySazmanForoshAddress = _cursor.getInt(_cursorIndexOfCcMoshtarySazmanForoshAddress);
            _item.ccMoshtarySazmanForosh = _cursor.getInt(_cursorIndexOfCcMoshtarySazmanForosh);
            _item.ccMoshtaryAddress = _cursor.getInt(_cursorIndexOfCcMoshtaryAddress);
            _item.ccAddress = _cursor.getInt(_cursorIndexOfCcAddress);
            _item.codeNoeAddress = _cursor.getInt(_cursorIndexOfCodeNoeAddress);
            _item.noeAddressTitle = _cursor.getInt(_cursorIndexOfNoeAddressTitle);
            _item.ccMahal = _cursor.getInt(_cursorIndexOfCcMahal);
            _item.ccMantaghe = _cursor.getInt(_cursorIndexOfCcMantaghe);
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _item.address = null;
            } else {
              _item.address = _cursor.getString(_cursorIndexOfAddress);
            }
            if (_cursor.isNull(_cursorIndexOfKhiabanAsli)) {
              _item.khiabanAsli = null;
            } else {
              _item.khiabanAsli = _cursor.getString(_cursorIndexOfKhiabanAsli);
            }
            if (_cursor.isNull(_cursorIndexOfKhiabanFarei1)) {
              _item.khiabanFarei1 = null;
            } else {
              _item.khiabanFarei1 = _cursor.getString(_cursorIndexOfKhiabanFarei1);
            }
            if (_cursor.isNull(_cursorIndexOfKhiabanFarei2)) {
              _item.khiabanFarei2 = null;
            } else {
              _item.khiabanFarei2 = _cursor.getString(_cursorIndexOfKhiabanFarei2);
            }
            if (_cursor.isNull(_cursorIndexOfKoocheAsli)) {
              _item.koocheAsli = null;
            } else {
              _item.koocheAsli = _cursor.getString(_cursorIndexOfKoocheAsli);
            }
            if (_cursor.isNull(_cursorIndexOfKoocheFarei1)) {
              _item.koocheFarei1 = null;
            } else {
              _item.koocheFarei1 = _cursor.getString(_cursorIndexOfKoocheFarei1);
            }
            if (_cursor.isNull(_cursorIndexOfKoocheFarei2)) {
              _item.koocheFarei2 = null;
            } else {
              _item.koocheFarei2 = _cursor.getString(_cursorIndexOfKoocheFarei2);
            }
            _item.pelak = _cursor.getInt(_cursorIndexOfPelak);
            _item.codePosty = _cursor.getInt(_cursorIndexOfCodePosty);
            _item.latitude = _cursor.getDouble(_cursorIndexOfLatitude);
            _item.longitude = _cursor.getDouble(_cursorIndexOfLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
