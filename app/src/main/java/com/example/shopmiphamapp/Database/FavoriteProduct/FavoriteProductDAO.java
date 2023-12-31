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
    List<FavoriteProduct> getFavoriteProduct(String userId);

    @Query("SELECT * FROM favorite_product")
     LiveData<List<FavoriteProduct>> getListProductLiveData();

    @Query("SELECT EXISTS( SELECT * FROM favorite_product " +
            "WHERE productId= :productId AND userId= :userId LIMIT 1)")
    boolean checkFavoriteProductExist(int productId, String userId);

    @Query("SELECT id FROM favorite_product WHERE userId= :userId AND productId= :productId")
    String getFavoriteProductId(String userId, int productId);

    @Query("DELETE FROM favorite_product WHERE userId= :userId AND productId= :productId")
    void deleteFavoriteProduct(String userId, int productId);

    @Query("DELETE FROM favorite_product")
    void deleteAllFavoriteProducts();

//    @Query("DELETE FROM sqlite_sequence WHERE name='favorite_product'")
//    void resetFavoriteProductId();
}
