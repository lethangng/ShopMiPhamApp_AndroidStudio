package com.example.shopmiphamapp.Database.ListImage;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ListImageDAO {
    @Insert
    void insertListImage(ListImage listImage);

    @Query("SELECT * FROM list_image where productId=:productId")
    ListImage getListImage(int productId);
}
