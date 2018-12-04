package com.example.adityanarayan.tollpay.Database;

import android.arch.persistence.db.SupportSQLiteStatement;
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
public class TransactionDao_Impl implements TransactionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTransaction;

  public TransactionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTransaction = new EntityInsertionAdapter<Transaction>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `transaction_details`(`vehicle_no`,`vehicle_type`,`timeStamp`,`source`,`destination`,`distance`,`amount`,`transaction_id`,`cardTimeStamp`,`card_cvv`,`card_no`,`success`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Transaction value) {
        if (value.getVehicle_no() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getVehicle_no());
        }
        if (value.getVehicle_type() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getVehicle_type());
        }
        if (value.getTimeStamp() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTimeStamp());
        }
        if (value.getSource() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSource());
        }
        if (value.getDestination() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDestination());
        }
        stmt.bindLong(6, value.getDistance());
        stmt.bindLong(7, value.getAmount());
        stmt.bindLong(8, value.getTransaction_id());
        if (value.getCardTimeStamp() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getCardTimeStamp());
        }
        stmt.bindLong(10, value.getCard_cvv());
        if (value.getCard_no() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getCard_no());
        }
        final int _tmp;
        _tmp = value.isSuccess() ? 1 : 0;
        stmt.bindLong(12, _tmp);
      }
    };
  }

  @Override
  public void addTransaction(Transaction history) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTransaction.insert(history);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Transaction> gethistory() {
    final String _sql = "SELECT vehicle_no,amount,timeStamp,distance,destination,source,vehicle_type,transaction_id,card_cvv,success from transaction_details";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfVehicleNo = _cursor.getColumnIndexOrThrow("vehicle_no");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfTimeStamp = _cursor.getColumnIndexOrThrow("timeStamp");
      final int _cursorIndexOfDistance = _cursor.getColumnIndexOrThrow("distance");
      final int _cursorIndexOfDestination = _cursor.getColumnIndexOrThrow("destination");
      final int _cursorIndexOfSource = _cursor.getColumnIndexOrThrow("source");
      final int _cursorIndexOfVehicleType = _cursor.getColumnIndexOrThrow("vehicle_type");
      final int _cursorIndexOfTransactionId = _cursor.getColumnIndexOrThrow("transaction_id");
      final int _cursorIndexOfCardCvv = _cursor.getColumnIndexOrThrow("card_cvv");
      final int _cursorIndexOfSuccess = _cursor.getColumnIndexOrThrow("success");
      final List<Transaction> _result = new ArrayList<Transaction>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Transaction _item;
        _item = new Transaction();
        final String _tmpVehicle_no;
        _tmpVehicle_no = _cursor.getString(_cursorIndexOfVehicleNo);
        _item.setVehicle_no(_tmpVehicle_no);
        final int _tmpAmount;
        _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
        _item.setAmount(_tmpAmount);
        final String _tmpTimeStamp;
        _tmpTimeStamp = _cursor.getString(_cursorIndexOfTimeStamp);
        _item.setTimeStamp(_tmpTimeStamp);
        final int _tmpDistance;
        _tmpDistance = _cursor.getInt(_cursorIndexOfDistance);
        _item.setDistance(_tmpDistance);
        final String _tmpDestination;
        _tmpDestination = _cursor.getString(_cursorIndexOfDestination);
        _item.setDestination(_tmpDestination);
        final String _tmpSource;
        _tmpSource = _cursor.getString(_cursorIndexOfSource);
        _item.setSource(_tmpSource);
        final String _tmpVehicle_type;
        _tmpVehicle_type = _cursor.getString(_cursorIndexOfVehicleType);
        _item.setVehicle_type(_tmpVehicle_type);
        final int _tmpTransaction_id;
        _tmpTransaction_id = _cursor.getInt(_cursorIndexOfTransactionId);
        _item.setTransaction_id(_tmpTransaction_id);
        final int _tmpCard_cvv;
        _tmpCard_cvv = _cursor.getInt(_cursorIndexOfCardCvv);
        _item.setCard_cvv(_tmpCard_cvv);
        final boolean _tmpSuccess;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfSuccess);
        _tmpSuccess = _tmp != 0;
        _item.setSuccess(_tmpSuccess);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
