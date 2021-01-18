package com.example.gestionstock.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gestionstock.CustomRoomDatabase;
import com.example.gestionstock.dao.ProviderDao;
import com.example.gestionstock.models.Provider;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ProviderRepository {
    private ProviderDao providerDao;
    private LiveData<List<Provider>> providersLiveData;

    public ProviderRepository(Application application) {
        CustomRoomDatabase database = CustomRoomDatabase.getDatabase(application);
        providerDao = database.providerDao();
        providersLiveData = providerDao.getAllProviders();
    }

    public LiveData<List<Provider>> getProvidersLiveData() {
        return providersLiveData;
    }

    public void insert(Provider provider) {
        new InsertAsyncTask(providerDao).execute(provider);
    }

    public void update(Provider provider) {
        new UpdateAsyncTask(providerDao).execute(provider);
    }

    public void delete(Provider provider) {
        new DeleteAsyncTask(providerDao).execute(provider);
    }

    public void getProviderById(Long id, AsyncResponse<Provider> asyncResponse) {
        new GetProviderByIdAsyncTask(providerDao, asyncResponse).execute(id);
    }

    private static class GetProviderByIdAsyncTask extends AsyncTask<Long, Void, Provider> {
        private ProviderDao providerAsyncDao;
        private AsyncResponse<Provider> delegate;

        public GetProviderByIdAsyncTask(ProviderDao providerAsyncDao, AsyncResponse<Provider> delegate) {
            this.delegate = delegate;
            this.providerAsyncDao = providerAsyncDao;
        }

        @Override
        protected Provider doInBackground(Long... longs) {
            return providerAsyncDao.getProviderById(longs[0]);
        }

        @Override
        protected void onPostExecute(Provider provider) {
            delegate.processFinish(provider);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Provider, Void, Void> {
        private ProviderDao providerAsyncDao;

        InsertAsyncTask(ProviderDao providerDao) {
            providerAsyncDao = providerDao;
        }

        @Override
        protected Void doInBackground(Provider... providers) {
            providerAsyncDao.insert(providers[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Provider, Void, Void> {
        private ProviderDao providerAsyncDao;

        DeleteAsyncTask(ProviderDao providerDao) {
            providerAsyncDao = providerDao;
        }

        @Override
        protected Void doInBackground(Provider... providers) {
            providerAsyncDao.delete(providers[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Provider, Void, Void> {
        private ProviderDao providerAsyncDao;

        UpdateAsyncTask(ProviderDao providerDao) {
            providerAsyncDao = providerDao;
        }

        @Override
        protected Void doInBackground(Provider... providers) {
            providerAsyncDao.update(providers[0]);
            return null;
        }
    }
}
