package com.example.celebrityquiz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class IncorrectNoteRecyclerViewAdapter extends RecyclerView.Adapter<IncorrectNoteRecyclerViewAdapter.ViewHolder> {
    Context context;
    ArrayList<IncorrectNote> items;

    public IncorrectNoteRecyclerViewAdapter(Context context, ArrayList<IncorrectNote> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public IncorrectNoteRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_incorrectnote, parent, false);
        return new IncorrectNoteRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IncorrectNote incorrectNote = items.get(position);

        try {
            String imgURL = incorrectNote.getImageURL();
            Glide.with(context).load(imgURL).into(holder.ivCelebrityImage);
            holder.tvAnswer.setText(incorrectNote.getAnswer());
        } catch (NullPointerException e) {
            Log.e("error ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCelebrityImage;
        TextView tvAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCelebrityImage = itemView.findViewById(R.id.iv_celebrityImage);
            tvAnswer = itemView.findViewById(R.id.tv_answer);
        }
    }

    public IncorrectNote getItem(int position) {
        return items.get(position);
    }
}
