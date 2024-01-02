package com.example.shopmiphamapp.Database.FavoriteProduct;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_product")
public class FavoriteProduct {
    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private int productId;

    public FavoriteProduct(String id, String userId, int productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    @Ignore
    public FavoriteProduct(String userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
