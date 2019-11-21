package com.example.myapplication.view.Pengajuan.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.volunteer.Volunteer_adapter_leaderboard;
import com.example.myapplication.model.Model_leaderboard;
import com.example.myapplication.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Volunteer_leaderboard extends Fragment {
    private RecyclerView rvLeader;

    private List<Model_leaderboard> model;
    private Volunteer_adapter_leaderboard adapter;

    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.volunteer_leaderboard,container,false);

        rvLeader = view.findViewById(R.id.volunteer_leaderboard_recycleview);

        getData();
        model = new ArrayList<>();
        adapter = new Volunteer_adapter_leaderboard(getContext(), model);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvLeader.setLayoutManager(layoutManager);
        rvLeader.setAdapter(adapter);

        return view;
    }

    private void getData() {
        reference = FirebaseDatabase.getInstance().getReference().child(Util.util_data_leaderboard);
        reference.orderByChild("desc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    model.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Model_leaderboard m = snapshot.getValue(Model_leaderboard.class);
                        String nama = m.getNama();
                        String poin = m.getPoin();
                        String desc = m.getDesc();

                        m = new Model_leaderboard(nama, poin,null, desc, null);
                        model.add(m);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Data leaderboard belum tersedia :)", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
