package com.example.shopmiphamapp.Database.ProductType;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ProductTypeDAO {
    @Insert
    void insertProductType(ProductType productType);

    @Query("SELECT * FROM product_type")
    List<ProductType> getListProductType();

    @Query("SELECT * FROM product_type where id= :productTypeId LIMIT 1")
    ProductType getProductTypeById(int productTypeId);
    @Query("DELETE FROM product_type")
    void deleteAllProductTypes();
}
