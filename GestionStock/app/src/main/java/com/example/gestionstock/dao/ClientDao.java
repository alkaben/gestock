package com.example.gestionstock.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionstock.models.Client;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ClientDao {
    @Insert
    void insert(Client client);

    @Delete
    void delete(Client client);

    @Update
    void update(Client client);

    @Query("select * from clients order by id desc")
    LiveData<List<Client>> getAllClients();

    @Query("select * from clients where id = :id")
    Client getClientById(Long id);
}
