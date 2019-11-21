package com.example.myapplication.view.Pengajuan;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.BaseApi;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelVoucher;
import com.example.myapplication.model.Pengguna;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.util.Util.REQUEST_IMAGE_CAPTURE;
import static com.example.myapplication.util.Util.WRITE_EXTERNAL;

public class MemasukanPengajuan extends Fragment {

    private final String TAG = MemasukanPengajuan.class.getSimpleName();

    private EditText namaPeternak, username, alamat, noKtp, jumlahSapi, noHp;
    private ImageView fotoKtp;
    private Button tambah, batal;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private Button buka_kamera;
    private Bitmap hasilFoto;
    private Uri uriFoto;
    private String uid;
    private String namaMitra;
    ImageView fotoMakanan;

    public MemasukanPengajuan() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan_memasukan, container, false);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference();

        namaPeternak = view.findViewById(R.id.input_pengajuan_namaPeternak);
        username = view.findViewById(R.id.input_pengajuan_username);
        alamat = view.findViewById(R.id.input_pengajuan_alamat);
        noKtp = view.findViewById(R.id.input_pengajuan_noKtp);
        jumlahSapi = view.findViewById(R.id.input_pengajuan_jumlahSapi);
        noHp = view.findViewById(R.id.input_pengajuan_noHp);

        fotoMakanan = view.findViewById(R.id.foto_pengajuan_fotoKtp);

//        fotoKtp = view.findViewById(R.id.foto_pengajuan_fotoKtp);

        batal = view.findViewById(R.id.btn_voucher_batal);
        tambah = view.findViewById(R.id.btn_submit_sapi);

        buka_kamera = view.findViewById(R.id.upload_pengajuan_fotoKtp);
        buka_kamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                cekIzin();
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);

            }
        });
        fotoMakanan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openFileChooser();
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

//        fotoKtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL);
//                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
//                } else {
//                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
//                }
//            }
//        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strNamaPeternak = namaPeternak.getText().toString().trim();
                final String strUsername = username.getText().toString().trim();
                final String strAlamat = alamat.getText().toString().trim();
                final String strNoKtp = noKtp.getText().toString().trim();
                final String strJumlahSapi = jumlahSapi.getText().toString().trim();
                final String strNoHp = noHp.getText().toString().trim();


                boolean kirim = false;

                if (strNamaPeternak.isEmpty()) {
                    namaPeternak.requestFocus();
                    namaPeternak.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strUsername.isEmpty()) {
                    username.requestFocus();
                    username.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strAlamat.isEmpty()) {
                    alamat.requestFocus();
                    alamat.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strNoKtp.isEmpty()) {
                    noKtp.requestFocus();
                    noKtp.setError("Isi terlebih dahulu");
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
                    noHp.requestFocus();
                    noHp.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                final String formattedDate = df.format(c);

                if (kirim) {
                    final StorageReference ref = FirebaseStorage.getInstance().getReference(BaseApi.DIR_FOTO_PENGAJUAN).child(uriFoto.getLastPathSegment());

                    UploadTask uploadFoto = ref.putFile(uriFoto);

                    Task<Uri> uploading = uploadFoto.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Gagal upload foto ktp:" + task.getException());
                            }
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                String urlDownload = task.getResult().toString();
                                /*
private String idPengajuan;
    private String namaPeternak;
    private String username;
    private String alamat;
    private String noKtp;
    private String jumlahSapi;
    private String urlFoto;
    private String noHp;
    private String tanggalPengajuan;
* */
                                ModelPengajuan pengajuan = new ModelPengajuan(uid, strNamaPeternak, strUsername, strAlamat, strNoKtp,  strJumlahSapi, urlDownload,strNoHp, formattedDate);
                                dbRef.child("dataPengajuan").push().setValue(pengajuan);
                                Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.w(TAG, "Gagal mengambil url pengajuan");
                            }
                        }
                    });
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ModelPengajuan");
                builder.setMessage("Batal menambahkan pengajuan?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        namaPeternak.setText("");
                        username.setText("");
                        alamat.setText("");
                        noKtp.setText("");
                        jumlahSapi.setText("");
                        noHp.setText("");
                        fotoKtp.setImageResource(R.drawable.bg_img_voucher);
                    }
                });
            }
        });
        return view;
    }
    private void cekIzin() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            hasilFoto = (Bitmap) data.getExtras().get("data");
            uriFoto = getImageUri(getContext(), hasilFoto);

            fotoKtp.setImageBitmap(hasilFoto);
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
                    Toast.makeText(getContext(), "Gagal Upload Foto", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Fragment Sampahku", "Masuk resul");
        switch (requestCode) {
            case WRITE_EXTERNAL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Fragment Sampahku", "disetujui");
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }
}
