package com.example.training.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.training.R;
import com.example.training.realm.ProductModel;
import com.example.training.search.SearchActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private RecyclerView list;
    ProgressBar progressBar;
    Realm realm;
    RealmAsyncTask realmAsyncTask;
    Button btnInsertItem;
    Button btnSearchItem;
    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView();
        init();

        realm = Realm.getDefaultInstance();
        presenter = new HomePresenter(this, realm, realmAsyncTask);
        presenter.getAllItems();

        btnInsertItem.setOnClickListener(view -> {
            showInsertDialog();
        });

        btnSearchItem.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        presenter.getAllItems();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (realmAsyncTask != null && realmAsyncTask.isCancelled()) {
            realmAsyncTask.cancel();
        }
    }

    private void renderView() {
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.rv_list);
        progressBar = findViewById(R.id.progress);
        btnInsertItem = findViewById(R.id.bt_insert);
        btnSearchItem = findViewById(R.id.bt_search);
    }

    private void init() {
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showInsertDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_item, null);
        al.setView(view);

        final EditText name= view.findViewById(R.id.et_name);
        final EditText stockNumber = view.findViewById(R.id.et_stock_number);
        final EditText variant = view.findViewById(R.id.et_variant);
        final EditText price = view.findViewById(R.id.et_price);
        final EditText onHandQuantity = view.findViewById(R.id.et_stock_on_hand);
        Button save = view.findViewById(R.id.bt_save);
        AlertDialog alertDialog = al.show();

        save.setOnClickListener(v -> {
            alertDialog.hide();

            final ProductModel dataModel = new ProductModel();
            Number current_id = realm.where(ProductModel.class).max("id");
            long nextId;
            if (current_id == null)
                nextId = 1;
            else
                nextId = current_id.intValue()+1;

            dataModel.setId(nextId);
            dataModel.setName(name.getText().toString());
            dataModel.setStockNumber(Integer.parseInt(stockNumber.getText().toString()));
            dataModel.setVariant(variant.getText().toString());
            dataModel.setPrice(Double.parseDouble(price.getText().toString()));
            dataModel.setOnHandStockQuantity(Integer.parseInt(onHandQuantity.getText().toString()));

            presenter.addItem(dataModel);
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAllItemsSuccess(List<ProductModel> productModels) {
        HomeAdapter adapter = new HomeAdapter(getApplicationContext(), productModels,
                item -> Toast.makeText(getApplicationContext(), item.getName(), Toast.LENGTH_SHORT)
                        .show());
        list.setAdapter(adapter);
    }

    @Override
    public void addItemSuccess(String successMessage) {
        Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show();
        presenter.getAllItems();
    }
}