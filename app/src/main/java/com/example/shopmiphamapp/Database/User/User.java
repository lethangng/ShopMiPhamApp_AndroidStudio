package com.example.shopmiphamapp.Database.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "user", indices = {@Index(value = "username", unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String username;
    private String password;
    private String imgFace;
    private int gender;
    private String name;
    private String phoneNumber;
    private String address;

    @Ignore
    public User(int userId, String username, String password, String imgFace, int gender, String name, String phoneNumber, String address) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.imgFace = imgFace;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public User(String username, String password, String imgFace, int gender, String name, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.imgFace = imgFace;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgFace() {
        return imgFace;
    }

    public void setImgFace(String imgFace) {
        this.imgFace = imgFace;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
