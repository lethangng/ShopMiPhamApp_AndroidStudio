package com.example.shopmiphamapp.Database.Carousel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarouselDAO {
    @Insert
    void insertCarousel(Carousel carousel);

    @Query("SELECT * FROM carousel")
    List<Carousel> getListCarousel();

    @Query("SELECT * FROM carousel")
    LiveData<List<Carousel>> getAllCarousels();

    @Query("DELETE FROM carousel")
    void deleteAllCarousels();
//    @Query("DELETE FROM sqlite_sequence WHERE name='carousel'")
//    void resetCarouselId();
}
