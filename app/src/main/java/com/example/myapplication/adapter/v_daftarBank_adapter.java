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
import com.example.myapplication.model.ModelDataSapi;
import com.example.myapplication.model.ModelDataSapi;

import java.util.List;


public class v_daftarBank_adapter extends RecyclerView.Adapter<v_daftarBank_adapter.MyViewHolder> {
    private Context context;
    private List<ModelDataSapi> modelList;

    public v_daftarBank_adapter(Context context, List<ModelDataSapi> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_bank, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelDataSapi model = modelList.get(position);
        if(model.getJenisKelamin()=="jantan") {
            holder.nomor.setText(model.getNomorSapi());
            holder.berat.setText(model.getBeratAwal());
            holder.jenis.setText(model.getJenisSapi());
        }
//        holder.telp.setText(model.getJenisSapi());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nomor, berat, jenis, telp;
        private ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.volunteer_nomor_pemilik);
            berat = itemView.findViewById(R.id.volunteer_berat_awal);
            jenis = itemView.findViewById(R.id.volunteer_jenis);
//            gambar = (ImageView) itemView.findViewById(R.id.volunteer_bank_img);
//            telp = itemView.findViewById(R.id.volunteer_bank_telp);
        }
    }
}
