package com.Kelompok1.android.hayunanem;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    private EditText edNama, edEmail, edNumberPhone, edAddress;
    private Button btnSubmit, btnCancel;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String UID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        edNama = findViewById(R.id.ed_nama_profile);
        edEmail = findViewById(R.id.ed_email);
        edNumberPhone = findViewById(R.id.ed_number_phone);
        edAddress = findViewById(R.id.ed_address);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSubmit = findViewById(R.id.btn_change_password);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        UID = auth.getCurrentUser().getUid();

        firestore.collection("Users").document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        String name = task.getResult().getString("Nama");
                        String address = task.getResult().getString("Alamat");
                        String noPhone = task.getResult().getString("Nomor Telfon");

                        edNama.setText(name);
                        edNumberPhone.setText(noPhone);
                        edAddress.setText(address);

                    }
                }
            }
        });
    }
}
