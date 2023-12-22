package com.example.shopmiphamapp.Database.Bill;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BillDAO {
    // Insert và trả lại Id của bản ghi ấy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBill(Bill bill);

    @Query("SELECT * FROM bill WHERE userId= :userId")
    List<Bill> getBillByUserId(int userId);

    @Query("SELECT * FROM bill WHERE id= :billId")
    Bill getBillById(int billId);
}
