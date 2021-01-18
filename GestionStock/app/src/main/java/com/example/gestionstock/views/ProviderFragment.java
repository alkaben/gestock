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
import com.example.gestionstock.adapters.ProviderListAdapter;
import com.example.gestionstock.models.Provider;
import com.example.gestionstock.viewmodels.ProviderViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;


public class ProviderFragment extends Fragment implements View.OnClickListener {
    public static final int NEW_CLIENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CLIENT_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA = "com.example.gestionstock.clients.INITIAL_CLIENT";
    private ProviderViewModel clientViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        clientViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ProviderViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.client_recyclerview);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.client_fab);
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddProvider.class);
            startActivityForResult(intent, NEW_CLIENT_ACTIVITY_REQUEST_CODE);
        });

        final ProviderListAdapter clientListAdapter = new ProviderListAdapter(view.getContext(), this);
        recyclerView.setAdapter(clientListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        clientViewModel.getAllProviders().observe(this, clients -> {
            clientListAdapter.setProviders(clients);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CLIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Provider client = (Provider) data.getSerializableExtra(AddProvider.EXTRA);
            clientViewModel.insert(client);
        } else if (requestCode == UPDATE_CLIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Provider client = (Provider) data.getSerializableExtra(AddProvider.EXTRA);
            clientViewModel.update(client);
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
                            clientViewModel.getProviderById(Long.parseLong(idTextView.getText().toString()), object -> {
                                Intent i = new Intent(getActivity().getApplicationContext(), AddProvider.class);
                                i.putExtra(EXTRA, object);
                                startActivityForResult(i, UPDATE_CLIENT_ACTIVITY_REQUEST_CODE);
                            });
                        }
                        break;
                        case 1: {
                            TextView idTextView = v.findViewById(R.id.tv_id);
                            System.out.print("TEXT VIEW ID " + idTextView);
                            clientViewModel.getProviderById(Long.parseLong(idTextView.getText().toString()), object -> {
                                new MaterialAlertDialogBuilder(v.getContext())
                                        .setTitle("Etes-vous sur de vouloir supprimer ce client?")
                                        .setMessage("La suppression d'un client entraine la suppression de toutes les informations le concernant. Prenez-soin de sauvegarder toute information indispensable.")
                                        .setNegativeButton("Annuler", (dialog1, which1) -> {
                                            try {
                                                finalize();
                                            } catch (Throwable throwable) {
                                                throwable.printStackTrace();
                                            }
                                        })
                                        .setPositiveButton("Supprimer", (dialog1, which1) -> {
                                            clientViewModel.delete(object);
                                        }).show();
                            });
                        }
                        break;
                    }
                }).show();
    }
}