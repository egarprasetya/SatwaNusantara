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

import com.example.myapplication.R;
import com.example.myapplication.model.ModelVoucherVolunteer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.myapplication.util.Util.MB;


public class RiwayatAdapterVolunteer extends RecyclerView.Adapter<RiwayatAdapterVolunteer.MyViewHolder> {

    private Context context;
    private List<ModelVoucherVolunteer> list;

    public RiwayatAdapterVolunteer(Context context, List<ModelVoucherVolunteer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer_riwayat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView kode;
        public TextView status;
        public TextView namaMitra;
        public TextView harga;
        public TextView judul;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.voulunteer_img_voucher);
            kode = itemView.findViewById(R.id.volunteer_kode_voucher);
            status = itemView.findViewById(R.id.volunteer_status_voucher);
            namaMitra = itemView.findViewById(R.id.volunteer_mitra_voucher);
            harga = itemView.findViewById(R.id.volunteer_harga_voucher);
            judul = itemView.findViewById(R.id.volunteer_judul_voucher);
        }

        public void bind(ModelVoucherVolunteer model) {
            namaMitra.setText(model.getNamaMitra());
            status.setText(model.getStatusVoucher());
            harga.setText(model.getHargaPoin());
            judul.setText(model.getNamaVoucher());
            kode.setText(model.getKodeVoucher());

            StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(model.getUrl_foto());
            ref.getBytes(MB).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        Bitmap fotoDownload = BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length);
                        img.setImageBitmap(fotoDownload);
                        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    } else {
                        Log.w(VoucherAdapter.class.getSimpleName(), "gagal download foto:" + task.getException());
                        img.setImageResource(R.drawable.download_failed);
                        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                }
            });
        }
    }
}
