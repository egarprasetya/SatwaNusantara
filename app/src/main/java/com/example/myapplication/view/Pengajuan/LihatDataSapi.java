package com.example.myapplication.view.Pengajuan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabAdapter;
import com.example.myapplication.view.Perusahaan.TabSapiBetina;
import com.example.myapplication.view.Perusahaan.TabSapiJantan;
import com.google.android.material.tabs.TabLayout;

public class LihatDataSapi extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TabSapiBetina tabSapiBetina;
    private TabSapiJantan tabSapiJantan;
    String namaPengajuan;
    String jumlahSapi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perusahaan_ubah_harga);
        tabLayout = findViewById(R.id.tab_ubah_harga);
        viewPager = findViewById(R.id.pager_ubah_harga);
        tabSapiBetina = new TabSapiBetina();
        tabSapiJantan = new TabSapiJantan();
        namaPengajuan = getIntent().getStringExtra("namaPeternak");
        jumlahSapi = getIntent().getStringExtra("jumlahSapi");
        Bundle bundle=new Bundle();
        bundle.putString("namaPeternak", namaPengajuan);
        bundle.putString("jumlahSapi", jumlahSapi);
        tabSapiBetina.setArguments(bundle);
        tabSapiJantan.setArguments(bundle);

        TabAdapter ubahHargaAdapter = new TabAdapter(getSupportFragmentManager());
        ubahHargaAdapter.tambahFragment(tabSapiJantan, "Jantan");
        ubahHargaAdapter.tambahFragment(tabSapiBetina, "Betina");

        viewPager.setAdapter(ubahHargaAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
