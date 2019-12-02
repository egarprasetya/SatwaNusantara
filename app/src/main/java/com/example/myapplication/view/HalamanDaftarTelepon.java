package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.Model_leaderboard;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.Util;
import com.example.myapplication.view.Perusahaan.HomePerusahaan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class HalamanDaftarTelepon extends AppCompatActivity {

    /*
     * TODO pendaftaran semua akun melalui online, volunteer, bank sampah, mitra, perusahaan
     * Termasuk pengecekan dan verifikasi mereka
     */
    private ViewGroup mainLayout;
    private ImageView tong;
    private static String TAG = HalamanMasuk.class.getSimpleName();
    private TextInputEditText nama, email, password;
    private Button daftar, verifikasiBut;
    private ImageView msgRegister;
    private FirebaseAuth auth;
    private DatabaseReference dbRef;
    private Button btninfo;
    private Button btnclose;
    private String namaB;
    String codeSent, strNama;


    //gambarbergerak
    private int xDelta;
    private int yDelta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar_phone);
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        nama = findViewById(R.id.input_daftar_nama);
        email = findViewById(R.id.input_daftar_email);
        password = findViewById(R.id.input_daftar_password);
        daftar = findViewById(R.id.btn_datftarDaftar);
        verifikasiBut = findViewById(R.id.btn_verifikasi);
        msgRegister = findViewById(R.id.msgregister);
        btninfo = findViewById(R.id.btn_info);
        btnclose = findViewById(R.id.btn_close);
        mainLayout = (RelativeLayout) findViewById(R.id.main);

        daftar.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);

        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDaftarTelepon.this, HalamanMasuk.class));
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("daftar coy");
                verifySignInCode();
            }
        });


        verifikasiBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final View overlay = findViewById(R.id.loading_overlay);
//                final Button btnDaftar = daftar;
//                btnDaftar.setEnabled(false);
//                overlay.setVisibility(View.VISIBLE);
                boolean bolehDaftar = true;
                strNama = nama.getText().toString().trim();
                String strEmail = email.getText().toString().trim();


                if (strNama.isEmpty()) {
                    nama.requestFocus();
                    bolehDaftar = false;
                    nama.setError("Isi terlebih dahulu!");
//                    overlay.setVisibility(View.GONE);
//                    btnDaftar.setEnabled(true);
                } else {
                    bolehDaftar = true;
                }

                if (strEmail.isEmpty()) {
                    email.requestFocus();
                    bolehDaftar = false;
                    email.setError("Isi terlebih dahulu!");
//                    overlay.setVisibility(View.GONE);
//                    btnDaftar.setEnabled(true);
                } else {
                    bolehDaftar = true;
                }



                if (bolehDaftar){
                    phoneVerification(strEmail);
                    daftar.setVisibility(View.VISIBLE);
                    password.setVisibility(View.VISIBLE);
                    verifikasiBut.setVisibility(View.GONE);

                }
            }


        });

        msgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgRegister.setVisibility(View.GONE);

            }
        });






    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

            Log.d(TAG, "onVerificationCompleted:" + credential);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
            System.out.println("code sent "+ codeSent);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // ...
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // ...
            }


            // Show a message and update the UI
            // ...
        }
    };

    void phoneVerification(String email){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                email,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
    }

    private void simpanLDR(String nama, String UID) {
        Model_leaderboard m = new Model_leaderboard(nama, "0", "0", Util.util_desc_leaderboard, UID);
        FirebaseDatabase.getInstance()
                .getReference()
                .child(Util.util_data_leaderboard)
                .child(UID)
                .setValue(m);
    }

    void verifySignInCode()
    {
        String code = password.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
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
                            final String email = auth.getCurrentUser().getPhoneNumber();
                            final String uid = auth.getCurrentUser().getUid();

                            Pengguna daftar = new Pengguna(uid, strNama, email, Pengguna.PERUSAHAAN, "0");
                            simpanLDR(strNama, uid);
                            dbRef.child("pengguna").child(uid).push().setValue(daftar);

                            ModelPengajuan dataPengajuan = new ModelPengajuan("", strNama, email, "", "", "",
                                    "", email, "");
                            final DatabaseReference pushId = dbRef.child("dataPengajuan");
                            pushId.push().setValue(dataPengajuan);

                            DatabaseReference getId = FirebaseDatabase.getInstance().getReference().child("dataPengajuan");
                            getId.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot perData : dataSnapshot.getChildren()) {
                                        ModelPengajuan model = perData.getValue(ModelPengajuan.class);
                                        if (model.getUsername() != null) {
                                            if (model.getUsername().equalsIgnoreCase(email)) {
                                                String key = perData.getKey();
                                                System.out.println("KEYNYA COK " + key);
                                                pushId.child(key).child("idPengajuan").setValue(key);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            startActivity(new Intent(HalamanDaftarTelepon.this, HomePerusahaan.class));
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Batal daftar?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nama.setText("");
                email.setText("");
                password.setText("");
                startActivity(new Intent(getApplicationContext(), HalamanMasuk.class));
                HalamanDaftarTelepon.this.finish();
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


    //gambar bergerak

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {


//                msgemail.setVisibility(View.VISIBLE);
//                msgpass.setVisibility(View.GONE);
//                msgdaftar.setVisibility(View.GONE);
//                msgreminder.setVisibility(View.GONE);
//                tgemail.setVisibility(View.VISIBLE);
//                tgpass.setVisibility(View.GONE);
//                tgdaftar.setVisibility(View.GONE);


                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                msgRegister.setVisibility(View.VISIBLE);

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        Toast.makeText(HalamanDaftarTelepon.this,
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

    private boolean cekJaringan() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
