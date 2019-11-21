package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelSampah;

import java.util.List;


public class Verifikasi_Adapter extends RecyclerView.Adapter<Verifikasi_Adapter.MyHolder> {

    List<ModelSampah> list;
    Context context;

    public Verifikasi_Adapter(List<ModelSampah> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_bank_verifikasi, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ModelSampah model = list.get(position);
        holder.kode.setText(model.getKodeSampah());
        holder.submit.setText(model.getTanggalSubmit());
        holder.berat.setText(model.getBeratSampah());
        holder.jenis.setText(model.getJenisSampah());
        holder.poin.setText(model.getPoinSampah());
        holder.harga.setText(model.getHargaSampah());                                                         
        holder.status.setText(model.getStatusVerifikasi());
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try {
            if (list.size() == 0) {
                arr = 0;
            } else {
                arr = list.size();
            }
        } catch (Exception e) {

        }
        return arr;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView kode, submit, poin, jenis, harga, berat, status;

        public MyHolder(View itemView) {
            super(itemView);
            kode = itemView.findViewById(R.id.verifikasi_text_kode);
            submit = itemView.findViewById(R.id.verifikasi_text_submit);
            poin = itemView.findViewById(R.id.verifikasi_text_poin);
            jenis = itemView.findViewById(R.id.verifikasi_text_jenis);
            harga = itemView.findViewById(R.id.verifikasi_text_harga);
            berat = itemView.findViewById(R.id.verifikasi_text_berat);
            status = itemView.findViewById(R.id.verifikasi_text_status);
        }
    }
}
