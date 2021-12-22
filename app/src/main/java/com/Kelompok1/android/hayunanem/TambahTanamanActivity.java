package com.Kelompok1.android.hayunanem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TambahTanamanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edNamaTanaman;
    private Spinner spJenisTanaman;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btnSubmit;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String currentUserId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_tanaman);

        edNamaTanaman = findViewById(R.id.ed_nama_tanaman);
        spJenisTanaman = findViewById(R.id.sp_jenis_tanaman);
        btnSubmit = findViewById(R.id.btn_submit);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        spJenisTanaman.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
//        categories.add(0, "Masukan Jenis Tanaman");
        categories.add("Kangkung");
        categories.add("Sawi");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spJenisTanaman.setAdapter(dataAdapter);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaTanaman = edNamaTanaman.getText().toString();
                String jenisTanaman = String.valueOf(spJenisTanaman.getSelectedItem());
                if (!namaTanaman.isEmpty() &&!jenisTanaman.isEmpty()){
                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("tempat", namaTanaman);
                    postMap.put("jenis", jenisTanaman);
                    postMap.put("user", currentUserId);
                    postMap.put("time", FieldValue.serverTimestamp());

                    firestore.collection("Tanaman").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(TambahTanamanActivity.this,
                                    "Tanaman Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(TambahTanamanActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                }else{
                    Toast.makeText(TambahTanamanActivity.this, "Tidak boleh ada kolom yang kosong!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
