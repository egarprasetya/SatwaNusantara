package com.example.myapplication.view.Perusahaan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.Pengguna;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CardView mUbahHarga, mDataSapi;
    private TextView nama, jumlahSapi;
    private ImageButton btnInfo;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String emailUser;

    private Button buka_kamera;
    private Bitmap hasilFoto;
    private Uri uriFoto;
    private String uid;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    DeskripsiFragment deskripsi;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        ((HomePerusahaan)getActivity()).setActionBarTitle("Menu Perusahaan");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perusahaan_home, container, false);
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        emailUser=auth.getCurrentUser().getPhoneNumber();
        nama = view.findViewById(R.id.infopoin_nama);
        jumlahSapi = view.findViewById(R.id.infopoin_poin);
        database = FirebaseDatabase.getInstance();
        final String[] namaPeternak = new String[1];

        reference = database.getReference("dataPengajuan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                modelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelPengajuan model = dataSnapshot1.getValue(ModelPengajuan.class);
                    if(model.getUsername()!=null){
                        if(model.getUsername().equalsIgnoreCase(emailUser)){
                            namaPeternak[0] = model.getNamaPeternak();
                            nama.setText(model.getNamaPeternak());
                            jumlahSapi.setText(model.getJumlahSapi());
                        }}
                }
//                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mUbahHarga = view.findViewById(R.id.cv_dataSapi);
        mUbahHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_perusahaan, new DeskripsiFragment(namaPeternak[0]));
                ft.commit();
            }
        });
        mDataSapi = view.findViewById(R.id.cv_dataPengajuan);
        mDataSapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_perusahaan, new EditDataPribadi());
                ft.commit();
            }
        });

        return view;
    }
}
