package com.example.shopmiphamapp.Database.Bill;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BillDAO {
    @Insert
    void insertBill(Bill bill);

    @Query("SELECT * FROM bill")
    List<Bill> getListBill();
}
