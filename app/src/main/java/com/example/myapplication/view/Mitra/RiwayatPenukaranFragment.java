package com.example.myapplication.view.Mitra;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.RiwayatAdapterMitra;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatPenukaranFragment extends Fragment {
    private static final String TAG = "RiwayatPenukaranFragmen";
    private RecyclerView recyclerView;
    private RiwayatAdapterMitra adapter;
    private List<ModelVoucherVolunteer> modelList;
    private EditText kodeVoucher;
    private ImageButton hapusKode, cekKode;

    private DatabaseReference ref;
    private FirebaseAuth auth;

    public RiwayatPenukaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomeMitra) getActivity()).setActionBarTitle("Riwayat Penukaran");
        View view = inflater.inflate(R.layout.fragment_mitra_riwayat_penukaran, container, false);
        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        kodeVoucher = view.findViewById(R.id.mitra_input_kode_voucher);
        hapusKode = view.findViewById(R.id.mitra_btn_hapus_kode_voucher);
        cekKode = view.findViewById(R.id.mitra_btn_cek_voucher);
        recyclerView = view.findViewById(R.id.rv_riwayatPenukaran);

        cekKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stringKode = kodeVoucher.getText().toString().toUpperCase();

                if (stringKode.isEmpty()){
                    Toast.makeText(getContext(), "Kode kosong", Toast.LENGTH_SHORT).show();
                return;
                }

                cek_kode(stringKode);
            }
        });

        hapusKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kodeVoucher.setText("");
            }
        });
        String UID = FirebaseAuth.getInstance().getUid();
        get_data(UID);
        modelList = new ArrayList<>();
        adapter = new RiwayatAdapterMitra(getActivity(), modelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void cek_kode(final String kode){
        FirebaseDatabase.getInstance().getReference().child(BaseApi.TABEL_VOUCHER_VOLUNTEER).child(kode)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DialogVoucher d=new DialogVoucher();
                            d.setKodeVoucher(kode);
                            d.show(getFragmentManager(),TAG);
                        }else{
                            Toast.makeText(getContext(), "Kode tidak ada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void get_data(final String UID) {
        ref = FirebaseDatabase.getInstance().getReference().child(BaseApi.TABEL_VOUCHER_VOLUNTEER);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    modelList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ModelVoucherVolunteer m = snapshot.getValue(ModelVoucherVolunteer.class);
                        String nama = m.getNamaVoucher();
                        String harga = m.getHargaPoin();
                        String pemesan = m.getNamaVolunteer();
                        String url = m.getUrl_foto();
                        String status = m.getStatusVoucher();
                        String uid = m.getUidMitra();
                        String tanggal = m.getTanggal();

                        if (uid.equalsIgnoreCase(UID) && status.equalsIgnoreCase(BaseApi.voucher_sudah_ditukar)) {
                            m = new ModelVoucherVolunteer(pemesan, null, null, null, url, nama, null, harga, status, tanggal, null);
                            modelList.add(m);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
