package com.example.shopmiphamapp.Database.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "user", indices = {@Index(value = "username", unique = true)})
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String username;
    private String password;
    private String imgFace;
    private int gender;
    private String name;
    private String phoneNumber;
    private String address;

    public User(String id, String username, String password, String imgFace, int gender, String name, String phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.imgFace = imgFace;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Ignore
    public User(String username, String password, String imgFace, int gender, String name, String phoneNumber, String address) {
        this.username = username;
        this.password = password;
        this.imgFace = imgFace;
        this.gender = gender;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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
