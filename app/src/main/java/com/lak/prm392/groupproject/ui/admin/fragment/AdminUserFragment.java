package com.lak.prm392.groupproject.ui.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.adapter.AdminUserAdapter;
import com.lak.prm392.groupproject.model.AdminViewModel;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import com.lak.prm392.groupproject.data.local.entities.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminUserFragment extends Fragment {

    private AdminViewModel viewModel;
    private AdminUserAdapter adapter;

    public AdminUserFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user, container, false);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        RecyclerView rvUsers = view.findViewById(R.id.rvUsers);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdminUserAdapter(user -> {
            if (user.isBanned) {
                viewModel.unbanUser(user.id);
                Toast.makeText(getContext(), "Đã unban " + user.name, Toast.LENGTH_SHORT).show();
            } else {
                String banUntil = getFutureDate(7);
                viewModel.banUser(user.id, banUntil);
                viewModel.addViolation(user.id, "Vi phạm nội quy", "Bị ban 7 ngày");
                Toast.makeText(getContext(), "Đã ban " + user.name + " đến " + banUntil, Toast.LENGTH_SHORT).show();
            }
        });
        rvUsers.setAdapter(adapter);

        viewModel.getAllUsers().observe(getViewLifecycleOwner(), adapter::setUserList);

        Button btnAdd = view.findViewById(R.id.btnAddUser);
        btnAdd.setOnClickListener(v -> showAddUserDialog());

        return view;
    }

    private String getFutureDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, days);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.getTime());
    }

    private void showAddUserDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_user, null);

        Spinner spRole = dialogView.findViewById(R.id.spRole);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.roles_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRole.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thêm người dùng")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = ((EditText) dialogView.findViewById(R.id.etName)).getText().toString();
                    String email = ((EditText) dialogView.findViewById(R.id.etEmail)).getText().toString();
                    String password = ((EditText) dialogView.findViewById(R.id.etPassword)).getText().toString();
                    String role = spRole.getSelectedItem().toString();

                    User user = new User();
                    user.name = name;
                    user.email = email;
                    user.password = password;
                    user.role = role;
                    viewModel.insertUser(user);

                    Toast.makeText(getContext(), "Đã thêm người dùng", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
}
