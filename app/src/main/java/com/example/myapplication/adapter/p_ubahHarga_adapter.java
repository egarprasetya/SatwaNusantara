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

import java.util.List;


public class p_ubahHarga_adapter extends RecyclerView.Adapter<p_ubahHarga_adapter.MyViewHolder> {
    private Context context;
    private List<ModelDataSapi> modelList;

    public p_ubahHarga_adapter(Context context, List<ModelDataSapi> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sapi_jantan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelDataSapi model = modelList.get(position);
        System.out.println(model.getJenisKelamin()+"cobalalallalalalala"+position);
//        if(model.getJenisKelamin().equalsIgnoreCase("jantan")) {
            System.out.println(model.getJenisKelamin()+"asdsadasasdd");
            holder.berat.setText(model.getBeratAwal());
            holder.jenis.setText(model.getJenisSapi());
//        holder.nama.setText(model.getNamaPeternak());
            holder.nomor.setText(model.getNomorSapi());
//        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView berat, jenis, nama, nomor;
        ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.sapi_jantan_nomor);
            jenis = itemView.findViewById(R.id.sapi_jantan_jenis);
//            telp = itemView.findViewById(R.id.perusahaan_bank_telp);
            berat = itemView.findViewById(R.id.sapi_jantan_berat);

//            gambar = (ImageView) itemView.findViewById(R.id.perusahaan_bank_gambar);
        }
    }
}
