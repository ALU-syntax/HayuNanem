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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TambahTanamanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edNamaTanaman, edTanggalTanam;
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
        edTanggalTanam = findViewById(R.id.ed_tanggal_tanam);
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


        edTanggalTanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaTanaman = edNamaTanaman.getText().toString();
                String tanggalTanam = edTanggalTanam.getText().toString();
                String jenisTanaman = String.valueOf(spJenisTanaman.getSelectedItem());
                if (!namaTanaman.isEmpty() && !tanggalTanam.isEmpty() && !jenisTanaman.isEmpty()){
                    HashMap<String, Object> postMap = new HashMap<>();
                    postMap.put("Nama Tanaman", namaTanaman);
                    postMap.put("Tanggal Tanam", tanggalTanam);
                    postMap.put("Jenis Tanaman", jenisTanaman);
                    postMap.put("User", currentUserId);

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

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                edTanggalTanam.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
