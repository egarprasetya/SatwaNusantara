package com.example.myapplication.view.Pengajuan;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BeliVoucherAdapter;
import com.example.myapplication.model.BaseApi;
import com.example.myapplication.model.ModelPoin;
import com.example.myapplication.model.ModelVoucher;
import com.example.myapplication.model.ModelVoucherVolunteer;
import com.example.myapplication.model.Pengguna;
import com.example.myapplication.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class TabVoucherFragment extends Fragment {
    private static final String TAG = TabVoucherFragment.class.getSimpleName();
    private Button tukar;

    private RecyclerView recyclerView;
    private BeliVoucherAdapter adapter;
    private List<ModelVoucher> listVoucher;
    private View view;

    private String poin;
    private int poinSekarang;
    private String key;

    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private TextView listKosong;
    private String nama;

    public TabVoucherFragment() {
        // Required empty public constructor
    }

    String UID = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer_tab_voucher, container, false);
        this.view = view;
        dbRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        UID = auth.getUid();
        listKosong = view.findViewById(R.id.teks_kosong);

        recyclerView = view.findViewById(R.id.rv_voucher);
        listVoucher = new ArrayList<>();

        dbRef.child("dataVolunteer").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Pengguna m = dataSnapshot.getValue(Pengguna.class);
                    poinSekarang = Integer.valueOf(m.getPoin());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Gagal Mengambil data poin", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Gagal mengambil info poin pengguna:" + databaseError.getDetails());
            }
        });

        dbRef.child("dataVoucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listVoucher.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ModelVoucher perVoucher = data.getValue(ModelVoucher.class);
                        int jml = Integer.valueOf(perVoucher.getJumlahKuota());
                        Log.d("TABVOU", "Jumlah Voucher:" + jml + ", jml voucher string" + perVoucher.getJumlahKuota());
                        Log.d("TabVou", "status voucher:" + perVoucher.getStatusVoucher());
                        if (perVoucher.getStatusVoucher().equals(ModelVoucher.VOUCHER_BERLAKU) && jml > 0) {
                            perVoucher.setKey(data.getKey());
                            listVoucher.add(perVoucher);
                        } else {
                            Log.d("TabVou", "Voucher kosong");
                            if (listVoucher.size() == 0) {
                                recyclerView.setVisibility(View.GONE);
                                listKosong.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    listKosong.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    listKosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new BeliVoucherAdapter(listVoucher, new BeliVoucherAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(final ModelVoucher model) {
                if (poinSekarang == 0) {
                    Util.toast(getContext(), "Poin Anda Habis");
                } else {
                    prosesBeli(model);
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }


    private void prosesBeli(final ModelVoucher voucher) {
        loading(true);
        int hargaPoin = Integer.valueOf(voucher.getHargaPoin());
        if (poinSekarang >= hargaPoin) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                    .setTitle("Beli Voucher")
                    .setMessage("Yakin ingin membeli voucher " + voucher.getNamaVoucher() + " seharga " + voucher.getHargaPoin() + " poin?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {

                            dbRef = FirebaseDatabase.getInstance().getReference().child(BaseApi.TABEL_VOUCHER).child(voucher.getKey()).child("jumlahKuota");
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final int jml = Integer.valueOf(dataSnapshot.getValue().toString());
                                    if (jml > 0) {
                                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        final String kode = BaseApi.get_kode(UID);
                                        ModelVoucherVolunteer beli = new ModelVoucherVolunteer(nama, voucher.getNamaMitra(), voucher.getUidMitra(), kode, voucher.getUrl_foto(), voucher.getNamaVoucher(), voucher.getDeskripsi(), voucher.getHargaPoin(), ModelVoucherVolunteer.VOUCHER_DIBELI, "", UID);

                                        dbRef = FirebaseDatabase.getInstance().getReference();
                                        dbRef.child(BaseApi.TABEL_VOUCHER_VOLUNTEER).child(kode)
                                                .setValue(beli).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //update jumlah voucher di mitra
                                                updateVoucher(voucher.getKey(), jml);

                                                //mengurangi poin volunteer
                                                updatePoinVolunteer(user.getUid(), voucher.getHargaPoin());

                                                //mengirim ke data poin volunteer
                                                dbRef = FirebaseDatabase.getInstance().getReference(BaseApi.TABEL_POIN_VOLUNTEER).child(user.getUid());
                                                ModelPoin poin = new ModelPoin(voucher.getHargaPoin(), user.getUid(), ModelPoin.DARI_PEMBELIAN_VOUCHER, voucher.getNamaVoucher());
                                                dbRef.push().setValue(poin);


                                                AlertDialog.Builder dialogBerhasil = new AlertDialog.Builder(getContext()).setTitle("Berhasil").setMessage("Pembelian Berhasil, Kode Voucher Anda : " + kode + "\nCek Voucher Anda Di Tab Riwayat").setPositiveButton("kembali", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                                dialogBerhasil.show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TabVoucherFragment.class.getSimpleName(), "Gagal mengirim data:" + e.getMessage());
                                                Toast.makeText(getContext(), "Gagal membeli voucher", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            }
                                        });
                                        loading(false);
                                    } else {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext()).setMessage("Kuota voucher sudah habis").setPositiveButton("kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    })
                    .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        } else {
            Util.alertDialog(getContext(), "", "Poin anda tidak cukup untuk membeli voucher", "", null, "", null);
            loading(false);
        }
    }

    private void loading(boolean load) {
        if (load) {
            view.findViewById(R.id.loading_overlay).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.loading_overlay).setVisibility(View.GONE);
        }
    }

    private void updateVoucher(String key, int kuota) {
        String jumlahBaru = String.valueOf(kuota - 1);
        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child(BaseApi.TABEL_VOUCHER).child(key).child("jumlahKuota").setValue(jumlahBaru);
    }

    private void updatePoinVolunteer(String uid, String harga) {
        int hargaVoucher = Integer.valueOf(harga);
        poinSekarang = poinSekarang - hargaVoucher;

        poin = String.valueOf(poinSekarang);

        FirebaseDatabase.getInstance().getReference().child("dataVolunteer")
                .child(uid).child("poin").setValue(poin)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Sisa : " + poin, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//        dbRef = FirebaseDatabase.getInstance().getReference(BaseApi.TABEL_PENGGUNA).child(uid).child(key).child("poin");
//        dbRef.setValue(poin);
    }

}
