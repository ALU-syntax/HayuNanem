package com.Kelompok1.android.hayunanem.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Kelompok1.android.hayunanem.Model.Tanaman;
import com.Kelompok1.android.hayunanem.R;
import com.Kelompok1.android.hayunanem.UpdateTanamanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TanamanAdapter extends RecyclerView.Adapter<TanamanAdapter.TanamanViewHolder> {

    private List<Tanaman> mList;
    private Activity context;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    public TanamanAdapter(List<Tanaman> mList, Activity context) {
        this.mList = mList;
        this.context = context;
    }

    //method untuk membuat inisialisasi pertama seperti view & database
    @NonNull
    @Override
    public TanamanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_penanaman, parent, false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new TanamanViewHolder(v) ;
    }

    //method binding data setiap widget yang sudah dideklarasikan
    @Override
    public void onBindViewHolder(@NonNull TanamanViewHolder holder, int position) {
        Tanaman tanaman = mList.get(position);
        String tnmnId = tanaman.TanamanId;
        String currentId = auth.getCurrentUser().getUid();
        holder.setNamaTanaman(tanaman.getTempat());
        holder.setJenisTanaman(tanaman.getJenis());

        //edit
        long milliseconds = tanaman.getTime().getTime();
        String date = DateFormat.format("MM/dd/yyyy", new Date(milliseconds)).toString();
        holder.setTglTanam(date);

        //delete Button
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Delete")
                        .setMessage("Apakah kamu yakin ingin menghapus tanaman ini?")
                        .setNegativeButton("Tidak", null)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firestore.collection("Tanaman")
                                        .document(tnmnId).delete();
                                mList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                alert.show();
            }
        });

        //edit button
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tanaman tanaman1 = mList.get(position);
                Intent updateIntent = new Intent(context, UpdateTanamanActivity.class);
                updateIntent.putExtra("TanamanId", tanaman1);
                context.startActivity(updateIntent);
            }
        });

    }

    //untuk menampilkan semua isi database
    @Override
    public int getItemCount() {
        return mList.size();
    }

    //kelas view holder untuk inisialisasi widget yang ditampilkan
    public class TanamanViewHolder extends RecyclerView.ViewHolder{
        TextView txtTempat, txtTglTanam,  txtJenisTanaman;
        Button btnEdit, btnDelete;
        View mView;

        public TanamanViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            btnEdit = mView.findViewById(R.id.btn_edit_tanaman);
            btnDelete = mView.findViewById(R.id.btn_delete_tanaman);

        }

        public void setNamaTanaman(String namaTanaman){
            txtTempat = mView.findViewById(R.id.nama_tanaman_penanaman);
            txtTempat.setText(namaTanaman);
        }
        public void setJenisTanaman(String jenisTanaman){
            txtJenisTanaman = mView.findViewById(R.id.txt_jenis_tanaman_penanaman);
            txtJenisTanaman.setText(jenisTanaman);
        }
        public void setTglTanam(String tglTanam){
            txtTglTanam = mView.findViewById(R.id.tgl_awal_tanam_penanaman);
            txtTglTanam.setText(tglTanam);
        }
    }

}
