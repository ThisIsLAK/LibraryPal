package com.lak.prm392.groupproject.ui.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.adapter.ViolationAdapter;
import com.lak.prm392.groupproject.model.AdminViewModel;

public class AdminViolationFragment extends Fragment {

    private AdminViewModel viewModel;
    private ViolationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_violation, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvViolations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ViolationAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        viewModel.getAllViolations().observe(getViewLifecycleOwner(), adapter::setViolationLogs);

        return view;
    }
}
