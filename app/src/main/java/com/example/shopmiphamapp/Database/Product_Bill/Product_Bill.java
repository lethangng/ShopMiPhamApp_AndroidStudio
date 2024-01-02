package com.example.shopmiphamapp.Database.Product_Bill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_bill")
public class Product_Bill {
    @PrimaryKey
    @NonNull
    private String id;
    private String billId;
    private int productId;
    private int quantity;

    public Product_Bill(@NonNull String id, String billId, int productId, int quantity) {
        this.id = id;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Ignore
    public Product_Bill(String billId, int productId, int quantity) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
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
