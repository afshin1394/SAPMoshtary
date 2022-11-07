package com.saphamrah.customer.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.saphamrah.customer.data.local.database.dao.MoshtaryDao;
import com.saphamrah.customer.data.local.database.dao.MoshtaryDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile MoshtaryDao _moshtaryDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MoshtaryTable` (`ccMoshtary` INTEGER NOT NULL, `nameMoshtary` TEXT, `nameTablo` TEXT, `codeEghtesady` TEXT, `codeMely` TEXT, `shenasehMeli` TEXT, `mobile` TEXT, `telephone` TEXT, `tarikhTavalod` TEXT, `email` TEXT, `codeMoshtary` TEXT, `companyTitle` TEXT, `codeNoeShakhsiat` INTEGER NOT NULL, `codeJesiat` INTEGER NOT NULL, `ccCompany` INTEGER NOT NULL, `isValid` INTEGER NOT NULL, PRIMARY KEY(`ccMoshtary`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '579d0d5520c9e81fa601458e7868a2cc')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `MoshtaryTable`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMoshtaryTable = new HashMap<String, TableInfo.Column>(16);
        _columnsMoshtaryTable.put("ccMoshtary", new TableInfo.Column("ccMoshtary", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("nameMoshtary", new TableInfo.Column("nameMoshtary", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("nameTablo", new TableInfo.Column("nameTablo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("codeEghtesady", new TableInfo.Column("codeEghtesady", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("codeMely", new TableInfo.Column("codeMely", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("shenasehMeli", new TableInfo.Column("shenasehMeli", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("mobile", new TableInfo.Column("mobile", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("telephone", new TableInfo.Column("telephone", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("tarikhTavalod", new TableInfo.Column("tarikhTavalod", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("codeMoshtary", new TableInfo.Column("codeMoshtary", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("companyTitle", new TableInfo.Column("companyTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("codeNoeShakhsiat", new TableInfo.Column("codeNoeShakhsiat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("codeJesiat", new TableInfo.Column("codeJesiat", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("ccCompany", new TableInfo.Column("ccCompany", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtaryTable.put("isValid", new TableInfo.Column("isValid", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMoshtaryTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMoshtaryTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMoshtaryTable = new TableInfo("MoshtaryTable", _columnsMoshtaryTable, _foreignKeysMoshtaryTable, _indicesMoshtaryTable);
        final TableInfo _existingMoshtaryTable = TableInfo.read(_db, "MoshtaryTable");
        if (! _infoMoshtaryTable.equals(_existingMoshtaryTable)) {
          return new RoomOpenHelper.ValidationResult(false, "MoshtaryTable(com.saphamrah.customer.data.local.database.entity.MoshtaryTable).\n"
                  + " Expected:\n" + _infoMoshtaryTable + "\n"
                  + " Found:\n" + _existingMoshtaryTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "579d0d5520c9e81fa601458e7868a2cc", "bc99e59c02bb2702886a2c595dd6ced4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "MoshtaryTable");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `MoshtaryTable`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MoshtaryDao.class, MoshtaryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public MoshtaryDao moshtaryDao() {
    if (_moshtaryDao != null) {
      return _moshtaryDao;
    } else {
      synchronized(this) {
        if(_moshtaryDao == null) {
          _moshtaryDao = new MoshtaryDao_Impl(this);
        }
        return _moshtaryDao;
      }
    }
  }
}
