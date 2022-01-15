package com.hutech.waiter.screens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hutech.waiter.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatisticFragment extends Fragment {

    public StatisticFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_statistic_fragment,container,false);
        initVieW(view);
        return view;
    }

    TextView textDate;

    private void initVieW(View view) {
        textDate = view.findViewById(R.id.text_date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        textDate.setText("Hôm nay\n"+String.valueOf(simpleDateFormat.format(Calendar.getInstance().getTime())));
    }
}