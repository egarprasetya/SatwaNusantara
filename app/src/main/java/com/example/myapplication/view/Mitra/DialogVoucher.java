package com.example.myapplication.view.Mitra;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.model.BaseApi;
import com.google.firebase.database.FirebaseDatabase;

public class DialogVoucher extends DialogFragment {
    private TextView kodeVoucher, namaMitra, namaPembeli, poinVoucher, namaVoucher;
    private Button verifikasi, batal;

    String kode;

    public DialogVoucher() {
    }

    public void setKodeVoucher(String kode) {
        this.kode = kode;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_voucher, container, false);

        kodeVoucher = view.findViewById(R.id.mitra_dialog_kode_voucher);
        namaVoucher = view.findViewById(R.id.mitra_dialog_nama_voucher);
        namaMitra = view.findViewById(R.id.mitra_dialog_mitra_voucher);
        namaPembeli = view.findViewById(R.id.mitra_dialog_pembeli_voucher);
        poinVoucher = view.findViewById(R.id.mitra_dialog_poin_voucher);
        verifikasi = view.findViewById(R.id.mitra_dialog_btn_verifikasi);
        batal = view.findViewById(R.id.mitra_dialog_btn_batal);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kodeVoucher.setText(kode);

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Verifikasi Voucher")
                        .setCancelable(false)
                        .setMessage("Penukaran kode " + kode + " telah telah berhasil dilakukan")
                        .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] child = {"statusVoucher", "tanggal"},
                                        nilai = {BaseApi.voucher_sudah_ditukar, BaseApi.get_tanggal()};

                                for (int i = 0; i < child.length; i++) {
                                    FirebaseDatabase.getInstance().getReference().child(BaseApi.TABEL_VOUCHER_VOLUNTEER)
                                            .child(kode).child(child[i])
                                            .setValue(nilai[i]);
                                }
                                dismiss();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
