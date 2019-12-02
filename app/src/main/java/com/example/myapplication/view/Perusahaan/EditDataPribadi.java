package com.example.myapplication.view.Perusahaan;

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

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.dataPribadiAdapter;
import com.example.myapplication.model.BaseApi;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelVoucher;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.view.Pengajuan.MemasukanPengajuan;
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
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.util.Util.REQUEST_IMAGE_CAPTURE;
import static com.example.myapplication.util.Util.WRITE_EXTERNAL;

public class EditDataPribadi extends Fragment {

    private final String TAG = MemasukanPengajuan.class.getSimpleName();
    private Context context;

    private EditText namaPeternak1, username1, alamat1, noKtp1, jumlahSapi1, noHp1;
    private String idPengajuan;
    public ImageView foto;
    private ImageView fotoKtp;
    private Button tambah, batal;
    private dataPribadiAdapter adapter1;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private Button buka_kamera;
    private Bitmap hasilFoto;
    private Uri uriFoto;
    private String uid;
    private String namaMitra;
    private String emailUser;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<ModelPengajuan> modelList;
    public ModelPengajuan model2;

    public EditDataPribadi() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan_memasukan, container, false);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        emailUser = auth.getCurrentUser().getPhoneNumber();
        dbRef = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();

        namaPeternak1 = view.findViewById(R.id.input_pengajuan_namaPeternak);
        username1 = view.findViewById(R.id.input_pengajuan_username);
        alamat1 = view.findViewById(R.id.input_pengajuan_alamat);
        noKtp1 = view.findViewById(R.id.input_pengajuan_noKtp);
        jumlahSapi1 = view.findViewById(R.id.input_pengajuan_jumlahSapi);
        noHp1 = view.findViewById(R.id.input_pengajuan_noHp);
        fotoKtp = view.findViewById(R.id.foto_pengajuan_fotoKtp);
        batal = view.findViewById(R.id.btn_voucher_batal);
        tambah = view.findViewById(R.id.btn_submit_sapi);

        reference = database.getReference("dataPengajuan");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                modelList.clear();
                for (DataSnapshot perData : dataSnapshot.getChildren()) {
                    ModelPengajuan model = perData.getValue(ModelPengajuan.class);
                    if (model.getUsername() != null) {
                        if (model.getUsername().equalsIgnoreCase(emailUser)) {
                            idPengajuan = (model.getIdPengajuan());
                            namaPeternak1.setText(model.getNamaPeternak());
                            username1.setText(model.getUsername());
                            alamat1.setText(model.getAlamat());
                            noKtp1.setText(model.getNoKtp());
                            jumlahSapi1.setText(model.getJumlahSapi());
                            noHp1.setText(model.getNoHp());
                            Glide.with(getContext()).load(model.getUrlFoto()).into(fotoKtp);
                        }
                    }
                }
//                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        buka_kamera = view.findViewById(R.id.upload_pengajuan_fotoKtp);
//        buka_kamera.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//                cekIzin();
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
//
//            }
//        });

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

        fotoKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL);
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
                } else {
                    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strNamaPeternak = namaPeternak1.getText().toString().trim();
                final String strUsername = username1.getText().toString().trim();
                final String strAlamat = alamat1.getText().toString().trim();
                final String strNoKtp = noKtp1.getText().toString().trim();
                final String strJumlahSapi = jumlahSapi1.getText().toString().trim();
                final String strNoHp = noHp1.getText().toString().trim();


                boolean kirim = false;

                if (strNamaPeternak.isEmpty()) {
                    namaPeternak1.requestFocus();
                    namaPeternak1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strUsername.isEmpty()) {
                    username1.requestFocus();
                    username1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strAlamat.isEmpty()) {
                    alamat1.requestFocus();
                    alamat1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if (strNoKtp.isEmpty()) {
                    noKtp1.requestFocus();
                    noKtp1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }
                if (strJumlahSapi.isEmpty()) {
                    jumlahSapi1.requestFocus();
                    jumlahSapi1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }
                if (strNoHp.isEmpty()) {
                    noHp1.requestFocus();
                    noHp1.setError("Isi terlebih dahulu");
                    kirim = false;
                } else {
                    kirim = true;
                }

                if(uriFoto == null) {
                    Toast.makeText(getContext(), "Foto Kosong", Toast.LENGTH_LONG).show();
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

                                dbRef.child("dataPengajuan").child(idPengajuan).child("namaPeternak").setValue(strNamaPeternak);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("username").setValue(strUsername);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("alamat").setValue(strAlamat);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("noKtp").setValue(strNoKtp);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("jumlahSapi").setValue(strJumlahSapi);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("urlFoto").setValue(urlDownload);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("noHp").setValue(strNoHp);
                                dbRef.child("dataPengajuan").child(idPengajuan).child("tanggalPengajuan").setValue(formattedDate);

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
                        namaPeternak1.setText("");
                        username1.setText("");
                        alamat1.setText("");
                        noKtp1.setText("");
                        jumlahSapi1.setText("");
                        noHp1.setText("");
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
        } else {
            uriFoto = null;
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