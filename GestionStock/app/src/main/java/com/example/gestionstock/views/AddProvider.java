package com.example.gestionstock.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Provider;
import com.example.gestionstock.utils.Ensure;
import com.google.android.material.textfield.TextInputLayout;

public class AddProvider extends AppCompatActivity {

    public static final String EXTRA = "com.example.gestionstock.providers.REPLY";

    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout email;
    private TextInputLayout phoneNumber;
    private Button submitButton;
    private Provider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_provider);

        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        email = findViewById(R.id.et_email);
        phoneNumber = findViewById(R.id.et_phone_number);
        submitButton = findViewById(R.id.submit_button);
        provider = new Provider();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            provider = (Provider) bundle.getSerializable(ProviderFragment.EXTRA);
            if (provider != null) {
                firstName.getEditText().setText(provider.getFirstName());
                lastName.getEditText().setText(provider.getLastName());
                email.getEditText().setText(provider.getEmail());
                phoneNumber.getEditText().setText(provider.getPhoneNumber());
                submitButton.setText("Modifier");
            }
        }

        submitButton.setOnClickListener(v -> {
            String firstNameTxt = firstName.getEditText().getText().toString();
            String lastNameTxt = lastName.getEditText().getText().toString();
            String phoneNumberTxt = phoneNumber.getEditText().getText().toString();
            String emailTxt = email.getEditText().getText().toString();
            if (!Ensure.ensure(firstNameTxt, Ensure.NOT_BLANK)) firstName.setError("Le nom est requis");
            if (!Ensure.ensure(lastNameTxt, Ensure.NOT_BLANK)) firstName.setError("Le prenom est requis");
            if (!Ensure.ensure(emailTxt, Ensure.NOT_BLANK)) firstName.setError("L'email est requis");
            if (!Ensure.ensure(phoneNumberTxt, Ensure.NOT_BLANK)) firstName.setError("Le numero de telephone est requis");

            if(!Ensure.getCurrentAcc()) {
                Ensure.reset();
                return;
            }
            provider.setFirstName(firstName.getEditText().getText().toString());
            provider.setLastName(lastName.getEditText().getText().toString());
            provider.setEmail(email.getEditText().getText().toString());
            provider.setPhoneNumber(phoneNumber.getEditText().getText().toString());
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA, provider);
            setResult(RESULT_OK, replyIntent);
            Ensure.reset();
            finish();
        });
    }
}