package com.example.shopmiphamapp.Database.Cart;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.shopmiphamapp.Database.Product.Product;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insertCart(Cart cart);

    @Query("SELECT * FROM cart where userId= :userId")
    List<Cart> getListCartUser(int userId);

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getListCartLiveData();

    @Query("SELECT product.* FROM cart INNER JOIN product ON cart.productId = product.productId where cart.productId= :productId")
    Product getListProductCartById(int productId);

    @Query("SELECT * FROM cart WHERE productId= :productId LIMIT 1")
    Cart checkProductExist(int productId);

    @Query("DELETE FROM cart WHERE cartId = :cartId")
    void deleteCart(int cartId);
}


