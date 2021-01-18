package com.example.gestionstock.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.models.User;
import com.example.gestionstock.utils.Ensure;
import com.example.gestionstock.viewmodels.AuthenticationViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextInputLayout password;
    private TextInputLayout email;
    private Button loginButton;
    private AuthenticationViewModel authenticationViewModel;
    private TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.button_login);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        registerLink = findViewById(R.id.tv_register);
        authenticationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AuthenticationViewModel.class);
        if(authenticationViewModel.isUserLogin()) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        authenticationViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        loginButton.setOnClickListener(v -> {
            String emailTxt = email.getEditText().getText().toString();
            String passwordTxt = password.getEditText().getText().toString();
            Ensure.reset();
            if (!Ensure.ensure(emailTxt, Ensure.NOT_BLANK)) email.setError("L'email est requis");
            if (!Ensure.ensure(passwordTxt, Ensure.NOT_BLANK)) password.setError("Le mot de passe est requis");

            if(!Ensure.getCurrentAcc()) {
                Ensure.reset();
                return;
            }

            try {
                authenticationViewModel.login(emailTxt, passwordTxt);
                Ensure.reset();
            } catch (AuthenticationViewModel.UserDoesNotExistException | Exception e) {
                email.setError("Les informations de connexion ne correspondent pas aux donnees stockees");
            }
        });
        registerLink.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
    }


}