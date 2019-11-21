package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelDaftarPakan;

import java.util.List;


public class DaftarPakanAdapter extends RecyclerView.Adapter<DaftarPakanAdapter.MyViewHolder> {
    private Context context;
    //    private List<ModelSampah> sampahList;
    private List<ModelDaftarPakan> stokList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pengajuan_daftar_pakan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ModelDaftarPakan model = stokList.get(position);
        holder.konsentrat.setText(model.getKonsentrat());
        holder.jagung.setText(model.getJagung());
        holder.jerami.setText(model.getJerami());

//        Log.w("foto", model.getUrlFoto());
    }

    @Override
    public int getItemCount() {
        return stokList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView konsentrat;
        public TextView jagung;
        public TextView jerami;

        public MyViewHolder(View itemView) {
            super(itemView);
            konsentrat = itemView.findViewById(R.id.daftar_pakan_stok_konsen);
            jagung = itemView.findViewById(R.id.daftar_pakan_stok_jagung);
            jerami = itemView.findViewById(R.id.daftar_pakan_stok_jerami);

        }
    }

    public DaftarPakanAdapter(Context context, List<ModelDaftarPakan> stokList) {
        this.context = context;
        this.stokList = stokList;
    }
}
