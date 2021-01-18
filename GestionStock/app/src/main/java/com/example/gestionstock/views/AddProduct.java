package com.example.gestionstock.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Product;
import com.example.gestionstock.utils.Ensure;
import com.google.android.material.textfield.TextInputLayout;

public class AddProduct extends AppCompatActivity {

    public static final String EXTRA = "com.example.gestionstock.products.REPLY";

    private TextInputLayout reference;
    private TextInputLayout name;
    private TextInputLayout qty;
    private Button submitButton;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        reference = findViewById(R.id.et_reference);
        name = findViewById(R.id.et_name);
        qty = findViewById(R.id.et_qty);
        submitButton = findViewById(R.id.submit_button);
        product = new Product();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = (Product) bundle.getSerializable(ProductFragment.EXTRA);
            if (product != null) {
                reference.getEditText().setText(product.getReference());
                name.getEditText().setText(product.getName());
                qty.getEditText().setText(product.getQty().toString());
                submitButton.setText("Modifier");
            }
        }

        submitButton.setOnClickListener(v -> {
            String nameTxt = name.getEditText().getText().toString();
            String referenceTxt = reference.getEditText().getText().toString();
            String qtyTxt = qty.getEditText().getText().toString();
            if (!Ensure.ensure(nameTxt, Ensure.NOT_BLANK)) name.setError("Le nom est requis");
            if (!Ensure.ensure(referenceTxt, Ensure.NOT_BLANK)) reference.setError("La reference est requis");
            if (!Ensure.ensure(qtyTxt, Ensure.NOT_BLANK)) qty.setError("La quantite est requis");

            if(!Ensure.getCurrentAcc()) {
                Ensure.reset();
                return;
            }
            product.setName(name.getEditText().getText().toString());
            product.setReference(reference.getEditText().getText().toString());
            product.setQty(Integer.parseInt(qty.getEditText().getText().toString()));
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA, product);
            setResult(RESULT_OK, replyIntent);
            Ensure.reset();
            finish();
        });
    }
}