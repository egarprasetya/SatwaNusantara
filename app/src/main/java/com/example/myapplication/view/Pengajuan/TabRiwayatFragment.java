package com.example.myapplication.view.Pengajuan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RiwayatAdapterVolunteer;
import com.example.myapplication.model.BaseApi;
import com.example.myapplication.model.ModelVoucherVolunteer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TabRiwayatFragment extends Fragment {
    private RecyclerView recyclerView;
    private RiwayatAdapterVolunteer adapter;
    private List<ModelVoucherVolunteer> voucherList;
    private TextView teksKosong;

    private DatabaseReference ref;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_tab_riwayat, container, false);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerView = view.findViewById(R.id.rv_riwayatVoucher);
        voucherList = new ArrayList<>();
        teksKosong = view.findViewById(R.id.teks_kosong);

        ref.child(BaseApi.TABEL_VOUCHER_VOLUNTEER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    voucherList.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        voucherList.add(data.getValue(ModelVoucherVolunteer.class));
                    }
                    teksKosong.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    teksKosong.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new RiwayatAdapterVolunteer(getActivity(), voucherList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
