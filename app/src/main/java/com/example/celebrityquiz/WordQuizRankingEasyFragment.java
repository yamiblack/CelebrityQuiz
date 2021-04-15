package com.example.celebrityquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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


public class WordQuizRankingEasyFragment extends Fragment {

    private ArrayList<Ranking> arrayList;
    private RecyclerView rvRanking;
    private RankingRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ranking, container, false);

        rvRanking = (RecyclerView) root.findViewById(R.id.rv_ranking);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getMultipleChoiceRanking();

        return root;
    }

    public void getMultipleChoiceRanking() {

        arrayList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());
        rvRanking.setLayoutManager(layoutManager);
        adapter = new RankingRecyclerViewAdapter(getActivity(), arrayList);
        rvRanking.setAdapter(adapter);

        db.collection("RANKING").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        if (documentSnapshot.get("level").toString().equals("1") && documentSnapshot.get("gameType").toString().equals("2")) {
                            Ranking ranking = documentSnapshot.toObject(Ranking.class);
                            arrayList.add(ranking);
                            Collections.sort(arrayList);
                        }
                    }

                    for (int i = 0; i < arrayList.size(); i++) {
                        arrayList.get(i).setRank(String.valueOf(i + 1));
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
