package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.myapplication.util.Util.MB;


public class EditPengajuanAdapter extends RecyclerView.Adapter<EditPengajuanAdapter.MyViewHolder> {

    private Context context;
    private ModelPengajuan editModel;
    private List<ModelPengajuan> pengajuanList;
    public interface OnItemClickListener {
        void onItemClick(ModelPengajuan model);
    }

    private List<ModelPengajuan> listPengajuan;
    private OnItemClickListener listener;

    public EditPengajuanAdapter(List<ModelPengajuan> listPengajuan, OnItemClickListener listener) {
        this.listener = listener;
        this.listPengajuan = listPengajuan;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_voucher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bind(listPengajuan.get(position), listener);
        ModelPengajuan model = pengajuanList.get(position);
        holder.namaPeternak.setText(editModel.getNamaPeternak());
        holder.alamat.setText(editModel.getAlamat());
        holder.tanggalPengajuan.setText(editModel.getTanggalPengajuan());
        holder.noHp.setText(editModel.getNoHp());
        holder.jumlahSapi.setText(editModel.getJumlahSapi());
        Glide.with(context).load(editModel.getUrlFoto()).into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return listPengajuan.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView namaPeternak;
        public TextView alamat;
        public TextView tanggalPengajuan;
        public TextView noHp;
        public TextView jumlahSapi;
        public ImageView foto;



        public MyViewHolder(View itemView) {
            super(itemView);
            namaPeternak = itemView.findViewById(R.id.volunteer_Nama);
            alamat = itemView.findViewById(R.id.volunteer_alamat);
            tanggalPengajuan = itemView.findViewById(R.id.volunteer_tanggal);
            noHp = itemView.findViewById(R.id.volunteer_hp);
            jumlahSapi = itemView.findViewById(R.id.volunteer_jml_sapi);
            foto=itemView.findViewById(R.id.volunteer_gambar_sampah);

        }

        public void bind(final ModelPengajuan model, final OnItemClickListener listener) {
            namaPeternak.setText(model.getNamaPeternak());
            alamat.setText(model.getAlamat());
            tanggalPengajuan.setText(model.getNoHp());
            noHp.setText(model.getTanggalPengajuan());

            StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(model.getUrlFoto());
            ref.getBytes(MB).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        Log.d("InfoAdap", "download foto");
                        Bitmap fotoDownload = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                        foto.setImageBitmap(fotoDownload);
                        foto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        Log.w(VoucherAdapter.class.getSimpleName(), "gagal download foto:" + task.getException());
                        foto.setImageResource(R.drawable.download_failed);
                        foto.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }
    public EditPengajuanAdapter(Context context, List<ModelPengajuan> pengajuanList, ModelPengajuan editModel) {
        this.context = context;
        this.pengajuanList = pengajuanList;
        this.editModel=editModel;
    }

}
