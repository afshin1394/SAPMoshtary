package com.saphamrah.customer.data.local.database.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.saphamrah.customer.data.local.database.entity.MoshtaryTable;
import io.reactivex.Completable;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.Void;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MoshtaryDao_Impl implements MoshtaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MoshtaryTable> __insertionAdapterOfMoshtaryTable;

  public MoshtaryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMoshtaryTable = new EntityInsertionAdapter<MoshtaryTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MoshtaryTable` (`ccMoshtary`,`nameMoshtary`,`nameTablo`,`codeEghtesady`,`codeMely`,`shenasehMeli`,`mobile`,`telephone`,`tarikhTavalod`,`email`,`codeMoshtary`,`companyTitle`,`codeNoeShakhsiat`,`codeJesiat`,`ccCompany`,`isValid`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MoshtaryTable value) {
        stmt.bindLong(1, value.ccMoshtary);
        if (value.getNameMoshtary() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNameMoshtary());
        }
        if (value.getNameTablo() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getNameTablo());
        }
        if (value.getCodeEghtesady() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCodeEghtesady());
        }
        if (value.getCodeMely() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCodeMely());
        }
        if (value.getShenasehMeli() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getShenasehMeli());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMobile());
        }
        if (value.getTelephone() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTelephone());
        }
        if (value.getTarikhTavalod() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getTarikhTavalod());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getEmail());
        }
        if (value.getCodeMoshtary() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCodeMoshtary());
        }
        if (value.getCompanyTitle() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getCompanyTitle());
        }
        stmt.bindLong(13, value.getCodeNoeShakhsiat());
        stmt.bindLong(14, value.getCodeJesiat());
        stmt.bindLong(15, value.getCcCompany());
        stmt.bindLong(16, value.getIsValid());
      }
    };
  }

  @Override
  public Completable insert(final List<MoshtaryTable> moshtary) {
    return Completable.fromCallable(new Callable<Void>() {
      @Override
      public Void call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMoshtaryTable.insert(moshtary);
          __db.setTransactionSuccessful();
          return null;
        } finally {
          __db.endTransaction();
        }
      }
    });
  }

  @Override
  public List<MoshtaryTable> getAll() {
    final String _sql = "SELECT `MoshtaryTable`.`ccMoshtary` AS `ccMoshtary`, `MoshtaryTable`.`nameMoshtary` AS `nameMoshtary`, `MoshtaryTable`.`nameTablo` AS `nameTablo`, `MoshtaryTable`.`codeEghtesady` AS `codeEghtesady`, `MoshtaryTable`.`codeMely` AS `codeMely`, `MoshtaryTable`.`shenasehMeli` AS `shenasehMeli`, `MoshtaryTable`.`mobile` AS `mobile`, `MoshtaryTable`.`telephone` AS `telephone`, `MoshtaryTable`.`tarikhTavalod` AS `tarikhTavalod`, `MoshtaryTable`.`email` AS `email`, `MoshtaryTable`.`codeMoshtary` AS `codeMoshtary`, `MoshtaryTable`.`companyTitle` AS `companyTitle`, `MoshtaryTable`.`codeNoeShakhsiat` AS `codeNoeShakhsiat`, `MoshtaryTable`.`codeJesiat` AS `codeJesiat`, `MoshtaryTable`.`ccCompany` AS `ccCompany`, `MoshtaryTable`.`isValid` AS `isValid` FROM MoshtaryTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCcMoshtary = 0;
      final int _cursorIndexOfNameMoshtary = 1;
      final int _cursorIndexOfNameTablo = 2;
      final int _cursorIndexOfCodeEghtesady = 3;
      final int _cursorIndexOfCodeMely = 4;
      final int _cursorIndexOfShenasehMeli = 5;
      final int _cursorIndexOfMobile = 6;
      final int _cursorIndexOfTelephone = 7;
      final int _cursorIndexOfTarikhTavalod = 8;
      final int _cursorIndexOfEmail = 9;
      final int _cursorIndexOfCodeMoshtary = 10;
      final int _cursorIndexOfCompanyTitle = 11;
      final int _cursorIndexOfCodeNoeShakhsiat = 12;
      final int _cursorIndexOfCodeJesiat = 13;
      final int _cursorIndexOfCcCompany = 14;
      final int _cursorIndexOfIsValid = 15;
      final List<MoshtaryTable> _result = new ArrayList<MoshtaryTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MoshtaryTable _item;
        final int _tmpCcMoshtary;
        _tmpCcMoshtary = _cursor.getInt(_cursorIndexOfCcMoshtary);
        final String _tmpNameMoshtary;
        if (_cursor.isNull(_cursorIndexOfNameMoshtary)) {
          _tmpNameMoshtary = null;
        } else {
          _tmpNameMoshtary = _cursor.getString(_cursorIndexOfNameMoshtary);
        }
        final String _tmpNameTablo;
        if (_cursor.isNull(_cursorIndexOfNameTablo)) {
          _tmpNameTablo = null;
        } else {
          _tmpNameTablo = _cursor.getString(_cursorIndexOfNameTablo);
        }
        final String _tmpCodeEghtesady;
        if (_cursor.isNull(_cursorIndexOfCodeEghtesady)) {
          _tmpCodeEghtesady = null;
        } else {
          _tmpCodeEghtesady = _cursor.getString(_cursorIndexOfCodeEghtesady);
        }
        final String _tmpCodeMely;
        if (_cursor.isNull(_cursorIndexOfCodeMely)) {
          _tmpCodeMely = null;
        } else {
          _tmpCodeMely = _cursor.getString(_cursorIndexOfCodeMely);
        }
        final String _tmpShenasehMeli;
        if (_cursor.isNull(_cursorIndexOfShenasehMeli)) {
          _tmpShenasehMeli = null;
        } else {
          _tmpShenasehMeli = _cursor.getString(_cursorIndexOfShenasehMeli);
        }
        final String _tmpMobile;
        if (_cursor.isNull(_cursorIndexOfMobile)) {
          _tmpMobile = null;
        } else {
          _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
        }
        final String _tmpTelephone;
        if (_cursor.isNull(_cursorIndexOfTelephone)) {
          _tmpTelephone = null;
        } else {
          _tmpTelephone = _cursor.getString(_cursorIndexOfTelephone);
        }
        final String _tmpTarikhTavalod;
        if (_cursor.isNull(_cursorIndexOfTarikhTavalod)) {
          _tmpTarikhTavalod = null;
        } else {
          _tmpTarikhTavalod = _cursor.getString(_cursorIndexOfTarikhTavalod);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpCodeMoshtary;
        if (_cursor.isNull(_cursorIndexOfCodeMoshtary)) {
          _tmpCodeMoshtary = null;
        } else {
          _tmpCodeMoshtary = _cursor.getString(_cursorIndexOfCodeMoshtary);
        }
        final String _tmpCompanyTitle;
        if (_cursor.isNull(_cursorIndexOfCompanyTitle)) {
          _tmpCompanyTitle = null;
        } else {
          _tmpCompanyTitle = _cursor.getString(_cursorIndexOfCompanyTitle);
        }
        final int _tmpCodeNoeShakhsiat;
        _tmpCodeNoeShakhsiat = _cursor.getInt(_cursorIndexOfCodeNoeShakhsiat);
        final int _tmpCodeJesiat;
        _tmpCodeJesiat = _cursor.getInt(_cursorIndexOfCodeJesiat);
        final int _tmpCcCompany;
        _tmpCcCompany = _cursor.getInt(_cursorIndexOfCcCompany);
        final int _tmpIsValid;
        _tmpIsValid = _cursor.getInt(_cursorIndexOfIsValid);
        _item = new MoshtaryTable(_tmpCcMoshtary,_tmpNameMoshtary,_tmpNameTablo,_tmpCodeEghtesady,_tmpCodeMely,_tmpShenasehMeli,_tmpMobile,_tmpTelephone,_tmpTarikhTavalod,_tmpEmail,_tmpCodeMoshtary,_tmpCompanyTitle,_tmpCodeNoeShakhsiat,_tmpCodeJesiat,_tmpCcCompany,_tmpIsValid);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
