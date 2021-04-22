package com.example.training.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductModel extends RealmObject {
    @PrimaryKey
    long id;
    String name;
    long stockNumber;
    String variant;
    double price;
    long onHandStockQuantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(long stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getOnHandStockQuantity() {
        return onHandStockQuantity;
    }

    public void setOnHandStockQuantity(long onHandStockQuantity) {
        this.onHandStockQuantity = onHandStockQuantity;
    }
}
