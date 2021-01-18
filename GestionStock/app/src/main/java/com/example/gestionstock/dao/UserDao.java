package com.example.gestionstock.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gestionstock.models.User;

import io.reactivex.Maybe;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("select * from users where email = (:email)")
    Maybe<User> getUserByEmail(String email);
}
