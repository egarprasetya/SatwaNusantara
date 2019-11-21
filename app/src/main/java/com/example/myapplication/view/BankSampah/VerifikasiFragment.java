package com.example.myapplication.view.BankSampah;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

public class VerifikasiFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TabVerifikasiFragment verifikasi;
    private TabRiwayatFragment riwayat;

    public VerifikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bank_sampah_verifikasi, container, false);

        tabLayout = view.findViewById(R.id.tab_verifikasi);
        viewPager = view.findViewById(R.id.pager_verifikasi);
        verifikasi = new TabVerifikasiFragment();
        riwayat = new TabRiwayatFragment();

        TabAdapter verifikasiAdapter = new TabAdapter(getChildFragmentManager());
        verifikasiAdapter.tambahFragment(verifikasi, "Verifikasi");
        verifikasiAdapter.tambahFragment(riwayat, "Riwayat");

        viewPager.setAdapter(verifikasiAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
