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
import com.example.myapplication.model.ModelVoucherVolunteer;

import java.util.List;


public class RiwayatAdapterMitra extends RecyclerView.Adapter<RiwayatAdapterMitra.ViewHolder> {

    private Context context;
    private List<ModelVoucherVolunteer> list;

    public RiwayatAdapterMitra(Context context, List<ModelVoucherVolunteer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mitra_riwayat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelVoucherVolunteer model = list.get(position);
        holder.judul.setText(model.getNamaVoucher());
        holder.namaVolunteer.setText(model.getNamaVolunteer());
        holder.harga.setText(model.getHargaPoin());
        holder.tanggal.setText(model.getTanggal());
        holder.status.setText(model.getStatusVoucher());
        holder.kode.setText(model.getKodeVoucher());
        Glide.with(context).load(model.getUrl_foto()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView judul;
        public TextView harga;
        public TextView tanggal;
        public TextView kode;
        public TextView status;
        private TextView namaVolunteer;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mitra_gambar_riwayat);
            judul = itemView.findViewById(R.id.nama_voucher);
            namaVolunteer = itemView.findViewById(R.id.teks_nama_volunteer);
            harga = itemView.findViewById(R.id.teks_poin);
            tanggal = itemView.findViewById(R.id.mitra_tanggal_tukar);
            status = itemView.findViewById(R.id.status_voucher);
            kode = itemView.findViewById(R.id.mitra_kode);
        }
    }
}
