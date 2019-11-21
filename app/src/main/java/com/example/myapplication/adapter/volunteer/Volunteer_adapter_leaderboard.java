package com.example.myapplication.adapter.volunteer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Model_leaderboard;

import java.util.List;


public class Volunteer_adapter_leaderboard extends RecyclerView.Adapter<Volunteer_adapter_leaderboard.MyViewHolder> {
    private Context context;
    private List<Model_leaderboard> model;

    public Volunteer_adapter_leaderboard(Context context, List<Model_leaderboard> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunteer_list_leaderboard, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder mv, int i) {
        Model_leaderboard m = model.get(i);
        i++;
        mv.no.setText(i + ".");
        mv.nama.setText(m.getNama());
        mv.poin.setText(m.getPoin() + " Poin");
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView no, nama, poin;

        public MyViewHolder(View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.volunteer_list_leaderboard_no_urut);
            nama = itemView.findViewById(R.id.volunteer_list_leaderboard_nama);
            poin = itemView.findViewById(R.id.volunteer_list_leaderboard_poin);
        }
    }
}
