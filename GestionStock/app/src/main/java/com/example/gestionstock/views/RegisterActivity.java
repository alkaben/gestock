package com.example.gestionstock.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.utils.Ensure;
import com.example.gestionstock.viewmodels.AuthenticationViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    public TextInputLayout name;
    public TextInputLayout email;
    public TextInputLayout password;
    public Button registerButton;
    public TextView loginLink;
    public AuthenticationViewModel authenticationViewModel;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        authenticationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AuthenticationViewModel.class);
        if(authenticationViewModel.isUserLogin()) {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        authenticationViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        registerButton = findViewById(R.id.button_register);
        loginLink = findViewById(R.id.tv_login);

        registerButton.setOnClickListener(v -> {
            String nameTxt = name.getEditText().getText().toString();
            String emailTxt = email.getEditText().getText().toString();
            String passwordTxt = password.getEditText().getText().toString();

            Ensure.reset();
            if (!Ensure.ensure(nameTxt, Ensure.NOT_BLANK)) name.setError("Le nom est requis");
            if (!Ensure.ensure(emailTxt, Ensure.NOT_BLANK | Ensure.IS_EMAIL)) email.setError("L'email soumis est invalide");
            if (!Ensure.ensure(passwordTxt, Ensure.NOT_BLANK | Ensure.LENGTH)) password.setError("Le mot de passe doit faire au moins 8 caracteres");
            if(!Ensure.getCurrentAcc()) {
                Ensure.reset();
                return;
            }

            try {
                authenticationViewModel.register(nameTxt, emailTxt, passwordTxt);
                Ensure.reset();
            } catch (AuthenticationViewModel.UserAlreadyExistsException e) {
                email.setError("Un utilisateur existe deja avec cet email");
            }
        });

        loginLink.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
    }
}