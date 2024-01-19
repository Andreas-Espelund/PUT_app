package com.example.put_app.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.put_app.util.PUTApplication;
import com.example.put_app.util.Person;
import com.example.put_app.databinding.ActivityLoginBinding;
import com.example.put_app.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInsanceState) {
        super.onCreate(savedInsanceState);

        // init viewmodel
        PUTApplication app = (PUTApplication) getApplication();

        viewModel = new LoginViewModel(app.getRepository());


        viewModel.getData().observe(this, this::handleAuthState);


        // init binding and views
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText username = binding.usernameInput;
        EditText password = binding.passwordInput;
        Button loginButton = binding.loginButton;

        loginButton.setOnClickListener(v -> viewModel.logIn(username.getText().toString(), password.getText().toString()));
    }

    private void handleAuthState(Person user) {
        if (user != null) {
            Log.d("AUTH", "LOGGED IN:" + user.toString());
            PUTApplication app = (PUTApplication) getApplication();
            app.setCurrentUser(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    }

}

