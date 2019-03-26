package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.Constant;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.WordParser;
import com.example.mohea.aba_ep1_words.listener.ScoreGraphElementListener;

public class ScoreGraphElementAdapter extends RecyclerView.Adapter<ScoreGraphElementAdapter.ScoreGraphElementViewHolder> {

    Context context;

    char[] elements;
    char[] elementSubs;

    ScoreGraphElementListener listener;

    int type;


    public ScoreGraphElementAdapter(Context context, ScoreGraphElementListener listener) {
        this.context = context;
        this.listener = listener;
    }


    public class ScoreGraphElementViewHolder extends RecyclerView.ViewHolder{

        TextView tvElement;

        public ScoreGraphElementViewHolder(@NonNull View itemView) {
            super(itemView);
            tvElement = itemView.findViewById(R.id.tv_element);
        }
    }

    @NonNull
    @Override
    public ScoreGraphElementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.score_element_item,null);
        return new ScoreGraphElementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreGraphElementViewHolder scoreGraphElementViewHolder, final int i) {

        scoreGraphElementViewHolder.tvElement.setText(String.valueOf(elements[i]));
        scoreGraphElementViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(elementSubs[i],type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return elements.length;
    }

    public void setType(int type,char[] elementSubs){
        this.type = type;

        this.elementSubs = elementSubs;
        this.elements = new WordParser().elementParsing(type,elementSubs);
    }
}
