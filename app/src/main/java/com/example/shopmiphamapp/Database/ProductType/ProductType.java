package com.example.shopmiphamapp.Database.ProductType;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_type")
public class ProductType {
    @PrimaryKey(autoGenerate = true)
    private int productTypeId;
    private String name;
    private int productTypeImg;
    private String description;

    public ProductType(String name, int productTypeImg, String description) {
        this.name = name;
        this.productTypeImg = productTypeImg;
        this.description = description;
    }


    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getProductTypeImg() {
        return productTypeImg;
    }

    public void setProductTypeImg(int productTypeImg) {
        this.productTypeImg = productTypeImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
