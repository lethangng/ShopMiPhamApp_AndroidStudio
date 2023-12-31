package com.example.shopmiphamapp.Database.Bill;

import androidx.room.Dao;
import androidx.room.Delete;
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
    List<Bill> getBillByUserId(String userId);

    @Query("SELECT * FROM bill WHERE id= :billId")
    Bill getBillById(String billId);

    @Query("DELETE FROM bill")
    void deleteAllBills();

//    @Query("DELETE FROM sqlite_sequence WHERE name='bill'")
//    void resetBillId();
}
