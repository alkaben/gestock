package com.example.gestionstock.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestionstock.models.Client;
import com.example.gestionstock.models.User;
import com.example.gestionstock.repositories.ClientRepository;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ClientViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;
    private LiveData<List<Client>> allClients;
    private Client client;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        allClients = clientRepository.getClientsLiveData();
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public void insert(Client client) {
        clientRepository.insert(client);
    }

    public void update(Client client) {
        clientRepository.update(client);
    }

    public void delete(Client client) {
        clientRepository.delete(client);
    }

    public void getClientById(Long id, AsyncResponse<Client> asyncResponse) {
        clientRepository.getClientById(id, asyncResponse);
    }
}
