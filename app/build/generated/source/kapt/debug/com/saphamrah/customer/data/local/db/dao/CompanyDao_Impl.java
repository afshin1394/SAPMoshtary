package com.saphamrah.customer.data.local.db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.RxRoom;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.saphamrah.customer.data.local.db.entity.Company;
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
public final class CompanyDao_Impl extends CompanyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Company> __insertionAdapterOfCompany;

  private final EntityDeletionOrUpdateAdapter<Company> __deletionAdapterOfCompany;

  private final EntityDeletionOrUpdateAdapter<Company> __updateAdapterOfCompany;

  public CompanyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCompany = new EntityInsertionAdapter<Company>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Company` (`ccCompany`,`companyTitle`,`image`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Company value) {
        stmt.bindLong(1, value.ccCompany);
        if (value.companyTitle == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.companyTitle);
        }
        if (value.image == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindBlob(3, value.image);
        }
      }
    };
    this.__deletionAdapterOfCompany = new EntityDeletionOrUpdateAdapter<Company>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Company` WHERE `ccCompany` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Company value) {
        stmt.bindLong(1, value.ccCompany);
      }
    };
    this.__updateAdapterOfCompany = new EntityDeletionOrUpdateAdapter<Company>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Company` SET `ccCompany` = ?,`companyTitle` = ?,`image` = ? WHERE `ccCompany` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Company value) {
        stmt.bindLong(1, value.ccCompany);
        if (value.companyTitle == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.companyTitle);
        }
        if (value.image == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindBlob(3, value.image);
        }
        stmt.bindLong(4, value.ccCompany);
      }
    };
  }

  @Override
  public void insert(final Company entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCompany.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final List<Company> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCompany.insert(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Company entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCompany.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final List<Company> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCompany.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Company entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCompany.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final List<Company> entities) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCompany.handleMultiple(entities);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Observable<List<Company>> getAll() {
    final String _sql = "SELECT `company`.`ccCompany` AS `ccCompany`, `company`.`companyTitle` AS `companyTitle`, `company`.`image` AS `image` FROM company";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return RxRoom.createObservable(__db, false, new String[]{"company"}, new Callable<List<Company>>() {
      @Override
      public List<Company> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfCcCompany = 0;
          final int _cursorIndexOfCompanyTitle = 1;
          final int _cursorIndexOfImage = 2;
          final List<Company> _result = new ArrayList<Company>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Company _item;
            _item = new Company();
            _item.ccCompany = _cursor.getInt(_cursorIndexOfCcCompany);
            if (_cursor.isNull(_cursorIndexOfCompanyTitle)) {
              _item.companyTitle = null;
            } else {
              _item.companyTitle = _cursor.getString(_cursorIndexOfCompanyTitle);
            }
            if (_cursor.isNull(_cursorIndexOfImage)) {
              _item.image = null;
            } else {
              _item.image = _cursor.getBlob(_cursorIndexOfImage);
            }
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
