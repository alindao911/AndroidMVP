package com.example.training.home;

import com.example.training.realm.ProductModel;

import java.util.List;

public interface HomeView {
    void showProgress();

    void hideProgress();

    void onError(String errorMessage);

    void getAllItemsSuccess(List<ProductModel> productModels);

    void addItemSuccess(String successMessage);
}
