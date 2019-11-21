package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ModelVoucher;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.MyViewHolder> {
    private List<ModelVoucher> list;
    private Context context;

    public VoucherAdapter(Context context, List<ModelVoucher> list) {
        this.context=context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelVoucher m=list.get(position);
        holder.harga.setText(m.getHargaPoin());
        holder.judul.setText(m.getNamaVoucher());
        holder.kuota.setText(m.getJumlahKuota());
        Glide.with(context).load(m.getUrl_foto()).into(holder.img);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mitra_daftar_voucher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView kuota;
        public TextView judul;
        public TextView harga;


        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_voucher);
            kuota = itemView.findViewById(R.id.kuota_voucher);
            judul = itemView.findViewById(R.id.judul_voucher);
            harga = itemView.findViewById(R.id.harga_voucher);
        }

    }
}
