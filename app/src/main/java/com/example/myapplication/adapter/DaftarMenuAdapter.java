package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DaftarMenu;

import java.util.List;


public class DaftarMenuAdapter extends BaseAdapter {

    private Context konteks;
    private List<DaftarMenu> daftarMenu;

    public DaftarMenuAdapter(Context konteks, List<DaftarMenu> list) {
        this.konteks = konteks;
        this.daftarMenu = list;
    }

    @Override
    public int getCount() {
        return daftarMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return daftarMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DaftarMenu m = daftarMenu.get(position);
        View v = View.inflate(konteks, R.layout.list_item_lama, null);
        ImageView img = v.findViewById(R.id.img_menu);
        img.setImageResource(m.getIDFoto());
        TextView t = v.findViewById(R.id.title_menu);
        t.setText(m.getNama());
        return v;
    }
}
