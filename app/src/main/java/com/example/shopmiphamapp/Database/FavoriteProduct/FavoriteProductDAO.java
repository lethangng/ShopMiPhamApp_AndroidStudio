package com.example.shopmiphamapp.Database.FavoriteProduct;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopmiphamapp.Database.ProductType.ProductType;

import java.util.List;

@Dao
public interface FavoriteProductDAO {
    @Insert
    void insertFavoriteProduct(FavoriteProduct favoriteProduct);

    @Query("SELECT * FROM favorite_product where userId= :userId")
    List<FavoriteProduct> getFavoriteProduct(int userId);

    @Query("SELECT * FROM favorite_product")
     LiveData<List<FavoriteProduct>> getListProductLiveData();

    @Query("SELECT EXISTS( SELECT * FROM favorite_product " +
            "WHERE productId= :productId LIMIT 1)")
    boolean checkFavoriteProductExist(int productId);

    @Query("DELETE FROM favorite_product WHERE userId= :userId AND productId= :productId")
    void deleteFavoriteProduct(int userId, int productId);
}
