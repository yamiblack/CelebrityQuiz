package com.example.celebrityquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingRecyclerViewAdapter extends RecyclerView.Adapter<RankingRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<Ranking> items;
    private int rank;

    public RankingRecyclerViewAdapter(Context context, ArrayList<Ranking> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RankingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new RankingRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ranking ranking = items.get(position);

        try {
            holder.tvRank.setText(ranking.getRank());
            holder.tvEmail.setText("이메일 : " + ranking.getEmail());
            holder.tvScore.setText("점수 : " + ranking.getScore());
            holder.tvTime.setText("소요시간 : " + ranking.getTime());
        } catch (NullPointerException e) {
            Log.e("error ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRank;
        TextView tvEmail;
        TextView tvScore;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRank = itemView.findViewById(R.id.tv_ranking_rank);
            tvEmail = itemView.findViewById(R.id.tv_ranking_email);
            tvScore = itemView.findViewById(R.id.tv_ranking_score);
            tvTime = itemView.findViewById(R.id.tv_ranking_time);
        }
    }

    public Ranking getItem(int position) {
        return items.get(position);
    }
}
