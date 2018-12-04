package com.example.adityanarayan.tollpay.Database;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class VehicleDao_Impl implements VehicleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfVehicle;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfVehicle;

  public VehicleDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVehicle = new EntityInsertionAdapter<Vehicle>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `vehicle_details`(`vehicle_no`,`vehicle_name`,`timeStamp`,`vehicle_type`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vehicle value) {
        if (value.getVehicle_no() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getVehicle_no());
        }
        if (value.getVehicle_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVehicle_name());
        }
        if (value.getTimeStamp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimeStamp());
        }
        if (value.getVehicle_type() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVehicle_type());
        }
      }
    };
    this.__updateAdapterOfVehicle = new EntityDeletionOrUpdateAdapter<Vehicle>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `vehicle_details` SET `vehicle_no` = ?,`vehicle_name` = ?,`timeStamp` = ?,`vehicle_type` = ? WHERE `vehicle_no` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vehicle value) {
        if (value.getVehicle_no() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getVehicle_no());
        }
        if (value.getVehicle_name() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVehicle_name());
        }
        if (value.getTimeStamp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimeStamp());
        }
        if (value.getVehicle_type() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getVehicle_type());
        }
        if (value.getVehicle_no() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getVehicle_no());
        }
      }
    };
  }

  @Override
  public void newVehicle(Vehicle vehicle) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfVehicle.insert(vehicle);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void addVehicle(Vehicle vehicle) {
    __db.beginTransaction();
    try {
      __updateAdapterOfVehicle.handle(vehicle);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Vehicle> getVehicle() {
    final String _sql = "SELECT vehicle_type, vehicle_name, vehicle_no, timeStamp FROM vehicle_details";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfVehicleType = _cursor.getColumnIndexOrThrow("vehicle_type");
      final int _cursorIndexOfVehicleName = _cursor.getColumnIndexOrThrow("vehicle_name");
      final int _cursorIndexOfVehicleNo = _cursor.getColumnIndexOrThrow("vehicle_no");
      final int _cursorIndexOfTimeStamp = _cursor.getColumnIndexOrThrow("timeStamp");
      final List<Vehicle> _result = new ArrayList<Vehicle>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Vehicle _item;
        _item = new Vehicle();
        final String _tmpVehicle_type;
        _tmpVehicle_type = _cursor.getString(_cursorIndexOfVehicleType);
        _item.setVehicle_type(_tmpVehicle_type);
        final String _tmpVehicle_name;
        _tmpVehicle_name = _cursor.getString(_cursorIndexOfVehicleName);
        _item.setVehicle_name(_tmpVehicle_name);
        final String _tmpVehicle_no;
        _tmpVehicle_no = _cursor.getString(_cursorIndexOfVehicleNo);
        _item.setVehicle_no(_tmpVehicle_no);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        _item.setTimeStamp(_tmpTimeStamp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
