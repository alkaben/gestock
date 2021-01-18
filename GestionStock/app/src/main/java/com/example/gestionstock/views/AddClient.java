package com.example.gestionstock.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Client;
import com.example.gestionstock.utils.Ensure;
import com.google.android.material.textfield.TextInputLayout;

public class AddClient extends AppCompatActivity {

    public static final String EXTRA = "com.example.gestionstock.clients.REPLY";

    private TextInputLayout firstName;
    private TextInputLayout lastName;
    private TextInputLayout email;
    private TextInputLayout phoneNumber;
    private Button submitButton;
    private Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        email = findViewById(R.id.et_email);
        phoneNumber = findViewById(R.id.et_phone_number);
        submitButton = findViewById(R.id.submit_button);
        client = new Client();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            client = (Client) bundle.getSerializable(ClientFragment.EXTRA);
            if (client != null) {
                firstName.getEditText().setText(client.getFirstName());
                lastName.getEditText().setText(client.getLastName());
                email.getEditText().setText(client.getEmail());
                phoneNumber.getEditText().setText(client.getPhoneNumber());
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
            client.setFirstName(firstName.getEditText().getText().toString());
            client.setLastName(lastName.getEditText().getText().toString());
            client.setEmail(email.getEditText().getText().toString());
            client.setPhoneNumber(phoneNumber.getEditText().getText().toString());
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA, client);
            setResult(RESULT_OK, replyIntent);
            Ensure.reset();
            finish();
        });
    }
}