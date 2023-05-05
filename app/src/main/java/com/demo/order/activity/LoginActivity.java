package com.demo.order.activity;

import com.demo.order.BaseBindingActivity;
import com.demo.order.DBHelper;
import com.demo.order.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseBindingActivity<ActivityLoginBinding> {

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

        // Adds a click listener to the login button
        viewBinder.tvLogin.setOnClickListener(view -> {
            String username = viewBinder.etUsername.getText().toString().trim();
            String password = viewBinder.etPassword.getText().toString().trim();
            // Checks if the username and password fields are empty
            if (username.isEmpty()){
                // If the username is empty, shows a toast message
                toast("username is empty");
                return;
            } if (password.isEmpty()){
                // If the password is empty, shows a toast message
                toast("password is empty");
                return;
            }
            // Calls the DBHelper class to check if the username and password match an existing account
            if (DBHelper.getHelper().login(username,password)){
                // If the login is successful, starts the MainActivity and finishes the LoginActivity
                startActivity(MainActivity.class);
                toast("login successful");
                finish();
            }else {
                // If the login fails, shows a toast message
                toast("wrong user name or password");
            }
        });

        // Adds a click listener to the sign up button
        viewBinder.tvSignUp.setOnClickListener(view -> {
            // Starts the SignActivity
            startActivity(SignActivity.class);
        });
    }
}
