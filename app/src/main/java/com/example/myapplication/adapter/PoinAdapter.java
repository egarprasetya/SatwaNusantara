package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelPoin;

import java.util.List;


public class PoinAdapter extends RecyclerView.Adapter<PoinAdapter.MyViewHolder> {
    private Context context;
    private List<ModelPoin> poinList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_poin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelPoin model = poinList.get(position);
        holder.sumber.setText(model.getSumber());
        holder.dari.setText(model.getDari());
        holder.poin.setText(model.getPoin());
    }

    @Override
    public int getItemCount() {
        return poinList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView poin;
        public TextView dari;
        public TextView sumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            poin = itemView.findViewById(R.id.volunteer_jumlahPoin_poin);
            dari = itemView.findViewById(R.id.volunteer_dari_poin);
            sumber = itemView.findViewById(R.id.volunteer_namaSumber_poin);
        }
    }

    public PoinAdapter(Context context, List<ModelPoin> poinList) {
        this.context = context;
        this.poinList = poinList;
    }
}
