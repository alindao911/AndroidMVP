package com.example.training.search;

import android.text.TextUtils;

import com.example.training.realm.ProductModel;

import io.realm.Realm;

public class SearchPresenter {
    private final SearchView view;
    Realm realm;

    public SearchPresenter(SearchView view, Realm realm) {
        this.view = view;
        this.realm = realm;
    }

    public void searchItem(String itemId) {
        long id = Long.parseLong(itemId);
        if (!TextUtils.isEmpty(itemId)) {
            final ProductModel dataModel = realm.where(ProductModel.class)
                    .equalTo("id", id)
                    .findFirst();
            if (dataModel != null)
                view.searchItemSuccess(realm.copyFromRealm(dataModel),
                        "Search successfully!");
            else
                view.onError("Item ID: " + itemId + " does not exist.");
        } else
            view.onError("Input Item ID!");
    }

    public void updateItem(ProductModel searchedProduct, String itemName,
                           String itemStockNumber, String itemVariant,
                           String itemPrice, String itemStockOnHand) {
        if (searchedProduct != null) {
            realm.executeTransactionAsync(realm -> {
                searchedProduct.setName(itemName);
                searchedProduct.setStockNumber(Integer.parseInt(itemStockNumber));
                searchedProduct.setVariant(itemVariant);
                searchedProduct.setPrice(Double.parseDouble(itemPrice));
                searchedProduct.setOnHandStockQuantity(Integer.parseInt(itemStockOnHand));
                realm.copyToRealmOrUpdate(searchedProduct);
            }, () -> {
                view.updateItemSuccess("Successfully updated!");
            }, error -> {
                view.onError("Something went wrong!" + error);
            });
        }
        else
            view.onError("Search an item first!");
    }

    public void deleteItem(ProductModel searchedProduct) {
        if (searchedProduct != null) {
            realm.executeTransactionAsync(realm -> {
                ProductModel dataModel = realm.where(ProductModel.class)
                        .equalTo("id", searchedProduct.getId())
                        .findFirst();
                dataModel.deleteFromRealm();
            }, () -> {
                view.deleteItemSuccess("Successfully deleted!");
            }, error -> {
                view.onError("Something went wrong!" + error);
            });
        }
        else
            view.onError("Search an item first!");
    }
}
