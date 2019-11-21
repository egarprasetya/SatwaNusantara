package com.example.myapplication.view.Mitra;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarVoucherFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public DaftarVoucherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((HomePerusahaan)getActivity()).setActionBarTitle("Daftar ModelVoucher");
        View view = inflater.inflate(R.layout.fragment_mitra_daftar_voucher, container, false);
        tabLayout = view.findViewById(R.id.tab_voucher);
        viewPager = view.findViewById(R.id.pager_voucher);

        TabAdapter voucher_adapter = new TabAdapter(getChildFragmentManager());
        voucher_adapter.tambahFragment(new TabDaftarVoucherFragment(), "Daftar Voucher");
        voucher_adapter.tambahFragment(new TabBuatVoucherFragment(), "Buat Voucher");

        viewPager.setAdapter(voucher_adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
