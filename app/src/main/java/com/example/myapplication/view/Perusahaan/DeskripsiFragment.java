package com.example.myapplication.view.Perusahaan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeskripsiFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TabSapiBetina ubahHarga;
    private TabSapiJantan daftarHarga;

    String namaPeternak;
    public DeskripsiFragment(String nama) {
        namaPeternak = nama;
    }
    public DeskripsiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((HomePerusahaan) getActivity()).setActionBarTitle("Ubah Harga");
        View view = inflater.inflate(R.layout.fragment_perusahaan_ubah_harga, container, false);

        tabLayout = view.findViewById(R.id.tab_ubah_harga);
        viewPager = view.findViewById(R.id.pager_ubah_harga);
        ubahHarga = new TabSapiBetina();
        daftarHarga = new TabSapiJantan();
        Bundle bundle=new Bundle();
        bundle.putString("namaPeternak", namaPeternak);
        ubahHarga.setArguments(bundle);
        daftarHarga.setArguments(bundle);
        TabAdapter ubahHargaAdapter = new TabAdapter(getChildFragmentManager());
        ubahHargaAdapter.tambahFragment(daftarHarga, "Jantan");
        ubahHargaAdapter.tambahFragment(ubahHarga, "Betina");

        viewPager.setAdapter(ubahHargaAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
