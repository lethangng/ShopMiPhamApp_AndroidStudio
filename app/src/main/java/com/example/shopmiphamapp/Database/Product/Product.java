package com.example.shopmiphamapp.Database.Product;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private String description;
    private String imgUrl;
//    private int imgListId;
    private int price;
    private int sold;
    private int productTypeId;
    private int quantity;

    public Product(@NonNull int id, String name, String description, String imgUrl,
                   int price, int sold, int productTypeId, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.sold = sold;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    @Ignore
    public Product(String name, String description, String imgUrl,
                   int price, int sold, int productTypeId, int quantity) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.price = price;
        this.sold = sold;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
