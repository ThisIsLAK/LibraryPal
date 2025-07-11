package com.lak.prm392.groupproject.ui.admin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lak.prm392.groupproject.R;
import com.lak.prm392.groupproject.data.local.dao.TopBook;
import com.lak.prm392.groupproject.model.AdminViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminDashboardFragment extends Fragment {
    private AdminViewModel viewModel;
    private TextView tvTotalUsers, tvTotalBooks;
    private BarChart barChartTop, barChartViolations, barChartBorrow;
    private Spinner spinnerTimeRange;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        tvTotalUsers = view.findViewById(R.id.tvTotalUsers);
        tvTotalBooks = view.findViewById(R.id.tvTotalBooks);
        barChartTop = view.findViewById(R.id.barChartTopBooks);
        barChartViolations = view.findViewById(R.id.barChartViolations);
        barChartBorrow = view.findViewById(R.id.barChartBorrow);
        spinnerTimeRange = view.findViewById(R.id.spinnerTimeRange);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        // Hiển thị số tổng
        viewModel.getAllUsers().observe(getViewLifecycleOwner(), users ->
                tvTotalUsers.setText("Tổng người dùng: " + users.size()));
        viewModel.getAllBooks().observe(getViewLifecycleOwner(), books ->
                tvTotalBooks.setText("Tổng sách: " + books.size()));

        // Hiển thị biểu đồ top sách mượn
        viewModel.getTopBooks().observe(getViewLifecycleOwner(), this::showTopBooksChart);

        // Spinner time range setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.time_range_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimeRange.setAdapter(adapter);

        spinnerTimeRange.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString().toLowerCase(); // day/week/month

                viewModel.getViolationStats(selected).observe(getViewLifecycleOwner(), data ->
                        showBarChart(barChartViolations, data, "Vi phạm theo thời gian"));

                viewModel.getBorrowStats(selected).observe(getViewLifecycleOwner(), data ->
                        showBarChart(barChartBorrow, data, "Lượt mượn theo thời gian"));
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        return view;
    }

    private void showTopBooksChart(List<TopBook> topBooks) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < topBooks.size(); i++) {
            TopBook book = topBooks.get(i);
            entries.add(new BarEntry(i, book.borrowCount));
            labels.add(book.title);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Sách mượn nhiều nhất");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data = new BarData(dataSet);
        barChartTop.setData(data);

        XAxis xAxis = barChartTop.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barChartTop.getDescription().setEnabled(false);
        barChartTop.invalidate();
    }

    private void showBarChart(BarChart chart, Map<String, Integer> data, String label) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            entries.add(new BarEntry(index++, entry.getValue()));

            String rawKey = entry.getKey();
            String formattedLabel = rawKey;

            // Nếu key là "01", "02", ... "12" thì hiển thị "Tháng 1", ...
            if (rawKey.matches("\\d{2}")) {
                int month = Integer.parseInt(rawKey);
                formattedLabel = "Tháng " + month;
            }

            labels.add(formattedLabel);
        }

        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(Color.WHITE); // 👉 màu chữ trên từng cột

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        chart.setData(barData);

// X-Axis label
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(-25);
        xAxis.setTextColor(Color.WHITE); // 👉 màu chữ trục X

// Y-Axis
        chart.getAxisLeft().setTextColor(Color.WHITE); // 👉 trục trái
        chart.getAxisRight().setTextColor(Color.WHITE); // 👉 trục phải

// No description
        chart.getDescription().setEnabled(false);
        chart.getLegend().setTextColor(Color.WHITE); // 👉 chú thích (legend)

        chart.setFitBars(true);
        chart.invalidate();

    }

}
