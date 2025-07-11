package com.lak.prm392.groupproject.ui.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.ui.admin.fragment.AdminBookFragment;
import com.lak.prm392.groupproject.ui.admin.fragment.AdminDashboardFragment;
import com.lak.prm392.groupproject.ui.admin.fragment.AdminUserFragment;
import com.lak.prm392.groupproject.ui.admin.fragment.AdminViolationFragment;

public class AdminMainDashboardActivity extends AppCompatActivity {
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_dashboard);

        BottomNavigationView nav = findViewById(R.id.adminBottomNav);

        // 👉 Set fragment mặc định lúc mở app
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.admin_fragment_container, new AdminDashboardFragment())
                    .commit();
            nav.setSelectedItemId(R.id.nav_dashboard); // để highlight luôn icon dashboard
        }

        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                fragment = new AdminDashboardFragment();
            } else if (id == R.id.nav_users) {
                fragment = new AdminUserFragment();
            } else if (id == R.id.nav_books) {
                fragment = new AdminBookFragment();
            } else if (id == R.id.nav_violations) {
                fragment = new AdminViolationFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.admin_fragment_container, fragment)
                        .commit();
            }

            return true;
        });

    }

}

