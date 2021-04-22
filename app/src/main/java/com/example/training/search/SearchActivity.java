package com.example.training.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.training.R;
import com.example.training.realm.ProductModel;

import io.realm.Realm;

public class SearchActivity extends AppCompatActivity implements SearchView {
    SearchPresenter presenter;
    Realm realm;
    EditText searchText;
    EditText name;
    EditText stockNumber;
    EditText variant;
    EditText price;
    EditText onHandStockQuantity;
    Button searchButton;
    Button updateButton;
    Button deleteButton;
    ProductModel searchedProduct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        renderView();
        realm = Realm.getDefaultInstance();
        presenter = new SearchPresenter(this, realm);

        searchButton.setOnClickListener(view -> {
            presenter.searchItem(searchText.getText().toString());
        });

        updateButton.setOnClickListener(view -> {
            onUpdateItem();
        });

        deleteButton.setOnClickListener(view -> {
            presenter.deleteItem(searchedProduct);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void renderView() {
        searchText = findViewById(R.id.et_search_text);
        name = findViewById(R.id.et_search_name);
        stockNumber = findViewById(R.id.et_search_stock_number);
        variant = findViewById(R.id.et_search_variant);
        price = findViewById(R.id.et_search_price);
        onHandStockQuantity = findViewById(R.id.et_search_on_hand_quantity);
        searchButton = findViewById(R.id.bt_search_item);
        updateButton = findViewById(R.id.bt_update);
        deleteButton = findViewById(R.id.bt_delete);
    }

    private void onUpdateItem() {
        String itemName = name.getText().toString();
        String itemStockNumber = stockNumber.getText().toString();
        String itemVariant = variant.getText().toString();
        String itemPrice = price.getText().toString();
        String itemStockOnHand = onHandStockQuantity.getText().toString();

        presenter.updateItem(searchedProduct, itemName, itemStockNumber, itemVariant, itemPrice,
                itemStockOnHand);
    }

    @Override
    public void searchItemSuccess(ProductModel productModel, String successMessage) {
        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
        name.setText(productModel.getName());
        stockNumber.setText(String.valueOf(productModel.getStockNumber()));
        variant.setText(productModel.getVariant());
        price.setText(String.valueOf(productModel.getPrice()));
        onHandStockQuantity.setText(String.valueOf(productModel.getOnHandStockQuantity()));

        searchedProduct = productModel;
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteItemSuccess(String successMessage) {
        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateItemSuccess(String successMessage) {
        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
    }
}