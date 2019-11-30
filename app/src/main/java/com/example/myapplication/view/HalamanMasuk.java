package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Welcome;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.TinyDB;
import com.example.myapplication.view.BankSampah.HomeBankSampah;
import com.example.myapplication.view.Mitra.HomeMitra;
import com.example.myapplication.view.Perusahaan.HomePerusahaan;
import com.example.myapplication.view.Pengajuan.HomePengajuan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//importan gambar bergerak

public class HalamanMasuk extends AppCompatActivity {

    private static String TAG = "HalamanMasuk";

    private TextInputEditText email, password;
    private Button masuk;
    private TextView daftar;
    private Button tutorial;
    private Button paham;
    private String strEmail, strPassword;
    private ImageView tong1;
    private ImageView tong2;
    private TextView tong3;
    private TextView tong4;
    private ImageView tong;
    private ImageView msgemail;
    private ImageView msgdaftar;
    private ImageView msgpass;
    private ImageView msgreminder;
    private ImageView msgtombol;
    private ImageView tgemail;
    private ImageView tgdaftar;
    private ImageView tgpass;
    private Button btninfo;
    private Button btnclose;




    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;

    //deklarasi gambar bergerak
    private ViewGroup mainLayout;
    private ImageView image;

    private int xDelta;
    private int yDelta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        //background
//        ImageView bg = findViewById(R.id.imgBackground);
//        bg.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bgsapi1));

        auth = FirebaseAuth.getInstance();

        dbRef = FirebaseDatabase.getInstance().getReference();

        //gambar bergerak



        mainLayout = (RelativeLayout) findViewById(R.id.main);
//        tong = (ImageView) findViewById(R.id.tong);

//        tong.setOnTouchListener(onTouchListener());

        //end

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        email = (TextInputEditText) findViewById(R.id.input_masukEmail);
        password = (TextInputEditText) findViewById(R.id.input_masukPassword);
//        tutorial = (Button) findViewById(R.id.btn_tutorial);
//        paham = (Button) findViewById(R.id.btn_ok);
        masuk = (Button) findViewById(R.id.btn_masuk);
        msgemail=   findViewById(R.id.msgemail);
        msgpass = findViewById(R.id.msgpassword);
        msgdaftar = findViewById(R.id.msgdaftar);
        msgreminder = findViewById(R.id.msgreminder);
        msgtombol = findViewById(R.id.msgtombol);
        tgemail = findViewById(R.id.tgemail);
        tgpass = findViewById(R.id.tgpassword);
        tgdaftar = findViewById(R.id.tgdaftar);
        btninfo = findViewById(R.id.btn_info);



        daftar = findViewById(R.id.btn_daftar);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();
                boolean bolehMasuk = true;


                if (strEmail.isEmpty()) {
                    email.requestFocus();
                    bolehMasuk = false;
                    email.setError("Isi terlebih dahulu!");
                } else {
                    bolehMasuk = true;
                }
                if (strPassword.isEmpty()) {
                    password.requestFocus();
                    bolehMasuk = false;
                    password.setError("Isi terlebih dahulu!");
                } else {
                    bolehMasuk = true;
                }

                if (bolehMasuk) {
                        auth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(HalamanMasuk.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = auth.getCurrentUser();
                                    dbRef.child("pengguna").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                Pengguna pen = child.getValue(Pengguna.class);
                                                cekLevel(pen.getLevel());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(HalamanMasuk.this, "Username Atau Email Salah", Toast.LENGTH_LONG).show();
                                            Log.w(TAG, "Login ERROR : " + databaseError.getDetails());
                                        }
                                    });

                                } else {
                                    if (cekJaringan()) {

                                    }
                                    Toast.makeText(HalamanMasuk.this, "Gagal Masuk", Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "Gagal Masuk: " + task.getException());
                                }
                            }
                        });

                }
            }
        });



        msgemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
                msgemail.setVisibility(View.GONE);
                msgpass.setVisibility(View.VISIBLE);
                msgdaftar.setVisibility(View.GONE);
                msgreminder.setVisibility(View.GONE);
                tgemail.setVisibility(View.GONE);
                tgpass.setVisibility(View.VISIBLE);
                tgdaftar.setVisibility(View.GONE);

            }
        });

        msgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
                msgemail.setVisibility(View.GONE);
                msgpass.setVisibility(View.GONE);
                msgdaftar.setVisibility(View.VISIBLE);
                msgreminder.setVisibility(View.GONE);
                tgemail.setVisibility(View.GONE);
                tgpass.setVisibility(View.GONE);
                tgdaftar.setVisibility(View.VISIBLE);

            }
        });

        msgdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
                msgemail.setVisibility(View.GONE);
                msgpass.setVisibility(View.GONE);
                msgdaftar.setVisibility(View.GONE);
                msgreminder.setVisibility(View.VISIBLE);
                tgemail.setVisibility(View.GONE);
                tgpass.setVisibility(View.GONE);
                tgdaftar.setVisibility(View.GONE);

            }
        });

        msgreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
                msgemail.setVisibility(View.GONE);
                msgpass.setVisibility(View.GONE);
                msgdaftar.setVisibility(View.GONE);
                msgreminder.setVisibility(View.GONE);
                msgtombol.setVisibility(View.VISIBLE);
                tgemail.setVisibility(View.GONE);
                tgpass.setVisibility(View.GONE);
                tgdaftar.setVisibility(View.GONE);

            }
        });

        msgtombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
                msgemail.setVisibility(View.GONE);
                msgpass.setVisibility(View.GONE);
                msgdaftar.setVisibility(View.GONE);
                msgreminder.setVisibility(View.GONE);
                msgreminder.setVisibility(View.GONE);
                msgtombol.setVisibility(View.GONE);
                tgemail.setVisibility(View.GONE);
                tgpass.setVisibility(View.GONE);
                tgdaftar.setVisibility(View.GONE);

            }
        });

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));
//                msgemail.setVisibility(View.GONE);
//                msgpass.setVisibility(View.GONE);
//                msgdaftar.setVisibility(View.GONE);
//                msgreminder.setVisibility(View.GONE);
//                msgreminder.setVisibility(View.GONE);
//                tgemail.setVisibility(View.GONE);
//                tgpass.setVisibility(View.GONE);
//                tgdaftar.setVisibility(View.GONE);
//                tong.setVisibility(View.VISIBLE);
                btninfo.setVisibility(View.GONE);

            }
        });


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.myapplication.view.HalamanDaftar.class));
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private void cekLevel(String level) {
        switch (level) {
            case Pengguna.VOLUNTEER:
                startActivity(new Intent(HalamanMasuk.this, HomePengajuan.class));
                break;
            case Pengguna.PENGAJUAN:
                startActivity(new Intent(HalamanMasuk.this, HomePerusahaan.class));
                break;
            case Pengguna.BANK_SAMPAH:
                startActivity(new Intent(HalamanMasuk.this, HomeBankSampah.class));
                break;
            case Pengguna.MITRA:
                startActivity(new Intent(HalamanMasuk.this, HomeMitra.class));
                break;
            default:
                Toast.makeText(HalamanMasuk.this, "Tidak Diketahui", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Level Tidak Diketahui");
        }
        View context = findViewById(R.id.root_layout_masuk);
        Snackbar.make(context, "Masuk Sebagai " + level, Snackbar.LENGTH_LONG).show();
        Log.d(TAG, "Berhasil Masuk");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Tutup aplikasi BUANG.IN?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                System.exit(1);
                //                HalamanMasuk.this.finish();
                Intent i = new Intent(getApplicationContext(), Welcome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("EXIT", true);
                startActivity(i);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean cekJaringan() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    //ontouch gambar bergerak

    private OnTouchListener onTouchListener() {
        return new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {


                msgemail.setVisibility(View.VISIBLE);
                msgpass.setVisibility(View.GONE);
                msgdaftar.setVisibility(View.GONE);
                msgreminder.setVisibility(View.GONE);
                tgemail.setVisibility(View.VISIBLE);
                tgpass.setVisibility(View.GONE);
                tgdaftar.setVisibility(View.GONE);


                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(HalamanMasuk.this,
                                "HALO AKU TONG! AKU BISA DIGESER LOH", Toast.LENGTH_SHORT)
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

