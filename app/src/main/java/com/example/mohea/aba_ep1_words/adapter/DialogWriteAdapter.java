package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class DialogWriteAdapter extends BaseAdapter {

    ArrayList<ClientSet> clientSetArrayList;
    Context context;

    public DialogWriteAdapter(Context context) {
        this.clientSetArrayList = new SQLiteHelper(context).getClientList();
        this.context = context;
    }

    @Override
    public int getCount() {
        return clientSetArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return clientSetArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.client_list_item,null);

        TextView tvClientListNumber = view.findViewById(R.id.tv_client_list_number);
        TextView tvClientListName = view.findViewById(R.id.tv_client_list_name);
        TextView tvClientListAge = view.findViewById(R.id.tv_client_list_age);
        TextView tvClientListgender = view.findViewById(R.id.tv_client_list_gender);

        ClientSet clientSet = clientSetArrayList.get(i);

        String gender = clientSet.getGender();

        if (gender.equals("m")) {
            gender = "남";
        } else {
            gender = "여";
        }


        tvClientListNumber.setText((i+1)+".");
        tvClientListName.setText(clientSet.getName());
        tvClientListAge.setText("("+clientSet.getAge()+"세/");
        tvClientListgender.setText(gender+")");

        return view;

    }
}
