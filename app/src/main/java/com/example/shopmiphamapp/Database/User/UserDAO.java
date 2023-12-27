package com.example.shopmiphamapp.Database.User;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getListUser();

    @Query("SELECT * FROM user where username = :username")
    User checkUser(String username);

    @Query("SELECT * FROM user where username = :username")
    User getUser(String username);

    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Query("DELETE FROM sqlite_sequence WHERE name='user'")
    void resetUserId();
}
