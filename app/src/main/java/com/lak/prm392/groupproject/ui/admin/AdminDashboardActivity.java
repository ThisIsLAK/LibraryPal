package com.lak.prm392.groupproject.ui.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.adapter.AdminUserAdapter;
import com.lak.prm392.groupproject.adapter.UserAdapter;
import com.lak.prm392.groupproject.data.local.entities.User;
import com.lak.prm392.groupproject.model.AdminViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdminDashboardActivity extends AppCompatActivity {
    private AdminViewModel viewModel;
    private AdminUserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        RecyclerView rv = findViewById(R.id.rvUsers);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminUserAdapter(user -> {
            if (user.isBanned) {
                viewModel.unbanUser(user.id);
                Toast.makeText(this, "Đã unban " + user.name, Toast.LENGTH_SHORT).show();
            } else {
                String banUntil = getFutureDate(7); // ban 7 ngày
                viewModel.banUser(user.id, banUntil);
                viewModel.addViolation(user.id, "Tự động bị ban bởi admin", "Ban 7 ngày");
                Toast.makeText(this, "Đã ban " + user.name + " đến " + banUntil, Toast.LENGTH_SHORT).show();
            }
        });
        rv.setAdapter(adapter);

        viewModel.getAllUsers().observe(this, adapter::setUserList);

        findViewById(R.id.btnAddUser).setOnClickListener(v -> showAddUserDialog());
    }

    private String getFutureDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm người dùng mới");

        View view = getLayoutInflater().inflate(R.layout.add_user_dialog, null);
        builder.setView(view);

        EditText etName = view.findViewById(R.id.etUserName);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);
        Spinner spRole = view.findViewById(R.id.spRole);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String role = spRole.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.name = name;
            user.email = email;
            user.password = password;
            user.role = role;

            viewModel.insertUser(user);
            Toast.makeText(this, "Đã thêm người dùng mới", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


}


