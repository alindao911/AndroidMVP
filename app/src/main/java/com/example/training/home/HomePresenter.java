package com.example.training.home;

import android.util.Log;

import com.example.training.realm.ProductModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class HomePresenter {
    private final HomeView view;
    Realm realm;
    RealmAsyncTask realmAsyncTask;

    public HomePresenter(HomeView view, Realm realm, RealmAsyncTask realmAsyncTask) {
        this.view = view;
        this.realm = realm;
        this.realmAsyncTask = realmAsyncTask;
    }

    public void getAllItems() {
        view.showProgress();
        List<ProductModel> dataModels = realm.where(ProductModel.class).findAll();
        view.getAllItemsSuccess(dataModels);
        view.hideProgress();
    }

    public void addItem(ProductModel productModel) {
        realmAsyncTask = realm.executeTransactionAsync(realm -> realm.copyToRealm(productModel), () -> {
            view.addItemSuccess("Item added successfully");
            realmAsyncTask.cancel();
        }, error -> {
            view.onError("Something went wrong: " + error);
        });
    }
}
