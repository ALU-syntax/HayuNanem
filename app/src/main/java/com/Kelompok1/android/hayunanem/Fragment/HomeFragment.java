package com.Kelompok1.android.hayunanem.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.Kelompok1.android.hayunanem.AccountActivity;
import com.Kelompok1.android.hayunanem.ApplicationInformationActivity;
import com.Kelompok1.android.hayunanem.LoginActivity;
import com.Kelompok1.android.hayunanem.ManagemenPenanamanActivity;
import com.Kelompok1.android.hayunanem.PanduanActivity;
import com.Kelompok1.android.hayunanem.R;
import com.Kelompok1.android.hayunanem.SetUpActivity;
import com.Kelompok1.android.hayunanem.TambahTanamanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private View view;
    private AppCompatButton btnRiwayatPanen, btnTambahTanaman, btnManagemenPenanaman,
    btnPanduanPenanaman, btnAccount, btnInformation, btnLogout;
    private TextView txtName, txtHayu;
    private Button btnNavdraw;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String Uid;

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Uid = mAuth.getCurrentUser().getUid();

        //method untuk memanggil data yang ada dicollection Users
        firestore.collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        String name = task.getResult().getString("Nama");

                        txtName.setText("Halo! " + name );
                        txtName.setTextColor(Color.parseColor("#FFFFFF"));

                    }
                }
            }
        });

    }

    //method inisialisasi untuk didalam view
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName = view.findViewById(R.id.txt_nama);
        btnLogout = view.findViewById(R.id.btn_logout);
        btnAccount = view.findViewById(R.id.btn_Account);
        btnInformation = view.findViewById(R.id.btn_information);
        btnRiwayatPanen = view.findViewById(R.id.btn_riwayat_panen);
        btnTambahTanaman = view.findViewById(R.id.btn_tambah_tanaman);
        btnManagemenPenanaman = view.findViewById(R.id.btn_managemen);
        btnPanduanPenanaman = view.findViewById(R.id.btn_panduan);

        //method click button logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Berhasil Logout !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        //method click button Account
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        //method click button informasi
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ApplicationInformationActivity.class));
            }
        });

        //method click button tambah tanaman
        btnTambahTanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TambahTanamanActivity.class));
            }
        });

        //method click button management penanaman
        btnManagemenPenanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ManagemenPenanamanActivity.class));
            }
        });

        //method click button panduan penanaman
        btnPanduanPenanaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PanduanActivity.class));
            }
        });

        //method click button riwayat panen
        btnRiwayatPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RiwayatPanenActivity.class));
            }
        });
    }

    //method create inisialisasi layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    //method yang akan dijalankan ketika activity baru dimulai
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            String currentUserId = mAuth.getCurrentUser().getUid();
            firestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (!task.getResult().exists()) {
                            startActivity(new Intent(getActivity(), SetUpActivity.class));
                            getActivity().finish();
                        }
                    }
                }
            });

        }
    }
}
