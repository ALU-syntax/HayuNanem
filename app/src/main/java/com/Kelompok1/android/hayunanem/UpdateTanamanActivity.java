package com.Kelompok1.android.hayunanem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.Kelompok1.android.hayunanem.Model.Tanaman;
import com.Kelompok1.android.hayunanem.Model.TanamanId;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UpdateTanamanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edNamaTanaman;
    private Spinner spJenisTanaman;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Button btnSubmit;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String currentUserId;

    private ArrayList<Tanaman> arrayList;
    private Tanaman tanaman;
    private TanamanId tanamanId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tanaman);
        tanaman = (Tanaman) getIntent().getSerializableExtra("TanamanId");

        tanamanId = tanaman;

        edNamaTanaman = findViewById(R.id.ed_nama_tanaman_update);
        spJenisTanaman = findViewById(R.id.sp_jenis_tanaman_update);
        btnSubmit = findViewById(R.id.btn_submit_update);



        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        spJenisTanaman.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
//        categories.add(0, "Masukan Jenis Tanaman");
        categories.add(0, "Kangkung");
        categories.add(1, "Sawi");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spJenisTanaman.setAdapter(dataAdapter);

        arrayList = new ArrayList<>();


        edNamaTanaman.setText(tanaman.getTempat().toString());
        if (tanaman.getJenis().equals("Kangkung")) {
            spJenisTanaman.setSelection(0);
        }else{
            spJenisTanaman.setSelection(1);
        }



//        firestore.collection("Tanaman").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if(!queryDocumentSnapshots.isEmpty()){
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                    for (DocumentSnapshot d : list){
//                        Tanaman t = d.toObject(Tanaman.class);
//                        t.setTanamanId(d.getId());
//
//                        edNamaTanaman.setText(t.getTempat().toString());
//                        edTanggalTanam.setText(t.getWaktuTanam().toString());
//                        String jenisTanaman = t.getJenis();
//                        if (jenisTanaman.equals("Kangkung")){
//                            spJenisTanaman.setSelection(0);
//                        }else{
//                            spJenisTanaman.setSelection(1);
//                        }
//                    }
//                }
//
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaTanaman = edNamaTanaman.getText().toString();
                String jenisTanaman = String.valueOf(spJenisTanaman.getSelectedItem());
                Date waktu = tanaman.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date tglTanam = null;

                if (!namaTanaman.isEmpty() && !jenisTanaman.isEmpty()){
                    updateTanaman(tanaman, namaTanaman, jenisTanaman, currentUserId, waktu);
                }else{
                    Toast.makeText(UpdateTanamanActivity.this, "Tidak boleh ada kolom yang kosong!",
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

    private void updateTanaman(Tanaman tanaman1, String namaTmptTanaman, String jenisTanaman,
                               String user, Date time){

        Tanaman updateTanaman = new Tanaman(namaTmptTanaman, jenisTanaman, user, time);
        firestore.collection("Tanaman").document(tanaman.getTanamanId()).set(updateTanaman).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateTanamanActivity.this, "Update Berhasil!!!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateTanamanActivity.this, MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTanamanActivity.this, "Update Gagal!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}