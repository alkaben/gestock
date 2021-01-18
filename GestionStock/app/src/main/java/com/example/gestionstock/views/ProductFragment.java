package com.example.gestionstock.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestionstock.R;
import com.example.gestionstock.adapters.ProductListAdapter;
import com.example.gestionstock.models.Product;
import com.example.gestionstock.viewmodels.ProductViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;

public class ProductFragment extends Fragment implements View.OnClickListener {
    public static final int NEW_CLIENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CLIENT_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA = "com.example.gestionstock.products.INITIAL_CLIENT";
    private ProductViewModel productViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        productViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ProductViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.product_recyclerview);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.product_fab);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddProduct.class);
            startActivityForResult(intent, NEW_CLIENT_ACTIVITY_REQUEST_CODE);
        });

        final ProductListAdapter productListAdapter = new ProductListAdapter(view.getContext(), this);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        productViewModel.getAllProducts().observe(this, products -> {
            productListAdapter.setProducts(products);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CLIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Product product = (Product) data.getSerializableExtra(AddProduct.EXTRA);
            productViewModel.insert(product);
        } else if (requestCode == UPDATE_CLIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Product product = (Product) data.getSerializableExtra(AddProduct.EXTRA);
            productViewModel.update(product);
        }
    }

    @Override
    public void onClick(View v) {
        String items[] = new String[]{"Modifier", "Supprimer"};
        new MaterialAlertDialogBuilder(v.getContext())
                .setItems(items, (dialog, which) -> {
                    switch (which) {
                        case 0: {
                            TextView idTextView = v.findViewById(R.id.tv_id);
                            System.out.print("TEXT VIEW ID " + idTextView);
                            productViewModel.getProductById(Long.parseLong(idTextView.getText().toString()), object -> {
                                Intent i = new Intent(getActivity().getApplicationContext(), AddProduct.class);
                                i.putExtra(EXTRA, object);
                                startActivityForResult(i, UPDATE_CLIENT_ACTIVITY_REQUEST_CODE);
                            });
                        }
                        break;
                        case 1: {
                            TextView idTextView = v.findViewById(R.id.tv_id);
                            System.out.print("TEXT VIEW ID " + idTextView);
                            productViewModel.getProductById(Long.parseLong(idTextView.getText().toString()), object -> {
                                new MaterialAlertDialogBuilder(v.getContext())
                                        .setTitle("Etes-vous sur de vouloir supprimer ce product?")
                                        .setMessage("La suppression d'un product entraine la suppression de toutes les informations le concernant. Prenez-soin de sauvegarder toute information indispensable.")
                                        .setNegativeButton("Annuler", (dialog1, which1) -> {
                                            try {
                                                finalize();
                                            } catch (Throwable throwable) {
                                                throwable.printStackTrace();
                                            }
                                        })
                                        .setPositiveButton("Supprimer", (dialog1, which1) -> {
                                            productViewModel.delete(object);
                                        }).show();
                            });
                        }
                        break;
                    }
                }).show();
    }
}