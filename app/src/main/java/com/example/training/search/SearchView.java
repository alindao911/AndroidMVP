package com.example.training.search;

import com.example.training.realm.ProductModel;

public interface SearchView {
    void searchItemSuccess(ProductModel productModel, String successMessage);

    void onError(String errorMessage);

    void deleteItemSuccess(String successMessage);

    void updateItemSuccess(String successMessage);
}
