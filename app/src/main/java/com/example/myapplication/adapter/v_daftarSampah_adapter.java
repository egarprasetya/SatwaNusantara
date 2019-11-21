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
import com.example.myapplication.model.ModelSampah;

import java.util.List;


public class v_daftarSampah_adapter extends RecyclerView.Adapter<v_daftarSampah_adapter.MyViewHolder> {

    private Context context;
    private List<ModelSampah> modelList;

    public v_daftarSampah_adapter(Context context, List<ModelSampah> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_volunteer_daftar_sampah, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelSampah model = modelList.get(position);
        holder.tanggal.setText(model.getTanggalSubmit());
        holder.waktu.setText(model.getTanggalAkhir());
        holder.status.setText(model.getStatusVerifikasi());

        Glide.with(context).load(model.getUrlFoto()).into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggal, waktu, status;
        public ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.volunteer_tanggal_daftarSampah);
            waktu = itemView.findViewById(R.id.volunteer_waktu_daftarSampah);
            status = itemView.findViewById(R.id.volunteer_status_daftarSampah);
            gambar = itemView.findViewById(R.id.volunteer_gambar_daftarSampah);
        }
    }
}