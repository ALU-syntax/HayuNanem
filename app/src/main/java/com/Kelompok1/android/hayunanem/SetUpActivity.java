package com.Kelompok1.android.hayunanem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SetUpActivity extends AppCompatActivity {

    private EditText edName, edNmbrPhone, edAddress;
    private Button btnRegist;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Uid = auth.getCurrentUser().getUid();

        edName = findViewById(R.id.ed_nama);
        edNmbrPhone = findViewById(R.id.ed_number_phone);
        edAddress = findViewById(R.id.ed_address);
        btnRegist = findViewById(R.id.btn_regist);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String address = edAddress.getText().toString();
                String nmbrPhone = edNmbrPhone.getText().toString();

                HashMap<String, Object> map = new HashMap<>();
                map.put("Nama", name);
                map.put("Alamat", address);
                map.put("Nomor Telfon", nmbrPhone);

                firestore.collection("Users").document(Uid).set(map).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SetUpActivity.this, "Profil Berhasil Disimpan!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SetUpActivity.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(SetUpActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}