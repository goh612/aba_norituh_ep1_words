package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.set.WordSet;

import java.util.ArrayList;

public class DialogWordsAdapter extends BaseAdapter {

    Context context;
    ArrayList<WordSet> wordList;
    int kind;

    public DialogWordsAdapter(Context context, ArrayList<WordSet> list) {
        this.context = context;
        this.wordList = list;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.word_select_dialog_item,null);


        TextView tv = view.findViewById(R.id.tv_select_dialog);
        tv.setText(wordList.get(i).getWord());

        return view;
    }
}
