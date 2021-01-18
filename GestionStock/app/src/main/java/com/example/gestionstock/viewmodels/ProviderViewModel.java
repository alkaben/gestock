package com.example.gestionstock.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestionstock.models.Provider;
import com.example.gestionstock.repositories.ProviderRepository;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ProviderViewModel extends AndroidViewModel {
    private ProviderRepository providerRepository;
    private LiveData<List<Provider>> allProviders;
    private Provider provider;

    public ProviderViewModel(@NonNull Application application) {
        super(application);
        providerRepository = new ProviderRepository(application);
        allProviders = providerRepository.getProvidersLiveData();
    }

    public LiveData<List<Provider>> getAllProviders() {
        return allProviders;
    }

    public void insert(Provider provider) {
        providerRepository.insert(provider);
    }

    public void update(Provider provider) {
        providerRepository.update(provider);
    }

    public void delete(Provider provider) {
        providerRepository.delete(provider);
    }

    public void getProviderById(Long id, AsyncResponse<Provider> asyncResponse) {
        providerRepository.getProviderById(id, asyncResponse);
    }
}