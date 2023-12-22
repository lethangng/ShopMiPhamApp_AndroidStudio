package com.example.shopmiphamapp.Database.Product;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int productId;
    private String name;
    private String description;
    private int imgProduct;
    private int imgProductListId;
    private int price;
    private int sold;
    private int categoryId;
    private int productTypeId;
    private int quantity;

    public Product(String name, String description, int imgProduct, int imgProductListId, int price, int sold, int categoryId, int productTypeId, int quantity) {
        this.name = name;
        this.description = description;
        this.imgProduct = imgProduct;
        this.imgProductListId = imgProductListId;
        this.price = price;
        this.sold = sold;
        this.categoryId = categoryId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public int getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(int imgProduct) {
        this.imgProduct = imgProduct;
    }

    public int getImgProductListId() {
        return imgProductListId;
    }

    public void setImgProductListId(int imgProductListId) {
        this.imgProductListId = imgProductListId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
