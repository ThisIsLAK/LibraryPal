package com.lak.prm392.groupproject.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.data.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private Spinner roleSpinner;
    private Button registerButton;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRepository = new UserRepository(this);

        nameEditText = findViewById(R.id.editTextNameRegister);
        emailEditText = findViewById(R.id.editTextEmailRegister);
        passwordEditText = findViewById(R.id.editTextPasswordRegister);
        roleSpinner = findViewById(R.id.spinnerRole);
        registerButton = findViewById(R.id.buttonRegister);

        // Gán role vào spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        registerButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String role = roleSpinner.getSelectedItem().toString().toLowerCase();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                boolean exists = userRepository.isEmailExists(email);
                runOnUiThread(() -> {
                    if (exists) {
                        Toast.makeText(this, "Email đã được đăng ký!", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User();
                        user.name = name;
                        user.email = email;
                        user.password = password;
                        user.role = role;

                        new Thread(() -> {
                            userRepository.insertUser(user);
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, LoginActivity.class));
                                finish();
                            });
                        }).start();
                    }
                });
            }).start();
        });
    }
}
