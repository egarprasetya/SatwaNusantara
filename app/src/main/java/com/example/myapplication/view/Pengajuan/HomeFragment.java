package com.example.myapplication.view.Pengajuan;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.view.Pengajuan.leaderboard.Volunteer_leaderboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    //    ListView listMenu;
//    OnMenuClickListener mCallback;
    CardView mSampahku, mMemasukan, mBankSampah,mLeaderboard;

    public HomeFragment() {
        // Required empty public constructor
    }

//    public interface OnMenuClickListener {
//        void onMenuSelected(int position);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        try {
//            mCallback = (OnMenuClickListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " Must Implement Listener");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((HomePengajuan) getActivity()).setActionBarTitle("Menu");
        View rootView = inflater.inflate(R.layout.fragment_volunteer_home, container, false);
        final FragmentTransaction ft = getFragmentManager().beginTransaction();

        mSampahku = rootView.findViewById(R.id.cv_sampahku);
        mMemasukan = rootView.findViewById(R.id.cv_memasukan);
        mBankSampah = rootView.findViewById(R.id.cv_bank_sampah);
        mLeaderboard=rootView.findViewById(R.id.cv_leaderboard);

        mSampahku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_volunteer, new LihatPengajuan()).commit();
            }
        });
        mMemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_volunteer, new MemasukanPengajuan()).commit();
            }
        });
        mBankSampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_volunteer, new LihatDaftarPakan()).commit();
            }
        });

        mLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft.replace(R.id.container_volunteer, new Volunteer_leaderboard()).commit();
            }
        });

//        listMenu = rootView.findViewById(R.id.daftar_utama_volunteer);
//        List<DaftarMenu> menu = new ArrayList<>();
//        menu.add(new DaftarMenu(0, "Sampahku", R.drawable.ic_delete_black_24dp));
//        menu.add(new DaftarMenu(1, "Info Poin", R.drawable.ic_attach_money_black_24dp));
//        menu.add(new DaftarMenu(2, "Bank ModelSampah", R.drawable.ic_history_black_24dp));
//
//        DaftarMenuAdapter adapter = new DaftarMenuAdapter(getActivity(), menu);
//        listMenu.setAdapter(adapter);
//
//        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mCallback.onMenuSelected(position);
//            }
//        });

        return rootView;
    }

}
