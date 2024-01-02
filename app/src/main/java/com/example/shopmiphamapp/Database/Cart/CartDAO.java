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
    List<Cart> getListCartUser(String userId);

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getListCartLiveData();

    @Query("SELECT product.* FROM cart INNER JOIN product ON cart.productId = product.id where cart.productId= :productId")
    Product getListProductCartById(int productId);

    @Query("SELECT EXISTS(SELECT * FROM cart WHERE productId= :productId AND userId= :userId LIMIT 1)")
    boolean checkProductExist(int productId, String userId);

    @Query("DELETE FROM cart WHERE id = :cartId")
    void deleteCart(String cartId);

    @Query("DELETE FROM cart")
    void deleteAllCarts();
//    @Query("DELETE FROM sqlite_sequence WHERE name='cart'")
//    void resetCartId();
}


