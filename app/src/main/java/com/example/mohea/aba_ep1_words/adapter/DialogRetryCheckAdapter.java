package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.set.CheckSet;
import com.example.mohea.aba_ep1_words.set.WordSet;

import java.util.ArrayList;

public class DialogRetryCheckAdapter extends RecyclerView.Adapter<DialogRetryCheckAdapter.RetryViewHolder> {

    Context context;
    ArrayList<WordSet> wordSets;
    ArrayList<CheckSet> checkSets;

    public DialogRetryCheckAdapter(Context context, ArrayList<WordSet> wordSets, ArrayList<CheckSet> checkSets) {
        this.context = context;
        this.wordSets = wordSets;
        this.checkSets = checkSets;
    }

    public class RetryViewHolder extends RecyclerView.ViewHolder{

        TextView tvCheckNum,tvCheckWord,tvCheckScore,tvCheckRate;

        public RetryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCheckNum = itemView.findViewById(R.id.tv_check_num);
            tvCheckWord = itemView.findViewById(R.id.tv_check_word);
            tvCheckScore = itemView.findViewById(R.id.tv_check_score);
            tvCheckRate = itemView.findViewById(R.id.tv_check_rate);
        }
    }

    @NonNull
    @Override
    public RetryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.retry_check_dialog_item,null);
        return new RetryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RetryViewHolder retryViewHolder, int i) {

        CheckSet checkSet = checkSets.get(i);

        retryViewHolder.tvCheckNum.setText(String.valueOf(checkSet.getNum()+1)+".");
        retryViewHolder.tvCheckWord.setText(wordSets.get(i).getWord());
        retryViewHolder.tvCheckScore.setText(checkSet.getScore());
        retryViewHolder.tvCheckRate.setText(checkSet.getRate()+"%");

    }

    @Override
    public int getItemCount() {
        return wordSets.size();
    }
}












































