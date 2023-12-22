package com.example.shopmiphamapp.Database.Carousel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carousel")
public class Carousel {
    @PrimaryKey(autoGenerate = true)
    private int carouselId;
    private int carouselImg;

    public Carousel(int carouselImg) {
        this.carouselImg = carouselImg;
    }

    public int getCarouselId() {
        return carouselId;
    }

    public void setCarouselId(int carouselId) {
        this.carouselId = carouselId;
    }

    public int getCarouselImg() {
        return carouselImg;
    }

    public void setCarouselImg(int carouselImg) {
        this.carouselImg = carouselImg;
    }
}
