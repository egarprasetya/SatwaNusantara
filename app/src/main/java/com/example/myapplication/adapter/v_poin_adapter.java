package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.v_poin_model;

import java.util.List;


public class v_poin_adapter extends RecyclerView.Adapter<v_poin_adapter.MyViewHolder> {

    private Context context;
    private List<v_poin_model> modelList;


    public v_poin_adapter(Context context, List<v_poin_model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_volunteer_poin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        v_poin_model model = modelList.get(position);
        holder.tanggal.setText(model.getTanggal());
        holder.poin.setText(model.getPoin());
        holder.berat.setText(model.getBerat());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggal, poin, berat;

        public MyViewHolder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.volunteer_poin_tanggal);
            poin = itemView.findViewById(R.id.volunteer_poin_poin);
            berat = itemView.findViewById(R.id.volunteer_poin_beratSampah);
        }
    }
}
