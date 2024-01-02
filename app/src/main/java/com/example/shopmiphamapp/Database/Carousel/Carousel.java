package com.example.shopmiphamapp.Database.Carousel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "carousel")
public class Carousel {
    @PrimaryKey
    @NonNull
    private int id;
    private String imgUrl;

    public Carousel(int id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    @Ignore
    public Carousel(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}



