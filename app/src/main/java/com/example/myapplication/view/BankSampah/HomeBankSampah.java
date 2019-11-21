package com.example.myapplication.view.BankSampah;

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
import com.example.myapplication.view.HalamanMasuk;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeBankSampah extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FragmentTransaction ft;
    FragmentManager fm;
    VerifikasiFragment verifikasi;
    Verifikasi_Fragment_2 verifikasi_2;
    HomeFragment home;
    DaftarPerusahaanFragment daftarPerusahaan;
    TabRiwayatFragment tabRiwayat;
    TabVerifikasiFragment tabVerifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_sampah);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        verifikasi = new VerifikasiFragment();
        verifikasi_2 = new Verifikasi_Fragment_2();
        home = new HomeFragment();
        daftarPerusahaan = new DaftarPerusahaanFragment();
        tabRiwayat = new TabRiwayatFragment();
        tabVerifikasi = new TabVerifikasiFragment();

//        ft.replace(R.id.container_bank_sampah, verifikasi).commit();
        ft.add(R.id.container_bank_sampah, home, HomeFragment.class.getSimpleName()).commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R. id.navigasi_bawah_bank);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_bawah_verifikasi:
                        fm.beginTransaction().replace(R.id.container_bank_sampah, verifikasi_2).commit();
                        break;
                    case R.id.nav_bawah_menu:
                        fm.beginTransaction().replace(R.id.container_bank_sampah, home).commit();
                        break;
                    case R.id.nav_bawah_perusahaan:
                        fm.beginTransaction().replace(R.id.container_bank_sampah, daftarPerusahaan).commit();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), HalamanMasuk.class));
                HomeBankSampah.this.finish();
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
}
