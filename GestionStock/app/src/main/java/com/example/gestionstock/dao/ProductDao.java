package com.example.gestionstock.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestionstock.models.Client;
import com.example.gestionstock.models.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Update
    void update(Product product);

    @Query("select * from products order by id desc")
    LiveData<List<Product>> getAllProducts();

    @Query("select * from products where id = :id")
    Product getProductById(Long id);
}