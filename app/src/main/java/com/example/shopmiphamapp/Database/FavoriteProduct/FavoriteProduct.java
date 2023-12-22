package com.example.shopmiphamapp.Database.FavoriteProduct;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_product")
public class FavoriteProduct {
    @PrimaryKey(autoGenerate = true)
    private int favoriteProductId;
    private int userId;
    private int productId;

    public FavoriteProduct(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getFavoriteProductId() {
        return favoriteProductId;
    }

    public void setFavoriteProductId(int favoriteProductId) {
        this.favoriteProductId = favoriteProductId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
