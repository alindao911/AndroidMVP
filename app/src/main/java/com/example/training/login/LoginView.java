package com.example.training.login;

public interface LoginView {
    void onLoginSuccess();

    void onLoginError(String errorMessage);
}
