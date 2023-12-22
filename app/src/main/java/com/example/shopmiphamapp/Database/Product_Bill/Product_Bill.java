package com.example.shopmiphamapp.Database.Product_Bill;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_bill")
public class Product_Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int billId;
    private int productId;
    private int quantity;

    public Product_Bill(int billId, int productId, int quantity) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
