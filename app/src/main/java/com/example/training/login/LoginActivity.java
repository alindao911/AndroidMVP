package com.example.training.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.training.R;
import com.example.training.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    EditText email;
    EditText password;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView();

        LoginPresenter presenter = new LoginPresenter(this);

        btnLogin.setOnClickListener(v -> {
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();

            if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword)) {
                onLoginError("Field(s) is required!");
            } else {
                presenter.doLogin(strEmail, strPassword);
            }
        });
    }

    public void renderView() {
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.bt_login);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        );
    }

    @Override
    public void onLoginError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
