package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.bs_daftarPerusahaan_model;

import java.util.List;


public class bs_daftarPerusahaan_adapter extends RecyclerView.Adapter<bs_daftarPerusahaan_adapter.MyViewHolder> {
    private Context context;
    private List<bs_daftarPerusahaan_model> modelList;

    public bs_daftarPerusahaan_adapter(Context context, List<bs_daftarPerusahaan_model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_bank_daftar_perusahaan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bs_daftarPerusahaan_model model = modelList.get(position);
        holder.instansi.setText(model.getNamaInstansi());
        holder.pemilik.setText(model.getNamaPemilik());
        holder.alamat.setText(model.getAlamat());
        holder.deskripsi.setText(model.getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView instansi, pemilik, alamat, deskripsi;

        public MyViewHolder(View itemView) {
            super(itemView);
            instansi = itemView.findViewById(R.id.bank_perusahaan_instansi);
            pemilik = itemView.findViewById(R.id.bank_perusahaan_pemilik);
            alamat = itemView.findViewById(R.id.bank_perusahaan_alamat);
            deskripsi = itemView.findViewById(R.id.bank_perusahaan_deskripsi);
        }
    }
}
