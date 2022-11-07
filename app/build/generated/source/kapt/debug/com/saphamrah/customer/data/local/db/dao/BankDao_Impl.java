package com.saphamrah.customer.data.local.db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.saphamrah.customer.data.local.db.entity.Bank;
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
public final class BankDao_Impl extends BankDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Bank> __insertionAdapterOfBank;

  private final EntityDeletionOrUpdateAdapter<Bank> __deletionAdapterOfBank;

  private final EntityDeletionOrUpdateAdapter<Bank> __updateAdapterOfBank;

  public BankDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBank = new EntityInsertionAdapter<Bank>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Bank` (`ccBank`,`bankTitle`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bank value) {
        stmt.bindLong(1, value.ccBank);
        if (value.bankTitle == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.bankTitle);
        }
      }
    };
    this.__deletionAdapterOfBank = new EntityDeletionOrUpdateAdapter<Bank>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Bank` WHERE `ccBank` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bank value) {
        stmt.bindLong(1, value.ccBank);
      }
    };
    this.__updateAdapterOfBank = new EntityDeletionOrUpdateAdapter<Bank>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Bank` SET `ccBank` = ?,`bankTitle` = ? WHERE `ccBank` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Bank value) {
        stmt.bindLong(1, value.ccBank);
        if (value.bankTitle == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.bankTitle);
        }
        stmt.bindLong(3, value.ccBank);
      }
    };
  }

  @Override
  public void insert(final Bank entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBank.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final List<Bank> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfBank.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Bank entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBank.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final List<Bank> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfBank.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Bank entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBank.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final List<Bank> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfBank.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Observable<List<Bank>> getAll() {
    final String _sql = "SELECT `bank`.`ccBank` AS `ccBank`, `bank`.`bankTitle` AS `bankTitle` FROM bank";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createObservable(__db, false, new String[]{"bank"}, new Callable<List<Bank>>() {
      @Override
      public List<Bank> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCcBank = 0;
          final int _cursorIndexOfBankTitle = 1;
          final List<Bank> _result = new ArrayList<Bank>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Bank _item;
            final int _tmpCcBank;
            _tmpCcBank = _cursor.getInt(_cursorIndexOfCcBank);
            final String _tmpBankTitle;
            if (_cursor.isNull(_cursorIndexOfBankTitle)) {
              _tmpBankTitle = null;
            } else {
              _tmpBankTitle = _cursor.getString(_cursorIndexOfBankTitle);
            }
            _item = new Bank(_tmpCcBank,_tmpBankTitle);
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
