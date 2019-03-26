package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;

import java.util.ArrayList;

public class WordTypeSelectAdapter extends BaseAdapter {

    Context context;

    ArrayList<Character> characters = new ArrayList<>();


    public WordTypeSelectAdapter(Context context,char[] chars) {
        this.context = context;
        for(char c:chars){
            characters.add(c);
        }
    }

    @Override
    public int getCount() {
        return characters.size();
    }

    @Override
    public Object getItem(int position) {
        return characters.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element_item,null);
        final TextView tv = view.findViewById(R.id.tv_element);
        tv.setText(String.valueOf(characters.get(i)));
        return view;
    }

}
