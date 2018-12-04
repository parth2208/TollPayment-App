package com.example.adityanarayan.tollpay.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class VehicleDatabase_Impl extends VehicleDatabase {
  private volatile VehicleDao _vehicleDao;

  private volatile TransactionDao _transactionDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `vehicle_details` (`vehicle_no` TEXT NOT NULL, `vehicle_name` TEXT, `timeStamp` TEXT, `vehicle_type` TEXT, PRIMARY KEY(`vehicle_no`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `transaction_details` (`vehicle_no` TEXT NOT NULL, `vehicle_type` TEXT, `timeStamp` TEXT, `source` TEXT, `destination` TEXT, `distance` INTEGER NOT NULL, `amount` INTEGER NOT NULL, `transaction_id` INTEGER NOT NULL, `cardTimeStamp` TEXT, `card_cvv` INTEGER NOT NULL, `card_no` TEXT, `success` INTEGER NOT NULL, PRIMARY KEY(`vehicle_no`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a268261671d6c620fe6d8b00bc753bd3\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `vehicle_details`");
        _db.execSQL("DROP TABLE IF EXISTS `transaction_details`");
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
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsVehicleDetails = new HashMap<String, TableInfo.Column>(4);
        _columnsVehicleDetails.put("vehicle_no", new TableInfo.Column("vehicle_no", "TEXT", true, 1));
        _columnsVehicleDetails.put("vehicle_name", new TableInfo.Column("vehicle_name", "TEXT", false, 0));
        _columnsVehicleDetails.put("timeStamp", new TableInfo.Column("timeStamp", "TEXT", false, 0));
        _columnsVehicleDetails.put("vehicle_type", new TableInfo.Column("vehicle_type", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVehicleDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVehicleDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVehicleDetails = new TableInfo("vehicle_details", _columnsVehicleDetails, _foreignKeysVehicleDetails, _indicesVehicleDetails);
        final TableInfo _existingVehicleDetails = TableInfo.read(_db, "vehicle_details");
        if (! _infoVehicleDetails.equals(_existingVehicleDetails)) {
          throw new IllegalStateException("Migration didn't properly handle vehicle_details(com.example.adityanarayan.tollpay.Database.Vehicle).\n"
                  + " Expected:\n" + _infoVehicleDetails + "\n"
                  + " Found:\n" + _existingVehicleDetails);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactionDetails = new HashMap<String, TableInfo.Column>(12);
        _columnsTransactionDetails.put("vehicle_no", new TableInfo.Column("vehicle_no", "TEXT", true, 1));
        _columnsTransactionDetails.put("vehicle_type", new TableInfo.Column("vehicle_type", "TEXT", false, 0));
        _columnsTransactionDetails.put("timeStamp", new TableInfo.Column("timeStamp", "TEXT", false, 0));
        _columnsTransactionDetails.put("source", new TableInfo.Column("source", "TEXT", false, 0));
        _columnsTransactionDetails.put("destination", new TableInfo.Column("destination", "TEXT", false, 0));
        _columnsTransactionDetails.put("distance", new TableInfo.Column("distance", "INTEGER", true, 0));
        _columnsTransactionDetails.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0));
        _columnsTransactionDetails.put("transaction_id", new TableInfo.Column("transaction_id", "INTEGER", true, 0));
        _columnsTransactionDetails.put("cardTimeStamp", new TableInfo.Column("cardTimeStamp", "TEXT", false, 0));
        _columnsTransactionDetails.put("card_cvv", new TableInfo.Column("card_cvv", "INTEGER", true, 0));
        _columnsTransactionDetails.put("card_no", new TableInfo.Column("card_no", "TEXT", false, 0));
        _columnsTransactionDetails.put("success", new TableInfo.Column("success", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactionDetails = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTransactionDetails = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTransactionDetails = new TableInfo("transaction_details", _columnsTransactionDetails, _foreignKeysTransactionDetails, _indicesTransactionDetails);
        final TableInfo _existingTransactionDetails = TableInfo.read(_db, "transaction_details");
        if (! _infoTransactionDetails.equals(_existingTransactionDetails)) {
          throw new IllegalStateException("Migration didn't properly handle transaction_details(com.example.adityanarayan.tollpay.Database.Transaction).\n"
                  + " Expected:\n" + _infoTransactionDetails + "\n"
                  + " Found:\n" + _existingTransactionDetails);
        }
      }
    }, "a268261671d6c620fe6d8b00bc753bd3", "c8113e63ce57095f17bc8acec6d8e0c4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "vehicle_details","transaction_details");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `vehicle_details`");
      _db.execSQL("DELETE FROM `transaction_details`");
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
  public VehicleDao vehicleDao() {
    if (_vehicleDao != null) {
      return _vehicleDao;
    } else {
      synchronized(this) {
        if(_vehicleDao == null) {
          _vehicleDao = new VehicleDao_Impl(this);
        }
        return _vehicleDao;
      }
    }
  }

  @Override
  public TransactionDao transactionDao() {
    if (_transactionDao != null) {
      return _transactionDao;
    } else {
      synchronized(this) {
        if(_transactionDao == null) {
          _transactionDao = new TransactionDao_Impl(this);
        }
        return _transactionDao;
      }
    }
  }
}
