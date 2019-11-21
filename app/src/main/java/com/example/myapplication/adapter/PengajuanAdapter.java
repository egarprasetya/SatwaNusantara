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
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelVoucher;

import java.util.List;


public class PengajuanAdapter extends RecyclerView.Adapter<PengajuanAdapter.MyViewHolder> {
    private Context context;
    //    private List<ModelSampah> sampahList;
    private List<ModelPengajuan> pengajuanList;

    public interface OnItemClickListener {
        void onItemClick(ModelPengajuan model);
    }
    private OnItemClickListener listenerDetailCok;

    public PengajuanAdapter(Context context, List<ModelPengajuan> listVoucher, OnItemClickListener listener) {
        this.listenerDetailCok = listener;
        this.pengajuanList = listVoucher;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengajuan_lihat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(pengajuanList.get(position), listenerDetailCok);
//        ModelPengajuan model = pengajuanList.get(position);
    }

    @Override
    public int getItemCount() {
        return pengajuanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaPeternak;
        public TextView alamat;
        public TextView tanggalPengajuan;
        public TextView noHp;
        public TextView jumlahSapi;
        public ImageView foto;
        private TextView detailCok;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaPeternak = itemView.findViewById(R.id.volunteer_Nama);
            alamat = itemView.findViewById(R.id.volunteer_alamat);
            tanggalPengajuan = itemView.findViewById(R.id.volunteer_tanggal);
            noHp = itemView.findViewById(R.id.volunteer_hp);
            jumlahSapi = itemView.findViewById(R.id.volunteer_jml_sapi);
            //nganter ibunku sek

            foto = itemView.findViewById(R.id.volunteer_gambar_sampah);

            detailCok = itemView.findViewById(R.id.detail_cok);
        }

        public void bind(final ModelPengajuan model, final OnItemClickListener listener) {
            namaPeternak.setText(model.getNamaPeternak());
            alamat.setText(model.getAlamat());
            tanggalPengajuan.setText(model.getTanggalPengajuan());
            noHp.setText(model.getNoHp());
            jumlahSapi.setText(model.getJumlahSapi());
            Glide.with(context).load(model.getUrlFoto()).into(foto);

            detailCok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listenerDetailCok.onItemClick(model);
//                }
//            });
        }
    }

}
