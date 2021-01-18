package com.example.gestionstock.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gestionstock.CustomRoomDatabase;
import com.example.gestionstock.dao.ProductDao;
import com.example.gestionstock.models.Product;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> productsLiveData;

    public ProductRepository(Application application) {
        CustomRoomDatabase database = CustomRoomDatabase.getDatabase(application);
        productDao = database.productDao();
        productsLiveData = productDao.getAllProducts();
    }

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public void insert(Product product) {
        new InsertAsyncTask(productDao).execute(product);
    }

    public void update(Product product) {
        new UpdateAsyncTask(productDao).execute(product);
    }

    public void delete(Product product) {
        new DeleteAsyncTask(productDao).execute(product);
    }

    public void getProductById(Long id, AsyncResponse<Product> asyncResponse) {
        new GetProductByIdAsyncTask(productDao, asyncResponse).execute(id);
    }

    private static class GetProductByIdAsyncTask extends AsyncTask<Long, Void, Product> {
        private ProductDao productAsyncDao;
        private AsyncResponse<Product> delegate;

        public GetProductByIdAsyncTask(ProductDao productAsyncDao, AsyncResponse<Product> delegate) {
            this.delegate = delegate;
            this.productAsyncDao = productAsyncDao;
        }

        @Override
        protected Product doInBackground(Long... longs) {
            return productAsyncDao.getProductById(longs[0]);
        }

        @Override
        protected void onPostExecute(Product product) {
            delegate.processFinish(product);
        }
    }

    private static class InsertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productAsyncDao;

        InsertAsyncTask(ProductDao productDao) {
            productAsyncDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncDao.insert(products[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productAsyncDao;

        DeleteAsyncTask(ProductDao productDao) {
            productAsyncDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncDao.delete(products[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productAsyncDao;

        UpdateAsyncTask(ProductDao productDao) {
            productAsyncDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productAsyncDao.update(products[0]);
            return null;
        }
    }
}