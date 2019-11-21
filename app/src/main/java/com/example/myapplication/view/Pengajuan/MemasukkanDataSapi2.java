package com.example.myapplication.view.Pengajuan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelDataSapi;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelVoucher;
import com.example.myapplication.model.Pengguna;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.myapplication.util.Util.REQUEST_IMAGE_CAPTURE;
import static com.example.myapplication.util.Util.WRITE_EXTERNAL;

public class MemasukkanDataSapi2 extends AppCompatActivity {

    private final String TAG = MemasukkanDataSapi2.class.getSimpleName();

    private EditText username, beratSapi, jenisKelamin;
    private TextView namaPeternak, jumlahSapi, nomorSapi;
    private Spinner jenisSapi;
    private Button tambah, batal;
    private Context context;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private Button buka_kamera;
    private Bitmap hasilFoto;
    private Uri uriFoto;
    private String uid;
    private String namaMitra;

    private String emailUser;
    private String jenisKelamin1;
    private String namaPeternak1;
    private String jenisSapi1;
    private int nomorSapi1 = 1;
    private int jumlahSapi1 = 0;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memasukkan_data_sapi2);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();
        emailUser = auth.getCurrentUser().getEmail();

        namaPeternak1 = getIntent().getStringExtra("NamaPeternak");
        jumlahSapi1 = Integer.valueOf(getIntent().getStringExtra("jumlahSapi"));

        switchCompat = findViewById(R.id.input_pengajuan_jenis_kelamin);
        namaPeternak = findViewById(R.id.memasukan_data_sapi_nama);
        jumlahSapi = findViewById(R.id.memasukan_data_sapi_jumlah);
        username = findViewById(R.id.input_pengajuan_username);
        beratSapi = findViewById(R.id.memasukan_data_sapi_berat);
        nomorSapi = findViewById(R.id.info_nomor_sapi);
        batal = findViewById(R.id.btn_voucher_batal);
        tambah = findViewById(R.id.btn_submit_sapi);
        jenisSapi = findViewById(R.id.input_pengajuan_jenis_sapi);

        dbRef.child("dataSapi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot perData : dataSnapshot.getChildren()) {
                        if (perData.getValue(ModelDataSapi.class).getNamaPeternak().equalsIgnoreCase(namaPeternak1)) {
                            nomorSapi1++;
                            nomorSapi.setText("" + nomorSapi1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        namaPeternak.setText(namaPeternak1);
        jumlahSapi.setText("" + jumlahSapi1);

        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.jenis, R.layout.list_spinner_item);
        adapterSpinner.setDropDownViewResource(R.layout.list_spinner_item);
        jenisSapi.setAdapter(adapterSpinner);
        jenisSapi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jenisSapi1 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked()) {
                    jenisKelamin1 = "betina";
                } else {
                    jenisKelamin1 = "jantan";
                }
            }
        });

        dbRef.child("dataPengajuan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                modelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelPengajuan model = dataSnapshot1.getValue(ModelPengajuan.class);
                    if (model.getUsername() != null) {
                        if (model.getUsername().equalsIgnoreCase(emailUser)) {

                            System.out.println("bisacoyyyyyyyyyyyyyyyyyyyyyyyy" + model.getUsername());
//                        modelList.add(model);
                            namaPeternak.setText(model.getNamaPeternak());
                            namaPeternak1 = model.getNamaPeternak();
                            jumlahSapi.setText(model.getJumlahSapi());
                            jumlahSapi1 = Integer.parseInt(model.getJumlahSapi());

                        }
                    }
                }
//                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dbRef.child("pengguna").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    namaMitra = data.getValue(Pengguna.class).getNama();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {

                }
                final String strNamaPeternak = namaPeternak.getText().toString().trim();

                final String strJumlahSapi = jumlahSapi.getText().toString().trim();
                final String strNoHp = nomorSapi.getText().toString().trim();


                boolean kirim = false;

                if (strNamaPeternak.isEmpty()) {
                    namaPeternak.requestFocus();
                    namaPeternak.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }




                if (strJumlahSapi.isEmpty()) {
                    jumlahSapi.requestFocus();
                    jumlahSapi.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }
                if (strNoHp.isEmpty()) {
                    nomorSapi.requestFocus();
                    nomorSapi.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c);

                if (kirim) {
                    ModelDataSapi dataSapi = new ModelDataSapi(
                            beratSapi.getText().toString(),
                            jenisSapi1,
                            jenisKelamin1,
                            namaPeternak1,
                            String.valueOf(nomorSapi1)
                    );
                    dbRef.child("dataSapi").push().setValue(dataSapi).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Data Sapi Ditambah", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("ModelPengajuan");
                builder.setMessage("Batal menambahkan pengajuan?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        namaPeternak.setText("");
                        username.setText("");

                        jumlahSapi.setText("");
                        nomorSapi.setText("");
                    }
                });
            }
        });
    }

    private void cekIzin() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            hasilFoto = (Bitmap) data.getExtras().get("data");
            uriFoto = getImageUri(getApplicationContext(), hasilFoto);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }

    private void kirimStorage(final StorageReference storageReference, Uri uri, final String namaVoucher, final String deskripsi, final String hargaPoin, final String jumlahKuota, final String uid, final String key) {
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String url = storageReference.getDownloadUrl().toString();
                    Log.d("TabBuatVoucherFragment", "URL foto ModelVoucher: " + url);

                    ModelVoucher voucher = new ModelVoucher(uid, namaMitra, url, namaVoucher, deskripsi, hargaPoin, jumlahKuota, ModelVoucher.VOUCHER_BERLAKU);
                    dbRef.child("dataVoucher").child(key).setValue(voucher);
                } else {
                    Log.w("TabBuatVoucherFragment", "Gagal Upload Foto" + task.getException());
                    Toast.makeText(getApplicationContext(), "Gagal Upload Foto", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Fragment Sampahku", "Masuk resul");
        if (requestCode == WRITE_EXTERNAL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Fragment Sampahku", "disetujui");
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }
    }
}
