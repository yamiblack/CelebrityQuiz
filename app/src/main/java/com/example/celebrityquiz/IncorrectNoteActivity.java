package com.example.celebrityquiz;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class IncorrectNoteActivity extends AppCompatActivity {

    Context context = this;

    private ArrayList<IncorrectNote> arrayList;
    private RecyclerView rvIncorrectNote;
    private IncorrectNoteRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrectnote);

        rvIncorrectNote = (RecyclerView) findViewById(R.id.rv_incorrectNotes);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getIncorrectNote();

    }

    public void getIncorrectNote() {

        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        rvIncorrectNote.setLayoutManager(layoutManager);
        adapter = new IncorrectNoteRecyclerViewAdapter(context, arrayList);
        rvIncorrectNote.setAdapter(adapter);

        db.collection("INCORRECTNOTE").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("email").equals(auth.getCurrentUser().getEmail())) {
                            Log.e("email", documentSnapshot.get("email").toString());
                            Log.e("imageURL", documentSnapshot.get("imageURL").toString());
                            Log.e("answer", documentSnapshot.get("answer").toString());
                            IncorrectNote incorrectNote = documentSnapshot.toObject(IncorrectNote.class);
                            arrayList.add(incorrectNote);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    if (arrayList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "현재까지는 틀린 문제가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
