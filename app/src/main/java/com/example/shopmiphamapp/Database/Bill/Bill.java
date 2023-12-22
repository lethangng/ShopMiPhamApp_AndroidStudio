package com.example.shopmiphamapp.Database.Bill;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity(tableName = "bill")
public class Bill {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long purchaseDate;
    private String deliveryAddress;
    private int totalMonney;
    private int userId;

    public Bill(long purchaseDate, String deliveryAddress, int totalMonney, int userId) {
        this.purchaseDate = purchaseDate;
        this.deliveryAddress = deliveryAddress;
        this.totalMonney = totalMonney;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public long getPurchaseDate() {
        return purchaseDate;
    }
//    @TypeConverter
//    public Date getPurcahseDate2() {
//        return new Date(purchaseDate);
//    }
//    public void setPurchaseDate2(Date purchaseDate) {
//        this.purchaseDate = purchaseDate.getTime();
//    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getTotalMonney() {
        return totalMonney;
    }

    public void setTotalMonney(int totalMonney) {
        this.totalMonney = totalMonney;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
