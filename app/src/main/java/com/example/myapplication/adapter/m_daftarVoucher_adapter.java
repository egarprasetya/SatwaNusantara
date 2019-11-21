package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.m_daftarVoucher_model;

import java.util.List;


public class m_daftarVoucher_adapter extends RecyclerView.Adapter<m_daftarVoucher_adapter.MyViewHolder> {
    private Context context;
    private List<m_daftarVoucher_model> modelList;

    public m_daftarVoucher_adapter(Context context, List<m_daftarVoucher_model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mitra_daftar_voucher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        m_daftarVoucher_model model = modelList.get(position);
        holder.gambar.setImageResource(model.getGambar());
        holder.keterangan.setText(model.getKeterangan());
        holder.jumlah.setText(model.getJumlah());
        holder.poin.setText(model.getPoin());
        holder.namaMitra.setText(model.getNamaMitra());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView namaMitra, poin, jumlah, keterangan;
        private ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaMitra = itemView.findViewById(R.id.mitra_namaMitra_daftarVoucher);
            poin = itemView.findViewById(R.id.mitra_poin_daftarVoucher);
            jumlah = itemView.findViewById(R.id.mitra_jumlah_daftarVoucher);
            keterangan = itemView.findViewById(R.id.mitra_keterangan_daftarVoucher);
            gambar = itemView.findViewById(R.id.mitra_gambar_daftarVoucher);
        }
    }
}
