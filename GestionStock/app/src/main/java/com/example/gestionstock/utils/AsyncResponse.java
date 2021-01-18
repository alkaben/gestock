package com.example.gestionstock.utils;

public interface AsyncResponse<T> {
    void processFinish(T object);
}
