package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ModelDataSapi;
import com.example.myapplication.model.ModelPengajuan;
import com.example.myapplication.view.Pengajuan.LihatDataSapi;
import com.example.myapplication.view.Pengajuan.LihatPengajuan;
import com.example.myapplication.view.Pengajuan.MemasukkanDataSapi2;
import com.example.myapplication.view.Perusahaan.TabSapiBetina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class PengajuanAdapter extends RecyclerView.Adapter<PengajuanAdapter.MyViewHolder> {
    private Context context;
    //    private List<ModelSampah> sampahList;
    private List<ModelPengajuan> pengajuanList;

    public interface OnItemClickListener {
        void onItemClick(ModelPengajuan model);
    }
    private OnItemClickListener listenerDetail;

    public PengajuanAdapter(Context context, List<ModelPengajuan> listVoucher) {
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
        holder.bind(pengajuanList.get(position), listenerDetail);
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
        private TextView detailData;
         FirebaseDatabase database;
        DatabaseReference reference;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaPeternak = itemView.findViewById(R.id.volunteer_Nama);
            alamat = itemView.findViewById(R.id.volunteer_alamat);
            tanggalPengajuan = itemView.findViewById(R.id.volunteer_tanggal);
            noHp = itemView.findViewById(R.id.volunteer_hp);
            jumlahSapi = itemView.findViewById(R.id.volunteer_jml_sapi);

            foto = itemView.findViewById(R.id.volunteer_gambar_sampah);

            detailData = itemView.findViewById(R.id.detail_cok);
        }

        public void bind(final ModelPengajuan model, final OnItemClickListener listener) {
            namaPeternak.setText(model.getNamaPeternak());
            alamat.setText(model.getAlamat());
            tanggalPengajuan.setText(model.getTanggalPengajuan());
            noHp.setText(model.getNoHp());
            jumlahSapi.setText(model.getJumlahSapi());
            Glide.with(context).load(model.getUrlFoto()).into(foto);
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("dataSapi");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ModelDataSapi model1 = dataSnapshot1.getValue(ModelDataSapi.class);
                        if(model1.getNomorSapi().equalsIgnoreCase(model.getJumlahSapi()) && model1.getNamaPeternak().equalsIgnoreCase(model.getNamaPeternak()))
                        {
                            detailData.setText("Lihat Data Sapi");
                            detailData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent data = new Intent(context.getApplicationContext(), LihatDataSapi.class);
                                    data.putExtra("namaPeternak", model.getNamaPeternak());
                                    data.putExtra("jumlahSapi", model.getJumlahSapi());
                                    data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.getApplicationContext().startActivity(data);
                                }
                            });
                            break;
                        }
                        else
                        {
                            detailData.setText("Tambah Data Sapi");
                            detailData.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent data = new Intent(context.getApplicationContext(), MemasukkanDataSapi2.class);
                                    data.putExtra("namaPeternak", model.getNamaPeternak());
                                    data.putExtra("jumlahSapi", model.getJumlahSapi());
                                    data.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.getApplicationContext().startActivity(data);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

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
