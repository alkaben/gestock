package com.example.gestionstock.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.gestionstock.CustomRoomDatabase;
import com.example.gestionstock.dao.UserDao;
import com.example.gestionstock.models.User;

import io.reactivex.Maybe;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        CustomRoomDatabase roomDatabase = CustomRoomDatabase.getDatabase(application);
        userDao = roomDatabase.userDao();
    }

    public void insertUser(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public Maybe<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userAsyncDao;

        InsertAsyncTask(UserDao userAsyncDao) {
            this.userAsyncDao = userAsyncDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            this.userAsyncDao.insert(users[0]);
            return null;
        }
    }
}
