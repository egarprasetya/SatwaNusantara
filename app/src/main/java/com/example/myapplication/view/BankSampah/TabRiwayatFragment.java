package com.example.myapplication.view.BankSampah;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.bs_riwayat_adapter;
import com.example.myapplication.model.bs_riwayat_model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabRiwayatFragment extends Fragment {

    private RecyclerView recyclerView;
    private bs_riwayat_adapter adapter;
    private List<bs_riwayat_model> modelList;

    public TabRiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_sampah_tab_riwayat, container, false);

        recyclerView = view.findViewById(R.id.rv_riwayatVerifikasi);
        modelList = new ArrayList<>();
        adapter = new bs_riwayat_adapter(getActivity(), modelList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        daftarRiwayat();

        return view;
    }

    private void daftarRiwayat() {
        int[] pic = {
                R.drawable.sampah3,
                R.drawable.sampah2,
                R.drawable.sampah1,
                R.drawable.sampah0,
                R.drawable.sampah_01,
                R.drawable.sampah_03,
                R.drawable.sampah_04
        };
        bs_riwayat_model a;
        a = new bs_riwayat_model("23 September 2018", "4", "diterima", pic[0]);
        modelList.add(a);
        a = new bs_riwayat_model("25 September 2018", "2", "diterima", pic[3]);
        modelList.add(a);
        a = new bs_riwayat_model("25 September 2018", "3", "diterima", pic[4]);
        modelList.add(a);

        adapter.notifyDataSetChanged();
    }

}
