package com.Kelompok1.android.hayunanem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Kelompok1.android.hayunanem.Adapter.TanamanAdapter;
import com.Kelompok1.android.hayunanem.Model.Tanaman;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ManagemenPenanamanActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private String Uid;
    private RecyclerView recyclerView;
    private List<Tanaman> tanamanList;
    private Query query;
    private Tanaman tanaman;
    private TanamanAdapter tanamanAdapter;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_penanaman);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        Uid = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recyler_tanaman);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManagemenPenanamanActivity.this));

        tanamanList = new ArrayList<>();
        tanamanAdapter = new TanamanAdapter(tanamanList, this);
        recyclerView.setAdapter(tanamanAdapter);

//        loadRecyclerViewData();

        if (auth.getCurrentUser() != null){


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean isBottom = !recyclerView.canScrollVertically(1);
                    if (isBottom){
                        Toast.makeText(ManagemenPenanamanActivity.this, "Reached Bottom", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            query = firestore.collection("Tanaman").orderBy("time", Query.Direction.DESCENDING);
            listenerRegistration = query.addSnapshotListener(ManagemenPenanamanActivity.this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                    for (DocumentChange doc : value.getDocumentChanges()){
                        if(doc.getType() == DocumentChange.Type.ADDED){
                            String tanamanId = doc.getDocument().getId();
                            Tanaman post = doc.getDocument().toObject(Tanaman.class).withId(tanamanId);
                            post.setTanamanId(tanamanId);
                            String postUserId = doc.getDocument().getString("user");
                            firestore.collection("Users").document(postUserId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()){
//                                                Users users = task.getResult().toObject(Users.class);
//                                                usersList.add(users);
                                                tanamanList.add(post);

                                                tanamanAdapter.notifyDataSetChanged();
                                            }else{
                                                Toast.makeText(ManagemenPenanamanActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            tanamanAdapter.notifyDataSetChanged();
                        }
                    }
//                    listenerRegistration.remove();
                }
            });
        }

//        if (Uid.equals(auth.getCurrentUser())){
//            query = firestore.collection("Tanaman").orderBy("time",
//                    Query.Direction.DESCENDING);
//            listenerRegistration = query.addSnapshotListener(ManagemenPenanamanActivity.this, new EventListener<QuerySnapshot>() {
//                @Override
//                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                    for (DocumentChange doc : value.getDocumentChanges()){
//                        if (doc.getType() == DocumentChange.Type.ADDED){
//                            String tanamanId = doc.getDocument().getId();
//                            tanaman = doc.getDocument().toObject(Tanaman.class).withId(tanamanId);
//                            firestore.collection("Tanaman").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()){
//                                        tanamanList.add(tanaman);
//
//                                    }else{
//                                        Toast.makeText(ManagemenPenanamanActivity.this,
//                                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }else{
//                            tanamanAdapter.notifyDataSetChanged();
//                        }
//                        tanamanAdapter.notifyDataSetChanged();
//                    }
//                    listenerRegistration.remove();
//                }
//            });
//        }
    }
//    private void loadRecyclerViewData(){
//        firestore.collection("Tanaman").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()){
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                    for(DocumentSnapshot d : list){
//                        Tanaman tanaman = d.toObject(Tanaman.class);
//                        tanamanList.add(tanaman);
//
//
//                    }
//                    tanamanAdapter.notifyDataSetChanged();
//                }else{
//                    Toast.makeText(ManagemenPenanamanActivity.this,
//                            "Tidak ada data penanaman", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(ManagemenPenanamanActivity.this,
//                        "Fail to get the data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
