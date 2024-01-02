package com.example.shopmiphamapp.Database.Bill;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity(tableName = "bill")
public class Bill {
    @PrimaryKey
    @NonNull
    private String id;
    private long purchaseDate;
    private String deliveryAddress;
    private int totalMoney;
    private String userId;
    private String note;
    private String payMethod;

    public Bill(String id, long purchaseDate, String deliveryAddress, int totalMoney, String userId, String payMethod, String note) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.deliveryAddress = deliveryAddress;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.payMethod = payMethod;
        this.note = note;
    }

    @Ignore
    public Bill(long purchaseDate, String deliveryAddress, int totalMoney, String userId, String payMethod, String note) {
        this.purchaseDate = purchaseDate;
        this.deliveryAddress = deliveryAddress;
        this.totalMoney = totalMoney;
        this.userId = userId;
        this.payMethod = payMethod;
        this.note = note;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
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

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
