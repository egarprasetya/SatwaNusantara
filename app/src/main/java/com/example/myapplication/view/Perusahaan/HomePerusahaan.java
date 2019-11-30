package com.example.myapplication.view.Perusahaan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.view.HalamanDaftarTelepon;
import com.example.myapplication.view.HalamanMasuk;
import com.example.myapplication.view.Pengajuan.MemasukanDataSapi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePerusahaan extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FragmentTransaction ft;
    FragmentManager fm;

    DeskripsiFragment hargaFragment;
    HomeFragment homeFragment;
    EditDataPribadi editDataPribadi;
    TabSapiJantan daftarHargaFragment;
    TabSapiBetina ubahHargaFragment;
//    MemasukanDataSapi memasukanDataSapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        hargaFragment = new DeskripsiFragment();
//        memasukanDataSapi = new MemasukanDataSapi();
        homeFragment = new HomeFragment();
        editDataPribadi = new EditDataPribadi();
        daftarHargaFragment = new TabSapiJantan();
        ubahHargaFragment = new TabSapiBetina();

        fm.beginTransaction().replace(R.id.container_perusahaan, homeFragment).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_perusahaan);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bawah_data_pribadi:
                        fm.beginTransaction().replace(R.id.container_perusahaan, editDataPribadi).commit();
                        break;
                    case R.id.nav_bawah_home_perusahaan:
                        fm.beginTransaction().replace(R.id.container_perusahaan, homeFragment).commit();
                        break;
                    case R.id.nav_bawah_data_sapi:
                        fm.beginTransaction().replace(R.id.container_perusahaan, hargaFragment).commit();
                        break;
                }
                return true;
            }
        });
    }

//    public void setActionBarTitle(String title){
//        getSupportActionBar().setTitle(title);
//    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftarTelepon.class));
                HomePerusahaan.this.finish();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public TabSapiJantan getDaftarHargaFragment() {
        return daftarHargaFragment;
    }

    public TabSapiBetina getUbahHargaFragment() {
        return ubahHargaFragment;
    }
}
