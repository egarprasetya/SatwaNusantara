package com.example.myapplication.view.BankSampah;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CardView mVerifikasi, mDaftarPerusahaan;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_sampah_home, container, false);

        final FragmentTransaction ft = getFragmentManager().beginTransaction();

        mVerifikasi = view.findViewById(R.id.cv_verifikasi);
        mDaftarPerusahaan = view.findViewById(R.id.cv_daftar_perusahaan);

        mVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_bank_sampah, new Verifikasi_Fragment_2()).commit();
            }
        });
        mDaftarPerusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_bank_sampah, new DaftarPerusahaanFragment()).commit();
            }
        });

        return view;
    }

}
