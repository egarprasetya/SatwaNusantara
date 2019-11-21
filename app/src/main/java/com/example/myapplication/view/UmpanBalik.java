package com.example.myapplication.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.view.Pengajuan.HomePengajuan;

/**
 * A simple {@link Fragment} subclass.
 */
public class UmpanBalik extends Fragment {


    public UmpanBalik() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomePengajuan) getActivity()).setActionBarTitle("Timbal Balik");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_umpan_balik, container, false);
    }

}
