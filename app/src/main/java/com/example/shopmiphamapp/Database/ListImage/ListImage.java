package com.example.shopmiphamapp.Database.ListImage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "list_image")
public class ListImage {
    @PrimaryKey(autoGenerate = true)
    private int idListImage;
    private int productId;
    private int listImg;

    public ListImage(int productId, int listImg) {
        this.productId = productId;
        this.listImg = listImg;
    }

    public int getIdListImage() {
        return idListImage;
    }

    public void setIdListImage(int idListImage) {
        this.idListImage = idListImage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getListImg() {
        return listImg;
    }

    public void setListImg(int listImg) {
        this.listImg = listImg;
    }
}
