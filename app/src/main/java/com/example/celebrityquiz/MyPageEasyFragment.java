package com.example.celebrityquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class MyPageEasyFragment extends Fragment {

    private TextView tvBestScore;
    private TextView tvAverageScore;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private int bestScore = -1;
    private double averageScore = -1;
    private double count = 0, scoreSum = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mypage_score, container, false);

        tvBestScore = (TextView) root.findViewById(R.id.tv_bestScore);
        tvAverageScore = (TextView) root.findViewById(R.id.tv_averageScore);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getData();

        return root;
    }

    public void getData() {

        db.collection("RANKING").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("email").toString().equals(auth.getCurrentUser().getEmail()) && documentSnapshot.get("level").toString().equals("1")) {

                            if (Integer.parseInt(documentSnapshot.get("score").toString()) > bestScore) {
                                bestScore = Integer.parseInt(documentSnapshot.get("score").toString());
                            }

                            scoreSum += Integer.parseInt(documentSnapshot.get("score").toString());
                            count++;

                            Log.e("score", documentSnapshot.get("score").toString());
                        }
                    }

                    if (bestScore == -1) {
                        tvBestScore.setText("기록 없음");
                    }

                    if (averageScore == -1) {
                        tvAverageScore.setText("기록 없음");
                    }

                    if (count != 0) {
                        tvBestScore.setText(String.valueOf(bestScore));
                        tvAverageScore.setText(formatting(scoreSum, count));
                    }
                }
            }
        });
    }

    public String formatting(double a, double b) {
        return String.format("%.2f", a / b);
    }

}
