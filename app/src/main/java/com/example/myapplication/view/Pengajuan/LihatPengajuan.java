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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BeliVoucherAdapter;
import com.example.myapplication.adapter.EditPengajuanAdapter;
import com.example.myapplication.adapter.PengajuanAdapter;
import com.example.myapplication.model.ModelDataSapi;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelSampah;
import com.example.myapplication.model.ModelVoucher;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.Util;
import com.example.myapplication.view.HalamanMasuk;
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

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class LihatPengajuan extends Fragment {
    private static final String TAG = "LihatPengajuan";

    private EditPengajuanAdapter adapter;
    private List<ModelPengajuan> listPengajuan;

    private LinearLayout layoutAtas;
    private FloatingActionButton btnFoto;
    private ImageView imgFoto;
    private Button btnSimpan, btnBatal;
    private RecyclerView rvSampah;
    private TextView txtKode, txtTanggal;
    private ProgressBar pbSimpan;
    Uri uriFoto;

    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;

    String UID = "";
    int coba=0;
    private DatabaseReference reference;
    private StorageReference storageReference;

    PengajuanAdapter pengajuanAdapter;
    List<ModelPengajuan> model;

    String strUID = "", strKode = "", strTanggalAmbil = "", strURL = "", strNama = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_sampahku, container, false);
        dbRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        UID = auth.getUid();


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
        pengajuanAdapter = new PengajuanAdapter(getContext(), model, new PengajuanAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ModelPengajuan model) {
                Intent asu = new Intent(getActivity(), MemasukkanDataSapi2.class);
                asu.putExtra("NamaPeternak", model.getNamaPeternak());
                asu.putExtra("jumlahSapi", model.getJumlahSapi());
                getActivity().startActivity(asu);
            }
        });
        rvSampah.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSampah.setAdapter(pengajuanAdapter);
        View rootView = inflater.inflate(R.layout.list_item_pengajuan_lihat, container, false);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();

        dbRef.child("dataPengajuan").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelPengajuan m = dataSnapshot.getValue(ModelPengajuan.class);
                    coba = Integer.valueOf(m.getNoHp());
                    System.out.println("cekkkkkk");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Gagal Mengambil data poin", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Gagal mengambil info poin pengguna:" + databaseError.getDetails());
            }
        });
//        dbRef.child("dataSapi").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()) {
//                    for (DataSnapshot perData : dataSnapshot.getChildren()) {
//                        if (perData.getValue(ModelDataSapi.class).getNamaPeternak().equalsIgnoreCase(namaPeternak1)) {
//                            nomorSapi1++;
//                            nomorSapi.setText("" + nomorSapi1);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        Log.w(String.valueOf(coba),"bisa");
        adapter = new EditPengajuanAdapter(listPengajuan, new EditPengajuanAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final ModelPengajuan model) {
                Log.w(String.valueOf(coba),"bisa");
                if (coba == 0) {
                    Util.toast(getContext(), "Poin Anda Habis");
                } else {
                    Util.toast(getContext(), "Gagal");
                }
            }
        });
//

        return view;
    }

    private void getSampah() {
        reference = FirebaseDatabase.getInstance().getReference().child("dataPengajuan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelPengajuan m = snapshot.getValue(ModelPengajuan.class);
                        model.add(m);
                        Log.w(LihatPengajuan.class.getSimpleName(), "url=" + m.getUrlFoto());

                    }
                    pengajuanAdapter.notifyDataSetChanged();
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
        ModelSampah m = new ModelSampah(strKode, strUID, strNama, "-", strURL, "-", "0", "-", "-", "-", "-", ModelSampah.VERIFIKASI_MENUNGGU);
        reference = FirebaseDatabase.getInstance().getReference().child("dataPengajuan").child(strKode);
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
        reference = FirebaseDatabase.getInstance().getReference().child("dataPengajuan").child(strUID);
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
