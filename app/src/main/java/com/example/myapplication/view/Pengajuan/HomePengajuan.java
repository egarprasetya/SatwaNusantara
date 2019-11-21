package com.example.myapplication.view.Pengajuan;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.view.HalamanMasuk;
import com.example.myapplication.view.Pengaturan;
import com.example.myapplication.view.Riwayatku;
import com.example.myapplication.view.UmpanBalik;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

//import

public class HomePengajuan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener /*, HomeFragment.OnMenuClickListener*/ {

    public static String ACTIVE = "fragment_volunteer_aktif";
    HomeFragment homeFragment;
    SampahkuFragment sampahkuFragment;
    MemasukanPengajuan memasukanPengajuan;
    BankSampahFragment bankSampahFragment;
    InfoPoinFragment infoPoinFragment;
    private BottomNavigationView nav_volunteer;
    private NavigationView navigationView;
    private ImageButton buka_kamera;
    private Pengaturan f_nav_pengaturan;
    private Riwayatku f_nav_riwayat;
    private UmpanBalik f_nav_timbalBalik;

    private FragmentManager fm;
    private FragmentTransaction ft;

    //private

    private ViewGroup mainLayout;
    private ImageView tong;
    private ImageView msgHome;
    private ImageView msg1;
    private ImageView msg2;
    private ImageView msg3;
    private ImageView msg4;

    private Button btninfo;
    private Button btnclose;

    private int xDelta;
    private int yDelta;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan);
        Toolbar toolbar = findViewById(R.id.toolbar_volunteer);
        setSupportActionBar(toolbar);

        mainLayout = (RelativeLayout) findViewById(R.id.main);

        homeFragment = new HomeFragment();
        sampahkuFragment = new SampahkuFragment();
        infoPoinFragment = new InfoPoinFragment();
        bankSampahFragment = new BankSampahFragment();
        auth = FirebaseAuth.getInstance();

        btninfo = findViewById(R.id.btn_infoHome);
//        btnclose = findViewById(R.id.btn_closeHome);
        String clientId = MqttClient.generateClientId();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("klikternak_broker");
        options.setPassword("yesiknow".toCharArray());
        MqttAndroidClient client =
                new MqttAndroidClient(HomePengajuan.this, "http://52.175.50.19", clientId);

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(HomePengajuan.this,"conected", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(HomePengajuan.this,"fail conected", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        f_nav_riwayat = new Riwayatku();
        f_nav_pengaturan = new Pengaturan();
        f_nav_timbalBalik = new UmpanBalik();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fm.beginTransaction().replace(R.id.container_volunteer, homeFragment, ACTIVE).commit();


        nav_volunteer = (BottomNavigationView) findViewById(R.id.nav_volunteer);
        nav_volunteer.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_vol_home:
                        fm.beginTransaction().replace(R.id.container_volunteer, homeFragment, ACTIVE).commit();
                        break;
                    case R.id.nav_vol_sampah:
                        fm.beginTransaction().replace(R.id.container_volunteer, new LihatPengajuan()).commit();

//                        fm.beginTransaction().replace(R.id.container_volunteer, sampahkuFragment, ACTIVE).commit();
                        break;
                    case R.id.nav_vol_poin:
                        fm.beginTransaction().replace(R.id.container_volunteer, new MemasukanPengajuan()).commit();
                        break;
                    case R.id.nav_vol_bank:
                        fm.beginTransaction().replace(R.id.container_volunteer, new LihatDaftarPakan()).commit();
                        break;
                }
                return true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_volunteer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_side_volunteer);
        navigationView.setNavigationItemSelectedListener(this);


        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tong.setVisibility(View.VISIBLE);
                btninfo.setVisibility(View.GONE);
                btnclose.setVisibility(View.VISIBLE);

            }
        });

//        btnclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
////                msgRegister.setVisibility(View.GONE);
////                tong.setVisibility(View.GONE);
//                btninfo.setVisibility(View.VISIBLE);
//                btnclose.setVisibility(View.GONE);
//
//            }
//        });


    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_timbalBalik) {
//            fm.beginTransaction().replace(R.id.container_volunteer, new UmpanBalik()).commit();
//        } else if (id == R.id.nav_riwayat) {
//            fm.beginTransaction().replace(R.id.container_volunteer, new Riwayatku()).commit();
//        } else
//        if (id == R.id.nav_pengaturan) {
//            fm.beginTransaction().replace(R.id.container_volunteer, new Pengaturan()).commit();
//        } else
        if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_volunteer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_volunteer);
        drawer.closeDrawer(GravityCompat.START);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Keluar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), HalamanMasuk.class));
                HomePengajuan.this.finish();
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

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                msgHome.setVisibility(View.VISIBLE);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(HomePengajuan.this,
                                "Sentuh tombol gambar kamera pada menu SAMPAHKU untuk Foto Sampah", Toast.LENGTH_SHORT)
                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }


}
