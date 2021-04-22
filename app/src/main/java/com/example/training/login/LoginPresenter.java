package com.example.training.login;

public class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void doLogin(String email, String password) {
        //Dummy credentials
        //Email: test@test.com
        //Password: 1234567

        if (email.equals("test@test.com") && password.equals("1234567")) {
            view.onLoginSuccess();
        } else {
            view.onLoginError("Invalid credentials!");
        }
    }
}
