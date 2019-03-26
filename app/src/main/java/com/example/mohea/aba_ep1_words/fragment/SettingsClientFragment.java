package com.example.mohea.aba_ep1_words.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.NoteDialog;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.adapter.ClientsListAdapter;
import com.example.mohea.aba_ep1_words.listener.ClientListClickListener;
import com.example.mohea.aba_ep1_words.listener.ClientListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsClientFragment extends Fragment {

    RecyclerView rvSettingsClient;
    Button btnSettingsClient,btnSettingsNote;

    TextView tvSettingsName,tvSettingsAge,tvSettingsGender;

    ClientSet clientSet;

    public SettingsClientFragment() {
        // Required empty public constructor
    }

    ClientListClickListener clientListClickListener = new ClientListClickListener() {
        @Override
        public void clientListClick(ClientSet client) {
            clientSet = client;

            String gender;

            if(client.getGender().equals("m")){
                gender = "남";
            }else{
                gender = "여";
            }

            tvSettingsName.setText(client.getName());
            tvSettingsAge.setText(String.valueOf(client.getAge()));
            tvSettingsGender.setText(gender);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_settings_client, container, false);

        rvSettingsClient = view.findViewById(R.id.rv_settings_client);
        rvSettingsClient.setAdapter(new ClientsListAdapter(getContext(),clientListClickListener,2));
        rvSettingsClient.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvSettingsClient.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        tvSettingsName = view.findViewById(R.id.tv_settings_name);
        tvSettingsAge = view.findViewById(R.id.tv_settings_age);
        tvSettingsGender = view.findViewById(R.id.tv_settings_gender);

        btnSettingsNote = view.findViewById(R.id.btn_settings_note);
        btnSettingsNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clientSet==null){
                    Toast.makeText(getContext(), "학습자를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new NoteDialog(getActivity(), clientSet, new ClientListener() {
                    @Override
                    public void clientComplete() {

                    }

                    @Override
                    public void clientComplete(String note) {

                    }
                }).show();
            }
        });

        btnSettingsClient = view.findViewById(R.id.btn_settings_client);
        btnSettingsClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clientSet==null){
                    Toast.makeText(getContext(), "학습자를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AlertDialog.Builder(getContext()).setTitle("학습자 삭제").setMessage(clientSet.getName()+" 님의 정보를 삭제하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean result = new SQLiteHelper(getContext()).deleteClient(clientSet.getId());
                                if(result) {
                                    Toast.makeText(getContext(), clientSet.getName() + " 학습자의 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    rvSettingsClient.setAdapter(new ClientsListAdapter(getContext(), clientListClickListener, 2));
                                }else{
                                    Toast.makeText(getContext(), "삭제 실패..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create().show();

            }
        });
        return view;
    }



}
