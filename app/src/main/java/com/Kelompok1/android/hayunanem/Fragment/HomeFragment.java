package com.Kelompok1.android.hayunanem.Fragment;

import android.content.Intent;
import android.net.wifi.ScanResult;
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
import com.Kelompok1.android.hayunanem.RiwayatPanenActivity;
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

    public HomeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnAccount = view.findViewById(R.id.btn_Account);
        btnInformation = view.findViewById(R.id.btn_information);
        btnRiwayatPanen = view.findViewById(R.id.btn_riwayat_panen);
        btnTambahTanaman = view.findViewById(R.id.btn_tambah_tanaman);
        btnManagemenPenanaman = view.findViewById(R.id.btn_managemen);
        btnPanduanPenanaman = view.findViewById(R.id.btn_panduan);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Berhasil Logout !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ApplicationInformationActivity.class));
            }
        });

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

        btnRiwayatPanen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RiwayatPanenActivity.class));
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
