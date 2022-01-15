package com.hutech.waiter.screens.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hutech.waiter.R;


public class NotiFragment extends Fragment {


    public NotiFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_notification_fragment, container, false);
        initView(view);
        return view;
    }

    RecyclerView recyclerView;
    LinearLayout layoutEmpty;

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutEmpty = view.findViewById(R.id.layout_empty);
    }
}