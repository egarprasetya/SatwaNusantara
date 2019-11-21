package com.example.myapplication.view.Pengajuan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TabAdapter;
import com.example.myapplication.model.Pengguna;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoPoinFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TextView nama, poin, level;

    public InfoPoinFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((HomePengajuan) getActivity()).setActionBarTitle("Info Poin");

        View view = inflater.inflate(R.layout.fragment_volunteer_info_poin, container, false);

        nama = view.findViewById(R.id.infopoin_nama);
        poin = view.findViewById(R.id.infopoin_poin);
        level = view.findViewById(R.id.infopoin_level);

        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        get_poin(UID);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("pengguna").child(UID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Pengguna model = dataSnapshot1.getValue(Pengguna.class);
                    String stringNama = model.getNama();
                    String stringLevel = model.getLevel();

                    nama.setText(stringNama);
                    level.setText(stringLevel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tabLayout = view.findViewById(R.id.tab_infopoin);
        viewPager = view.findViewById(R.id.pager_infopoin);

        TabAdapter infopoin_adapter = new TabAdapter(getChildFragmentManager());
        infopoin_adapter.tambahFragment(new TabPoinFragment(), "Poin");
        infopoin_adapter.tambahFragment(new TabVoucherFragment(), "Voucher");
        infopoin_adapter.tambahFragment(new TabRiwayatFragment(), "Riwayat");

        viewPager.setAdapter(infopoin_adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void get_poin(String UID){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("dataVolunteer").child(UID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Pengguna m=dataSnapshot.getValue(Pengguna.class);
                    String str_poin =m.getPoin();
                    poin.setText(str_poin);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
