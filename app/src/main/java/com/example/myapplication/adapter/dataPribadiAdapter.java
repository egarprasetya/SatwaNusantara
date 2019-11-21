package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.model.ModelDataSapi;

import java.util.List;


public class dataPribadiAdapter extends RecyclerView.Adapter<dataPribadiAdapter.MyViewHolder> {
    private Context context;
    private List<ModelPengajuan> modelList;

    public dataPribadiAdapter(Context context, List<ModelPengajuan> modelList) {
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
        ModelPengajuan model = modelList.get(position);
//        System.out.println(model.getJenisKelamin()+"cobalalallalalalala"+position);
//        if(model.getJenisKelamin().equalsIgnoreCase("jantan")) {
//        System.out.println(model.getJenisKelamin()+"asdsadasasdd");
        System.out.println("cekkkkkkkkkkkkkkk"+model.getUsername());
        holder.namaPeternak.setText(model.getNamaPeternak());
        holder.username.setText(model.getUsername());
        holder.alamat.setText(model.getAlamat());
        holder.noKtp.setText(model.getNoKtp());
        holder.jumlahSapi.setText(model.getJumlahSapi());
        holder.noHp.setText(model.getNoHp());
//        holder.berat.setText(model.get());
//        holder.jenis.setText(model.getJenisSapi());
////        holder.nama.setText(model.getNamaPeternak());
//        holder.nomor.setText(model.getNomorSapi());
//        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText namaPeternak, username, alamat, noKtp, jumlahSapi, noHp;
        TextView berat, jenis, nama, nomor;
        ImageView gambar;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaPeternak = itemView.findViewById(R.id.input_pengajuan_namaPeternak);
            username = itemView.findViewById(R.id.input_pengajuan_username);
            alamat = itemView.findViewById(R.id.input_pengajuan_alamat);
            noKtp = itemView.findViewById(R.id.input_pengajuan_noKtp);
            jumlahSapi = itemView.findViewById(R.id.input_pengajuan_jumlahSapi);
            noHp = itemView.findViewById(R.id.input_pengajuan_noHp);
//            nomor = itemView.findViewById(R.id.input_pengajuan_namaPeternak);
//            jenis = itemView.findViewById(R.id.input);
////            telp = itemView.findViewById(R.id.perusahaan_bank_telp);
//            berat = itemView.findViewById(R.id.sapi_jantan_berat);

//            gambar = (ImageView) itemView.findViewById(R.id.perusahaan_bank_gambar);
        }
    }
}
