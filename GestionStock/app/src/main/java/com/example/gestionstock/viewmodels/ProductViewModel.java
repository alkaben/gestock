package com.example.gestionstock.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gestionstock.models.Client;
import com.example.gestionstock.models.Product;
import com.example.gestionstock.repositories.ClientRepository;
import com.example.gestionstock.repositories.ProductRepository;
import com.example.gestionstock.utils.AsyncResponse;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;
    private LiveData<List<Product>> allProducts;
    private Product product;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        allProducts = productRepository.getProductsLiveData();
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insert(Product product) {
        productRepository.insert(product);
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

    public void getProductById(Long id, AsyncResponse<Product> asyncResponse) {
        productRepository.getProductById(id, asyncResponse);
    }
}