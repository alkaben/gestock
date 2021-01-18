package com.example.gestionstock;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestionstock.dao.ClientDao;
import com.example.gestionstock.dao.ProductDao;
import com.example.gestionstock.dao.ProviderDao;
import com.example.gestionstock.dao.UserDao;
import com.example.gestionstock.models.Client;
import com.example.gestionstock.models.Product;
import com.example.gestionstock.models.Provider;
import com.example.gestionstock.models.User;

@Database(entities = {User.class, Client.class, Product.class, Provider.class}, version = 4, exportSchema = false)
public abstract class CustomRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ClientDao clientDao();
    public abstract ProductDao productDao();
    public abstract ProviderDao providerDao();

    private static CustomRoomDatabase INSTANCE;
    public static CustomRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CustomRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), CustomRoomDatabase.class, "database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
