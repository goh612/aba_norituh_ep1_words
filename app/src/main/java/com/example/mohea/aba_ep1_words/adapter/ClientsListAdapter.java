package com.example.mohea.aba_ep1_words.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.listener.ClientListClickListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class ClientsListAdapter extends RecyclerView.Adapter<ClientsListAdapter.ClientsListViewHolder> {

    Context context;
    ArrayList<ClientSet> clientSets;
    ClientListClickListener clientListClickListener;
    int type;

    int count=0;

    public ClientsListAdapter(Context context, ClientListClickListener clientListClickListener,int type) {
        this.context = context;
        this.clientSets = new SQLiteHelper(context).getClientList();
        this.clientListClickListener = clientListClickListener;
        this.type = type;
    }

    public class ClientsListViewHolder extends RecyclerView.ViewHolder{

        TextView tvClientListNumber;
        TextView tvClientListName;
        TextView tvClientListAge;
        TextView tvClientListGender;
        public ClientsListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientListNumber = itemView.findViewById(R.id.tv_client_list_number);
            tvClientListName = itemView.findViewById(R.id.tv_client_list_name);
            tvClientListAge = itemView.findViewById(R.id.tv_client_list_age);
            tvClientListGender = itemView.findViewById(R.id.tv_client_list_gender);
        }
    }

    @NonNull
    @Override
    public ClientsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {



        View itemView;

        if(count==clientSets.size()&&type==1){
            itemView = LayoutInflater.from(context).inflate(R.layout.add_client_item,null);
        }else{
            itemView = LayoutInflater.from(context).inflate(R.layout.client_list_item, null);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        count++;
        return new ClientsListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsListViewHolder clientsListViewHolder, final int i) {

        if(i==clientSets.size()){
            clientsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clientListClickListener.clientListClick(null);
                }
            });
        }else {

            String gender = clientSets.get(i).getGender();

            if (gender.equals("m")) {
                gender = "남";
            } else {
                gender = "여";
            }

            clientsListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clientListClickListener.clientListClick(clientSets.get(i));
                }
            });

            clientsListViewHolder.tvClientListNumber.setText((i + 1) + ".");
            clientsListViewHolder.tvClientListName.setText(clientSets.get(i).getName());
            clientsListViewHolder.tvClientListAge.setText("(" + clientSets.get(i).getAge() + "세/");
            clientsListViewHolder.tvClientListGender.setText(gender + ")");
        }
    }

    @Override
    public int getItemCount() {
        if(type==1) {
            return clientSets.size() + 1;
        }else{
            return clientSets.size();
        }
    }
}
