package com.example.shopmiphamapp.Database.Cart;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.User.User;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey
    @NonNull
    private String id;
    private int productId;
    private String userId;
    private int quantity;

    public Cart(String id, int productId, String userId) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = 1;
    }

    @Ignore
    public Cart(int productId, String userId) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = 1;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
