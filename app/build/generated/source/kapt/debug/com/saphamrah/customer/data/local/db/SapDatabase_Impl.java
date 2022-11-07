package com.saphamrah.customer.data.local.db;

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
import com.saphamrah.customer.data.local.db.dao.BankDao;
import com.saphamrah.customer.data.local.db.dao.BankDao_Impl;
import com.saphamrah.customer.data.local.db.dao.CompanyDao;
import com.saphamrah.customer.data.local.db.dao.CompanyDao_Impl;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao;
import com.saphamrah.customer.data.local.db.dao.MoshtarySazmanForoshAddressDao_Impl;
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
public final class SapDatabase_Impl extends SapDatabase {
  private volatile CompanyDao _companyDao;

  private volatile BankDao _bankDao;

  private volatile MoshtarySazmanForoshAddressDao _moshtarySazmanForoshAddressDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Company` (`ccCompany` INTEGER NOT NULL, `companyTitle` TEXT, `image` BLOB, PRIMARY KEY(`ccCompany`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Bank` (`ccBank` INTEGER NOT NULL, `bankTitle` TEXT, PRIMARY KEY(`ccBank`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MoshtarySazmanForoshAddress` (`ccMoshtarySazmanForoshAddress` INTEGER NOT NULL, `ccMoshtarySazmanForosh` INTEGER NOT NULL, `ccMoshtaryAddress` INTEGER NOT NULL, `ccAddress` INTEGER NOT NULL, `codeNoeAddress` INTEGER NOT NULL, `noeAddressTitle` INTEGER NOT NULL, `ccMahal` INTEGER NOT NULL, `ccMantaghe` INTEGER NOT NULL, `address` TEXT, `khiabanAsli` TEXT, `khiabanFarei1` TEXT, `khiabanFarei2` TEXT, `koocheAsli` TEXT, `koocheFarei1` TEXT, `koocheFarei2` TEXT, `pelak` INTEGER NOT NULL, `codePosty` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`ccMoshtarySazmanForoshAddress`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd3ef336e7140e54468a1d3ea0b8807dd')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Company`");
        _db.execSQL("DROP TABLE IF EXISTS `Bank`");
        _db.execSQL("DROP TABLE IF EXISTS `MoshtarySazmanForoshAddress`");
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
        final HashMap<String, TableInfo.Column> _columnsCompany = new HashMap<String, TableInfo.Column>(3);
        _columnsCompany.put("ccCompany", new TableInfo.Column("ccCompany", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompany.put("companyTitle", new TableInfo.Column("companyTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompany.put("image", new TableInfo.Column("image", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCompany = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCompany = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCompany = new TableInfo("Company", _columnsCompany, _foreignKeysCompany, _indicesCompany);
        final TableInfo _existingCompany = TableInfo.read(_db, "Company");
        if (! _infoCompany.equals(_existingCompany)) {
          return new RoomOpenHelper.ValidationResult(false, "Company(com.saphamrah.customer.data.local.db.entity.Company).\n"
                  + " Expected:\n" + _infoCompany + "\n"
                  + " Found:\n" + _existingCompany);
        }
        final HashMap<String, TableInfo.Column> _columnsBank = new HashMap<String, TableInfo.Column>(2);
        _columnsBank.put("ccBank", new TableInfo.Column("ccBank", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBank.put("bankTitle", new TableInfo.Column("bankTitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBank = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBank = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBank = new TableInfo("Bank", _columnsBank, _foreignKeysBank, _indicesBank);
        final TableInfo _existingBank = TableInfo.read(_db, "Bank");
        if (! _infoBank.equals(_existingBank)) {
          return new RoomOpenHelper.ValidationResult(false, "Bank(com.saphamrah.customer.data.local.db.entity.Bank).\n"
                  + " Expected:\n" + _infoBank + "\n"
                  + " Found:\n" + _existingBank);
        }
        final HashMap<String, TableInfo.Column> _columnsMoshtarySazmanForoshAddress = new HashMap<String, TableInfo.Column>(19);
        _columnsMoshtarySazmanForoshAddress.put("ccMoshtarySazmanForoshAddress", new TableInfo.Column("ccMoshtarySazmanForoshAddress", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("ccMoshtarySazmanForosh", new TableInfo.Column("ccMoshtarySazmanForosh", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("ccMoshtaryAddress", new TableInfo.Column("ccMoshtaryAddress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("ccAddress", new TableInfo.Column("ccAddress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("codeNoeAddress", new TableInfo.Column("codeNoeAddress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("noeAddressTitle", new TableInfo.Column("noeAddressTitle", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("ccMahal", new TableInfo.Column("ccMahal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("ccMantaghe", new TableInfo.Column("ccMantaghe", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("khiabanAsli", new TableInfo.Column("khiabanAsli", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("khiabanFarei1", new TableInfo.Column("khiabanFarei1", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("khiabanFarei2", new TableInfo.Column("khiabanFarei2", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("koocheAsli", new TableInfo.Column("koocheAsli", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("koocheFarei1", new TableInfo.Column("koocheFarei1", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("koocheFarei2", new TableInfo.Column("koocheFarei2", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("pelak", new TableInfo.Column("pelak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("codePosty", new TableInfo.Column("codePosty", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoshtarySazmanForoshAddress.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMoshtarySazmanForoshAddress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMoshtarySazmanForoshAddress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMoshtarySazmanForoshAddress = new TableInfo("MoshtarySazmanForoshAddress", _columnsMoshtarySazmanForoshAddress, _foreignKeysMoshtarySazmanForoshAddress, _indicesMoshtarySazmanForoshAddress);
        final TableInfo _existingMoshtarySazmanForoshAddress = TableInfo.read(_db, "MoshtarySazmanForoshAddress");
        if (! _infoMoshtarySazmanForoshAddress.equals(_existingMoshtarySazmanForoshAddress)) {
          return new RoomOpenHelper.ValidationResult(false, "MoshtarySazmanForoshAddress(com.saphamrah.customer.data.local.db.entity.MoshtarySazmanForoshAddress).\n"
                  + " Expected:\n" + _infoMoshtarySazmanForoshAddress + "\n"
                  + " Found:\n" + _existingMoshtarySazmanForoshAddress);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d3ef336e7140e54468a1d3ea0b8807dd", "a75e41dc44e398a0018c66519caa1a0f");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Company","Bank","MoshtarySazmanForoshAddress");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Company`");
      _db.execSQL("DELETE FROM `Bank`");
      _db.execSQL("DELETE FROM `MoshtarySazmanForoshAddress`");
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
    _typeConvertersMap.put(CompanyDao.class, CompanyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BankDao.class, BankDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MoshtarySazmanForoshAddressDao.class, MoshtarySazmanForoshAddressDao_Impl.getRequiredConverters());
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
  public CompanyDao companyDao() {
    if (_companyDao != null) {
      return _companyDao;
    } else {
      synchronized(this) {
        if(_companyDao == null) {
          _companyDao = new CompanyDao_Impl(this);
        }
        return _companyDao;
      }
    }
  }

  @Override
  public BankDao bankDao() {
    if (_bankDao != null) {
      return _bankDao;
    } else {
      synchronized(this) {
        if(_bankDao == null) {
          _bankDao = new BankDao_Impl(this);
        }
        return _bankDao;
      }
    }
  }

  @Override
  public MoshtarySazmanForoshAddressDao moshtarySazmanForoshAddressDao() {
    if (_moshtarySazmanForoshAddressDao != null) {
      return _moshtarySazmanForoshAddressDao;
    } else {
      synchronized(this) {
        if(_moshtarySazmanForoshAddressDao == null) {
          _moshtarySazmanForoshAddressDao = new MoshtarySazmanForoshAddressDao_Impl(this);
        }
        return _moshtarySazmanForoshAddressDao;
      }
    }
  }
}
