package com.example.myapplication.view.Pengajuan;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelSampah;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.Util;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogSampah extends DialogFragment {

    private static final String TAG = "Dialog ModelSampah";

    private ImageView imgPreview;
    private TextView kode, tanggal;
    private Button kirim;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private StorageReference stRef;
    private String nama;

    private Bitmap img;
    private Uri imgUri;

    public DialogSampah() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_sampah, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();

        imgPreview = view.findViewById(R.id.img_preview);
        kode = view.findViewById(R.id.kode_sampah);
        tanggal = view.findViewById(R.id.tanggal_sampah);
        kirim = view.findViewById(R.id.kirim_sampah);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String uid = user.getUid();
        String tanggalAwal = "";
        String tanggalAkhir = "";

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
        tanggalAwal = formatter.format(new Date());

        Date date = null;
        try {
            date = formatter.parse(tanggalAwal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendarPlus = Calendar.getInstance();
        calendarPlus.setTime(date);
        calendarPlus.add(Calendar.HOUR, 2);
        Date akhir = calendarPlus.getTime();

        tanggalAkhir = formatter.format(akhir);

        SimpleDateFormat menit = new SimpleDateFormat("mm");
        SimpleDateFormat detik = new SimpleDateFormat("ss");
        String strMenit = menit.format(new Date());
        String strDetik = detik.format(new Date());
        String strUID = uid.substring(0, 3);

        final String stringKode = md5(strUID + strMenit + strDetik);
        final String strKode = stringKode.substring(0, 7);

        kode.setText(strKode);
        tanggal.setText(tanggalAwal);
        imgPreview.setImageBitmap(img);

        final String tglAwal = tanggalAwal;
        final String tglAkhir = tanggalAkhir;
        dbRef.child("Pengguna").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Pengguna pen = child.getValue(Pengguna.class);
                    final String stringNama = pen.getNama();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //(String kodeSampah, String uidVolunteer, String namaVolunteer, String uidBank, String urlFoto, String jenisSampah, String poinSampah, String tanggalSubmit, String tanggalAkhir, String hargaSampah, String beratSampah, String statusVerifikasi)
                ModelSampah tempSampah = new ModelSampah(strKode, uid, null, null, null, null, "0", tglAwal, tglAkhir, null, null, ModelSampah.VERIFIKASI_MENUNGGU);
                dbRef.child("dataSampah").child(strKode).setValue(tempSampah)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                simpan_db(strKode,uid,tglAwal,tglAkhir);
                            }
                        });
//                        (tempSampah, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
//                        if (databaseError == null) {
////                            String key = databaseReference.getKey();
////                            StorageReference stor = FirebaseStorage.getInstance()
////                                    .getReference("sampah_volunteer")
////                                    .child(uid)
////                                    .child(key)
////                                    .child(imgUri.getLastPathSegment());
//                            System.out.println("masudk simpan_db");
//                            simpan_db(strKode,uid, tglAwal, tglAkhir);
//                        } else {
//                            Log.w(TAG, "Database Error: " + databaseError.getDetails());
//                        }
//                    }
//                });
//                Toast.makeText(getContext(), "Sukses", Toast.LENGTH_LONG).show();
//                dismiss();
            }
        });
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public void setImgUri(Uri imgUri) {
        this.imgUri = imgUri;
    }

    private void simpan_db(final String kode,final String uid, final String tAwal, final String tAkhir) {
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("dataSampah").child(Util.kode_sampah());
        UploadTask uploadTask = storageReference.putFile(imgUri);
        dismiss();
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "then: ", task.getException());
                }
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri url = task.getResult();
                    String URL_FOTO = url.toString();
                    System.out.println("url foto : " +URL_FOTO);
                    ModelSampah sampah = new ModelSampah(kode, uid, nama, "-", URL_FOTO, "-", "0", tAwal, tAkhir, "0", "0", ModelSampah.VERIFIKASI_MENUNGGU);
                    FirebaseDatabase.getInstance().getReference("dataSampah")
                            .child(kode).setValue(sampah);
                }
            }
        });
    }

    private void kirimDB(final StorageReference storageReference, Uri uri, final String key, final String kode, final String tAwal, final String tAkhir, final String uid) {
        storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String url = storageReference.getDownloadUrl().toString();
                    Log.d(TAG, "Download URL : " + url);
                    ModelSampah sampah = new ModelSampah(kode, uid, nama, null, url, null, "0", tAwal, tAkhir, null, null, ModelSampah.VERIFIKASI_MENUNGGU);
                    dbRef.child("dataSampah").child(key).setValue(sampah);
                } else {
                    Toast.makeText(getContext(), "Gagal Mengupload Foto : " + task.getException(), Toast.LENGTH_LONG).show();
                    Log.w(TAG, "Image upload task was not successful.", task.getException());
                }
            }
        });
    }

}
