package com.example.gestionstock.viewmodels;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gestionstock.models.User;
import com.example.gestionstock.repositories.UserRepository;
import com.example.gestionstock.utils.AESEncrypter;

import java.lang.ref.WeakReference;
import java.util.Optional;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private MutableLiveData<User> userLiveData;

    private static final String TAG = "AuthenticationViewModel";
    public static class UserAlreadyExistsException extends Exception {}

    public static class InvalidPasswordException extends Exception {
    }

    public static class UserDoesNotExistException extends Throwable {
    }

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        userLiveData = new MutableLiveData<User>();
        userRepository = new UserRepository(application);
    }

    public void register(String name, String email, String password) throws UserAlreadyExistsException {
        User user = userRepository.getUserByEmail(email).subscribeOn(Schedulers.io()).blockingGet();
        if (user != null) {
            throw new UserAlreadyExistsException();
        }
        try {
            user = new User(name, email, AESEncrypter.encrypt(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.insertUser(user);
        SharedPreferences preferences = getApplication().getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("user_id", user.getId());
        editor.commit();
        userLiveData.postValue(user);
    }

    public void login(String email, String password) throws Exception, UserDoesNotExistException {
        User user = userRepository.getUserByEmail(email).subscribeOn(Schedulers.io()).blockingGet();
        if (user == null) throw new UserDoesNotExistException();
        if (!password.equals(AESEncrypter.decrypt(user.getPassword()))) throw new InvalidPasswordException();

        SharedPreferences preferences = getApplication().getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("user_id", user.getId());
        editor.commit();
        userLiveData.postValue(user);
    }

    public void logout() {
        SharedPreferences preferences = getApplication().getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("user_id");
        editor.commit();
        userLiveData.postValue(null);
    }

    public boolean isUserLogin() {
        SharedPreferences preferences = getApplication().getApplicationContext().getSharedPreferences("MyPref", 0);
        Long userId = preferences.getLong("user_id", -1);
        return userId != -1;
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
