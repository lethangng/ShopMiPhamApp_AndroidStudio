package com.example.shopmiphamapp.Database.Product_Bill;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface Product_BillDAO {
    @Insert
    void insertProductBill(Product_Bill product_bill);

    @Query("SELECT * FROM product_bill WHERE billId= :billId")
    List<Product_Bill> getListProductBill(int billId);
}
