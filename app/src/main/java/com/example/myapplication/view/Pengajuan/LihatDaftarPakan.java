package com.example.myapplication.view.Pengajuan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DaftarPakanAdapter;
import com.example.myapplication.adapter.PengajuanAdapter;
import com.example.myapplication.model.ModelDaftarPakan;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelSampah;
import com.example.myapplication.util.Util;
import com.example.myapplication.view.Pengajuan.leaderboard.Volunteer_leaderboard;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class LihatDaftarPakan extends Fragment {
    private static final String TAG = "LihatDaftarPakan";

    private LinearLayout layoutAtas;
    private FloatingActionButton btnFoto;
    private ImageView imgFoto;
    private Button btnSimpan, btnBatal;
    private RecyclerView rvSampah;
    private TextView txtKode, txtTanggal;
    private ProgressBar pbSimpan;

    Uri uriFoto;

    private DatabaseReference reference;
    private StorageReference storageReference;

    DaftarPakanAdapter daftarPakanAdapter;
    List<ModelDaftarPakan> model;

    String strUID = "", strKode = "", strTanggalAmbil = "", strURL = "", strNama = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengajuan_daftar_pakan, container, false);

        strUID = FirebaseAuth.getInstance().getUid();
        strTanggalAmbil = Util.kode_sampah().replace(" ", "");
        strKode = strUID.substring(0, 3).toUpperCase() + strTanggalAmbil;
        dataVolunteer();

        layoutAtas = view.findViewById(R.id.volunteer_sampahku_layout_atas);
//        btnFoto = view.findViewById(R.id.volunteer_sampahku_btn_foto);
//        imgFoto = view.findViewById(R.id.volunteer_sampahku_img_sampah);
        btnSimpan = view.findViewById(R.id.volunteer_sampahku_btn_simpan);
        btnBatal = view.findViewById(R.id.volunteer_sampahku_btn_batal);
        rvSampah = view.findViewById(R.id.volunteer_sampahku_recycleview);
        txtKode = view.findViewById(R.id.volunteer_sampahku_text_kode);
        txtTanggal = view.findViewById(R.id.volunteer_sampahku_text_tanggal_sambil);
        pbSimpan = view.findViewById(R.id.volunteer_sampahku_progresbar);

        getSampah();
        model = new ArrayList<>();
        daftarPakanAdapter = new DaftarPakanAdapter(getContext(), model);
        rvSampah.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSampah.setAdapter(daftarPakanAdapter);





//        btnFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Util.REQUEST_IMAGE_CAPTURE);
//            }
//        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanGambar();
                pbSimpan.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "simpan", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void getSampah() {
        reference = FirebaseDatabase.getInstance().getReference().child("dataDaftarPakan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelDaftarPakan m = snapshot.getValue(ModelDaftarPakan.class);
                        model.add(m);
//                        Log.w(LihatDaftarPakan.class.getSimpleName(), "url=" + m.getUrlFoto());
//                        String namaPeternak = m.getNamaPeternak();
//                        String alamat = m.getAlamat();
//                        String tanggalPengajuan = m.getTanggalPengajuan();
//                        String noHp = m.getNoHp();
//                        String url = m.getUrlFoto();
//                        String jumlahSapi = m.getJumlahSapi();
////                        String stringStatus = model.getNamaPeternak();
////                        String stringUID = model.getAlamat();
////                        String stringKode = model.getTanggalPengajuan();
////                        String stringAmbil = model.getNoHp();
////                        String stringAkhit = model.getJumlahSapi();
//
//                        if (uid.equalsIgnoreCase(strUID) && status.equalsIgnoreCase(ModelSampah.VERIFIKASI_MENUNGGU)) {
//                            m = new ModelPengajuan(null, namaPeternak, null, alamat, null, jumlahSapi, noHp, tanggalPengajuan, url);
//                        }
                    }
                    daftarPakanAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Util.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uriFoto = getImageUri(getContext(), bitmap);

            layoutAtas.setVisibility(View.VISIBLE);
            imgFoto.setImageBitmap(bitmap);
            txtKode.setText(strKode);
            txtTanggal.setText(Util.tanggalSekarang());
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        return Uri.parse(path);
    }

    private void simpanGambar() {
        storageReference = FirebaseStorage.getInstance().getReference("sampah_volunteer").child(strKode);
        Toast.makeText(getContext(), "simpan gambar", Toast.LENGTH_SHORT).show();
        UploadTask uploadTask = storageReference.putFile(uriFoto);
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
                if (task.isSuccessful()) {
                    pbSimpan.setVisibility(View.GONE);
                    Uri uri = task.getResult();
                    strURL = uri.toString();
                    simpanData();
                }
            }
        });
    }

    //    String kodeSampah, String uidVolunteer, String namaVolunteer, String uidBank, String urlFoto, String jenisSampah,
// String poinSampah, String tanggalSubmit, String tanggalAkhir, String hargaSampah, String beratSampah, String statusVerifikasi
    private void simpanData() {
        ModelDaftarPakan m = new ModelDaftarPakan(strKode, strUID, strNama, "-");
        reference = FirebaseDatabase.getInstance().getReference().child("dataDaftarPakan").child(strKode);
        reference.setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Sampah berhasil disimpan", Toast.LENGTH_SHORT).show();
                    selesai();
                }
            }
        });
    }

    private void dataVolunteer() {
        reference = FirebaseDatabase.getInstance().getReference().child("dataDaftarPakan").child(strUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Pengguna p = dataSnapshot.getValue(Pengguna.class);
//                    strNama = p.getNama();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void selesai() {
        layoutAtas.setVisibility(View.GONE);
        pbSimpan.setVisibility(View.GONE);
    }
}
