package com.example.gestionstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gestionstock.adapters.ClientListAdapter;
import com.example.gestionstock.viewmodels.AuthenticationViewModel;
import com.example.gestionstock.views.ClientFragment;
import com.example.gestionstock.views.LoginActivity;
import com.example.gestionstock.views.ProductFragment;
import com.example.gestionstock.views.ProviderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    public TextView logoutButton;
    private AuthenticationViewModel authenticationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authenticationViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AuthenticationViewModel.class);
        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: Working");
            authenticationViewModel.logout();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        });
        loadFragment(new ClientFragment());
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.item1:
                fragment = new ClientFragment();
                break;
            case R.id.item2:
                fragment = new ProductFragment();
                break;
            case R.id.item3:
                fragment = new ProviderFragment();
                break;
        }
        return loadFragment(fragment);
    }
}