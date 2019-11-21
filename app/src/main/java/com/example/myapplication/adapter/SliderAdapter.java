package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;

public class SliderAdapter extends PagerAdapter {

    public int[] slide_image = {
            R.drawable.ic_langkah_1_scan,
            R.drawable.ic_langkah_2_kumpul_poin,
            R.drawable.ic_langkah_3_makanan
    };
    public int[] bg_resource = {
            R.drawable.bg_langkah_1,
            R.drawable.bg_langkah_2,
            R.drawable.bg_langkah_3
    };
    public String[] slide_heading = {
            "Foto Sampah",
            "Kumpulkan Poin",
            "Tukar Poin Dengan Makanan"
    };
    public String[] slide_deskripsi = {
            "Kumpulkan sampah Anda dengan cara memindai sampah menggunakan fitur yang telah tersedia untuk mendapatkan poin",
            "Kumpulkan poin Anda dengan cara menukarkan sampah yang sudah terkumpul ke bank sampah",
            "Tukarkan poin Anda ke mitra UMKM untuk mendapatkan makanan"
    };
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImg = view.findViewById(R.id.silde_img);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDeskripsi = view.findViewById(R.id.slide_deskripsi);
        ImageView bg = view.findViewById(R.id.bg_awal);

        slideImg.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
        slideDeskripsi.setText(slide_deskripsi[position]);
        bg.setImageResource(bg_resource[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
