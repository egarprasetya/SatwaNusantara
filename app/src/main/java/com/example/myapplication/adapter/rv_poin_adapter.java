package com.example.myapplication.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.cv_poin_model;

import java.util.ArrayList;


public class rv_poin_adapter extends RecyclerView.Adapter<rv_poin_adapter.list_poin> {

    private ArrayList<cv_poin_model> dataList;

    public rv_poin_adapter(ArrayList<cv_poin_model> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public list_poin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cv_menu_point, parent, false);
        return new list_poin(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_poin holder, int position) {
        holder.txtNo.setText(dataList.get(position).getNo());
        holder.txtTanggal.setText(dataList.get(position).getTanggal());
        holder.txtPoin.setText(dataList.get(position).getPoin());
        holder.txtSampah.setText(dataList.get(position).getSampah());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class list_poin extends RecyclerView.ViewHolder {
        private TextView txtNo, txtTanggal, txtPoin, txtSampah;

        public list_poin(View itemView) {
            super(itemView);
            txtNo = itemView.findViewById(R.id.text_poinNo);
            txtTanggal = itemView.findViewById(R.id.text_poinTanggal);
            txtPoin = itemView.findViewById(R.id.text_poinPoin);
            txtSampah = itemView.findViewById(R.id.text_poinSampah);
        }
    }
}
