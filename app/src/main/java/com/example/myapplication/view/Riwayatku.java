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
public class Riwayatku extends Fragment {


    public Riwayatku() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomePengajuan) getActivity()).setActionBarTitle("Riwayat");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat, container, false);
    }

}
