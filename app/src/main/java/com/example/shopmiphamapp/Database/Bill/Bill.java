package com.example.shopmiphamapp.Database.Bill;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "bill")
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long purchaseDate;
    private float totalMonney;
    private int userId;

    public Bill(long purchaseDate, float totalMonney, int userId) {
        this.purchaseDate = purchaseDate;
        this.totalMonney = totalMonney;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getTotalMonney() {
        return totalMonney;
    }

    public void setTotalMonney(float totalMonney) {
        this.totalMonney = totalMonney;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
