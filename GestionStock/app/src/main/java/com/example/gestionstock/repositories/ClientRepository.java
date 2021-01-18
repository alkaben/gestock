package com.example.gestionstock.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Update;

import com.example.gestionstock.CustomRoomDatabase;
import com.example.gestionstock.dao.ClientDao;
import com.example.gestionstock.models.Client;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ClientRepository {
    private ClientDao clientDao;
    private LiveData<List<Client>> clientsLiveData;

    public ClientRepository(Application application) {
        CustomRoomDatabase database = CustomRoomDatabase.getDatabase(application);
        clientDao = database.clientDao();
        clientsLiveData = clientDao.getAllClients();
    }

    public LiveData<List<Client>> getClientsLiveData() {
        return clientsLiveData;
    }

    public void insert(Client client) {
        new InsertAsyncTask(clientDao).execute(client);
    }

    public void update(Client client) {
        new UpdateAsyncTask(clientDao).execute(client);
    }

    public void delete(Client client) {
        new DeleteAsyncTask(clientDao).execute(client);
    }

    public void getClientById(Long id, AsyncResponse<Client> asyncResponse) {
        new GetClientByIdAsyncTask(clientDao, asyncResponse).execute(id);
    }

    private static class GetClientByIdAsyncTask extends AsyncTask<Long, Void, Client> {
        private ClientDao clientAsyncDao;
        private AsyncResponse<Client> delegate;

        public GetClientByIdAsyncTask(ClientDao clientAsyncDao, AsyncResponse<Client> delegate) {
            this.delegate = delegate;
            this.clientAsyncDao = clientAsyncDao;
        }

        @Override
        protected Client doInBackground(Long... longs) {
            return clientAsyncDao.getClientById(longs[0]);
        }

        @Override
        protected void onPostExecute(Client client) {
            delegate.processFinish(client);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Client, Void, Void> {
        private ClientDao clientAsyncDao;

        InsertAsyncTask(ClientDao clientDao) {
            clientAsyncDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientAsyncDao.insert(clients[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Client, Void, Void> {
        private ClientDao clientAsyncDao;

        DeleteAsyncTask(ClientDao clientDao) {
            clientAsyncDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientAsyncDao.delete(clients[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Client, Void, Void> {
        private ClientDao clientAsyncDao;

        UpdateAsyncTask(ClientDao clientDao) {
            clientAsyncDao = clientDao;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            clientAsyncDao.update(clients[0]);
            return null;
        }
    }
}
