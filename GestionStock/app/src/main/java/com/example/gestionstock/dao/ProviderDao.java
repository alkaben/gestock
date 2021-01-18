package com.example.gestionstock.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionstock.models.Client;
import com.example.gestionstock.models.Provider;

import java.util.List;

@Dao
public interface ProviderDao {
    @Insert
    void insert(Provider provider);

    @Delete
    void delete(Provider provider);

    @Update
    void update(Provider provider);

    @Query("select * from providers order by id desc")
    LiveData<List<Provider>> getAllProviders();

    @Query("select * from providers where id = :id")
    Provider getProviderById(Long id);
}