package com.example.myapplication.view.Pengajuan;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.v_daftarBank_adapter;
import com.example.myapplication.model.ModelDataSapi;
//import com.example.myapplication.model.v_daftarSapiBetina;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankSampahFragment extends Fragment {

    private RecyclerView recyclerView;
    private v_daftarBank_adapter adapter;
    private List<ModelDataSapi> modelList;

    private TextView namaVolunteer, levelVolunteer;

    private FirebaseDatabase database;
    private DatabaseReference reference, dRef;
    private FirebaseAuth auth;

    public BankSampahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomePengajuan) getActivity()).setActionBarTitle("Daftar Sapi");
        View view = inflater.inflate(R.layout.fragment_volunteer_daftar_bank_sampah, container, false);

//        namaVolunteer = (TextView) view.findViewById(R.id.volunteer_bank_nama);
//        levelVolunteer = (TextView) view.findViewById(R.id.volunteer_bank_level);

        database = FirebaseDatabase.getInstance();
//        auth = FirebaseAuth.getInstance();
//        String UID = auth.getUid();
//        dRef = database.getReference("pengguna").child(UID);
//        dRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Pengguna pengguna = dataSnapshot1.getValue(Pengguna.class);
//                    String nama = pengguna.getNama();
//                    String level = pengguna.getLevel();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        reference = database.getReference("dataBankSampah");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelDataSapi model = dataSnapshot1.getValue(ModelDataSapi.class);
                    String nomorSapi = model.getNomorSapi();
                    String beratAwal = model.getBeratAwal();
                    String jenisSapi = model.getJenisSapi();
//                    String alamat = model.getAlamat();

                    model = new ModelDataSapi(null,nomorSapi, beratAwal, jenisSapi,null);
                    modelList.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = view.findViewById(R.id.rv_daftarBankSampah);
        modelList = new ArrayList<>();
        adapter = new v_daftarBank_adapter(getActivity(), modelList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
