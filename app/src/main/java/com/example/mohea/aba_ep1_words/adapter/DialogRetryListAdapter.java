package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.set.WordSet;

import java.util.ArrayList;

public class DialogRetryListAdapter extends RecyclerView.Adapter<DialogRetryListAdapter.RetryViewHolder> {

    Context context;
    ArrayList<WordSet> wordSets;

    public DialogRetryListAdapter(Context context, ArrayList<WordSet> wordSets) {
        this.context = context;
        this.wordSets = wordSets;
    }

    public class RetryViewHolder extends RecyclerView.ViewHolder{

        TextView tvSelect;

        public RetryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSelect = itemView.findViewById(R.id.tv_select_dialog);
        }
    }

    @NonNull
    @Override
    public RetryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.word_select_dialog_item,null);
        return new RetryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RetryViewHolder retryViewHolder, int i) {
            retryViewHolder.tvSelect.setText(wordSets.get(i).getWord());
    }

    @Override
    public int getItemCount() {
        return wordSets.size();
    }
}
