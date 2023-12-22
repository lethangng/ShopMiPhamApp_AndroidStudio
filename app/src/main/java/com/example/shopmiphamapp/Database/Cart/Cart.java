package com.example.shopmiphamapp.Database.Cart;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.User.User;

@Entity(tableName = "cart")
public class Cart {
    @PrimaryKey(autoGenerate = true)
    private int cartId;
    private int productId;
    private int userId;
    @ColumnInfo(defaultValue = "1")
    private int quantity;

    public Cart(int productId, int userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
