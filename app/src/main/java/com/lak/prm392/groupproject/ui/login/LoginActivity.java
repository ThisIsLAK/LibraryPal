package com.lak.prm392.groupproject.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.data.repository.UserRepository;
import com.lak.prm392.groupproject.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            new Thread(() -> {
                User user = userRepository.login(email, password);
                runOnUiThread(() -> {
                    if (user != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("user_role", user.role);
                        intent.putExtra("user_id", user.id);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
    }
}

