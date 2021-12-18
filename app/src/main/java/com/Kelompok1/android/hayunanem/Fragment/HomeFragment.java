package com.Kelompok1.android.hayunanem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.Kelompok1.android.hayunanem.ManagemenPenanamanActivity;
import com.Kelompok1.android.hayunanem.PanduanActivity;
import com.Kelompok1.android.hayunanem.R;
import com.Kelompok1.android.hayunanem.TambahTanamanActivity;

public class HomeFragment extends Fragment {

    private View view;
    private AppCompatButton btnRiwayatPanen, btnTambahTanaman, btnManagemenPenanaman, btnPanduanPenanaman;
    private TextView txtName, txtHayu;
    private Button btnNavdraw;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRiwayatPanen = view.findViewById(R.id.btn_riwayat_panen);
        btnTambahTanaman = view.findViewById(R.id.btn_tambah_tanaman);
        btnManagemenPenanaman = view.findViewById(R.id.btn_managemen);
        btnPanduanPenanaman = view.findViewById(R.id.btn_panduan);

        btnTambahTanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TambahTanamanActivity.class));
            }
        });

        btnManagemenPenanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManagemenPenanamanActivity.class));
            }
        });

        btnPanduanPenanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PanduanActivity.class));
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}
