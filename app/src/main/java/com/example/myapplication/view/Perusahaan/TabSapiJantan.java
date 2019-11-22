package com.example.myapplication.view.Perusahaan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.p_ubahHarga_adapter;
import com.example.myapplication.model.ModelDataSapi;
import com.example.myapplication.model.ModelDataSapi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TabSapiJantan extends Fragment {
    private RecyclerView recyclerView;
    private p_ubahHarga_adapter adapter;
    private List<ModelDataSapi> modelList;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public TabSapiJantan() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sapi_tab_jantan, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("dataSapi");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ModelDataSapi model = dataSnapshot1.getValue(ModelDataSapi.class);
                    String beratAwal = model.getBeratAwal();
                    String jenisSapi = model.getJenisSapi();
                    String jenisKelamin = model.getJenisKelamin();
                    String namaPeternak = model.getNamaPeternak();
                    String nomorSapi = model.getNomorSapi();
                    model = new ModelDataSapi( beratAwal, jenisSapi,jenisKelamin, namaPeternak, nomorSapi);
                    if(model.getJenisKelamin().equalsIgnoreCase("jantan")){
                        modelList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView = view.findViewById(R.id.rv_jantan);
        modelList = new ArrayList<>();
        adapter = new p_ubahHarga_adapter(getActivity(), modelList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
