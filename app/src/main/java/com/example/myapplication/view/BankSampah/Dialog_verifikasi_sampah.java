package com.example.myapplication.view.BankSampah;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ModelHargaSampah;
import com.example.myapplication.model.ModelSampah;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dialog_verifikasi_sampah extends Dialog implements View.OnClickListener {
    private Activity activity;
    private String strKode = "";

    public Dialog_verifikasi_sampah(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void getKode(String kode) {
        strKode = kode;
    }

    @Override
    public void onClick(View v) {

    }

    private ImageView imgFoto;
    private TextView txtNama, txtHarga, txtPoin, btnTerima, btnCek;
    private EditText inputBerat, inputJenis, inputHarga;
    private Spinner spinJenis;

    private DatabaseReference reference, databaseReference;

    private String strNama = "", strTotalHarga = "", strPoin = "", strJenis, strBerat = "", strHarga = "";

    private String poinVolunteer = "", strUIDVolunteer = "", strUIDBank = "", strURL = "";

    private double dHarga, dPoin, dTotalHarga, dBerat, dTotalPoin;

    private String strHargaKertas = "", strHargaBotol = "";

    private String plastik = "Plastik", kertas = "Kertas", lainnya = "Lainnya";
    private String[] arrJenis = {plastik, kertas, lainnya};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_dialog_verifikasi_sampah);
        strUIDBank = FirebaseAuth.getInstance().getUid();


        getHarga();
        getSamopah();
//        getPoin();

        imgFoto = findViewById(R.id.bank_dialog_verifikasi_foto);
        txtNama = findViewById(R.id.bank_dialog_verifikasi_nama);
        txtHarga = findViewById(R.id.bank_dialog_verifikasi_harga);
        txtPoin = findViewById(R.id.bank_dialog_verifikasi_poin);
        btnTerima = findViewById(R.id.bank_dialog_verifikasi_btn_terima);
        btnCek = findViewById(R.id.bank_dialog_verifikasi_btn_cek_harga);
        inputBerat = findViewById(R.id.bank_dialog_verifikasi_input_berat);
        inputJenis = findViewById(R.id.bank_dialog_verifikasi_input_jenis_sampah);
        inputHarga = findViewById(R.id.bank_dialog_verifikasi_input_harga);
        spinJenis = findViewById(R.id.bank_dialog_verifikasi_spin_jenis_sampah);

        inputBerat.setText("1");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrJenis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJenis.setAdapter(adapter);
        spinJenis.setSelection(0);
        spinJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strJenis = (String) spinJenis.getItemAtPosition(position);
                if (strJenis.equalsIgnoreCase(plastik)) {
                    strHarga = strHargaBotol;
                    inputJenis.setText(strJenis);
                    inputHarga.setText(strHarga);
                    pilihData();
                    harga();
                } else if (strJenis.equalsIgnoreCase(kertas)) {
                    strHarga = strHargaKertas;
                    inputJenis.setText(strJenis);
                    inputHarga.setText(strHarga);
                    pilihData();
                    harga();
                } else {
                    strHarga = strHargaBotol;
                    pilihLainnya();
                    inputJenis.setText(plastik);
                    inputHarga.setText(strHarga);
                    harga();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harga();
            }
        });

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekData();
            }
        });
    }

    private void cekData() {
        harga();
//        strBerat = inputBerat.getText().toString().trim();
//        strHarga = inputHarga.getText().toString().trim();
//        strJenis = inputJenis.getText().toString().trim();
        boolean bolehSimpan = true;
        System.out.println("urutan terima");

        System.out.println("strBerat : " + strBerat);
        System.out.println("strHarga : " + strHarga);
        System.out.println("strJenis : " + strJenis);

        if (strJenis.isEmpty() || strBerat.isEmpty() || strHarga.isEmpty()) {
            notif("tidak boleh kosong");
            bolehSimpan = false;
        } else {
            bolehSimpan = true;
        }

        if (Integer.parseInt(strHarga) <= 0) {
            bolehSimpan = false;
            notif("harga tidak valid");
        } else {
            bolehSimpan = true;
        }

        if (bolehSimpan) {
            if (Integer.parseInt(strBerat) <= 0) {
                bolehSimpan = false;
            } else {
                simpan();
            }
        }
    }

//    firebaseDatabase.getReference("dataSampah").child(kode).child("jenisSampah").setValue(stringJenisSampah);
//    firebaseDatabase.getReference("dataSampah").child(kode).child("statusVerifikasi").setValue(ModelSampah.VERIFIKASI_DITERIMA);
//    firebaseDatabase.getReference("dataSampah").child(kode).child("idBank").setValue(uid);
//    firebaseDatabase.getReference("dataSampah").child(kode).child("beratSampah").setValue(stringBerat);
//    firebaseDatabase.getReference("dataSampah").child(kode).child("poinSampah").setValue(String.valueOf(doubleTotalPoin));
//    firebaseDatabase.getReference("dataSampah").child(kode).child("tanggalSubmit").setValue(stringTanggalSubmit);
//    firebaseDatabase.getReference("dataSampah").child(kode).child("hargaSampah").setValue(String.valueOf(dooubleTotalHarga));
//    firebaseDatabase.getReference("pengguna").child(stringUID).child(stringKey).child("poin").setValue(String.valueOf(doublePoin));

    private void simpan() {
        System.out.println("urutan simpan");
        String[] arrChild = {"jenisSampah", "statusVerifikasi", "uidBank", "beratSampah", "poinSampah", "tanggalSubmit", "hargaSampah"};
        String[] arrNilai = {strJenis, ModelSampah.VERIFIKASI_DITERIMA, strUIDBank, strBerat, strPoin, Util.tanggalSekarang(), strTotalHarga};
        System.out.println("poin didapat : " + strPoin + ", harga : " + strHarga);
        System.out.println("strPoin : " + poinVolunteer);
        for (int i = 0; i < arrChild.length; i++) {
            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("dataSampah")
                    .child(strKode)
                    .child(arrChild[i])
                    .setValue(arrNilai[i]);
        }

        simpanLeaderboard();
    }

    private void simpanLeaderboard() {
        System.out.println("urutan simpan leaderboard");
        int int_poin = Integer.valueOf(poinVolunteer) + Integer.valueOf(strPoin);
        int int_desc = Integer.valueOf(Util.util_desc_leaderboard) - int_poin;

        final double doublePoin = Double.valueOf(poinVolunteer) + Double.valueOf(strPoin);
        System.out.println("poin volunteer : " + poinVolunteer + ", strPoin : " + strPoin);
        double doubleDesc = Double.valueOf(Util.util_desc_leaderboard) - doublePoin;

        String[] child = {"desc", "poin"},
                nilai = {String.valueOf(int_desc), String.valueOf(int_poin)};

        for (int i = 0; i < child.length; i++) {
            FirebaseDatabase.getInstance().getReference().child(Util.util_data_leaderboard)
                    .child(strUIDVolunteer).child(child[i])
                    .setValue(nilai[i]);
        }

        simpanPoin(String.valueOf(int_poin));

    }

    private String string_angka(String angka) {
        return angka.substring(0, angka.length() - 2);
    }

    private void simpanPoin(String poin) {
        System.out.println("urutan simpan poin");
        FirebaseDatabase.getInstance()
                .getReference()
                .child("dataVolunteer")
                .child(strUIDVolunteer)
                .child("poin")
                .setValue(poin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    pesan();
                }
            }
        });
    }

    private void pesan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(false)
                .setTitle("Berhasil")
                .setMessage("Status data berhasil diperbarui")
                .setPositiveButton("selesai", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void notif(String pesan) {
        Toast.makeText(getContext(), pesan, Toast.LENGTH_SHORT).show();
    }

    private void harga() {
        strBerat = inputBerat.getText().toString().trim();
        strHarga = inputHarga.getText().toString().trim();
        strJenis = inputJenis.getText().toString().trim();

        System.out.println("strHarga : " + strHarga);
        Toast.makeText(getContext(), "strHarga : " + strHarga, Toast.LENGTH_SHORT).show();

        dHarga = Double.valueOf(strHarga);
        dBerat = Double.valueOf(strBerat);

        dTotalHarga = dHarga * dBerat;
        dTotalPoin = dTotalHarga / 100;

        strPoin = string_angka(String.valueOf(dTotalPoin));
        Toast.makeText(activity, "poin didapat : " + strPoin, Toast.LENGTH_LONG).show();

        txtHarga.setText(string_angka(String.valueOf(dTotalHarga)));
        txtPoin.setText(string_angka(String.valueOf(dTotalPoin)));
    }

    private void pilihData() {
        inputJenis.setEnabled(false);
        inputHarga.setEnabled(false);
    }

    private void pilihLainnya() {
        inputJenis.setEnabled(true);
        inputHarga.setEnabled(true);
    }

    private void getSamopah() {
        reference = FirebaseDatabase.getInstance().getReference().child("dataSampah").child(strKode);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelSampah m = dataSnapshot.getValue(ModelSampah.class);
                    strURL = m.getUrlFoto();
                    strUIDVolunteer = m.getUidVolunteer();

                    System.out.println("uid volunteer : " + strUIDVolunteer);
                    getPoin();
                    Glide.with(getContext()).load(strURL).into(imgFoto);

                    getnama(strUIDVolunteer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getnama(String strUIDVolunteer) {
        reference = FirebaseDatabase.getInstance().getReference().child("pengguna").child(strUIDVolunteer);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Pengguna p = snapshot.getValue(Pengguna.class);
                        String nama = p.getNama();

                        txtNama.setText(nama);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPoin() {
        reference = FirebaseDatabase.getInstance().getReference().child("dataVolunteer").child(strUIDVolunteer);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pengguna m = dataSnapshot.getValue(Pengguna.class);
                    poinVolunteer = m.getPoin();
                    System.out.println("strPoin : " + poinVolunteer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getHarga() {
        reference = FirebaseDatabase.getInstance().getReference().child("hargaSampah");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelHargaSampah m = dataSnapshot.getValue(ModelHargaSampah.class);
                    strHargaBotol = m.getHargaPlastik();
                    strHargaKertas = m.getHargaKertas();
                } else {
                    strHargaBotol = "3600";
                    strHargaKertas = "500";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
