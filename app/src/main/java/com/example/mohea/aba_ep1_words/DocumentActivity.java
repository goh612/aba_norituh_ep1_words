package com.example.mohea.aba_ep1_words;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.adapter.ClientsListAdapter;
import com.example.mohea.aba_ep1_words.listener.ClientListener;
import com.example.mohea.aba_ep1_words.listener.ClientListClickListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;

public class DocumentActivity extends AppCompatActivity {

    RecyclerView rvDocuClients;
    TextView tvDocuName,tvDocuAge,tvDocuGender,tvDocuNote;
    Button btnDocu;

    ClientSet client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        onBindAndListener();

        setClientList();

    }

    ClientListClickListener clientListClickListener = new ClientListClickListener() {
        @Override
        public void clientListClick(ClientSet clientSet) {

            if(clientSet==null){
                new ClientAdd(DocumentActivity.this,clientListener).clientAddDialogShow();
                return;
            }

            client = clientSet;

            String gender;

            if(clientSet.getGender().equals("m")){
                gender = "남";
            }else{
                gender = "여";
            }

            String note = client.getNote();

            tvDocuName.setText(clientSet.getName());
            tvDocuAge.setText(String.valueOf(clientSet.getAge()));
            tvDocuGender.setText(gender);

            if(note==null||note.equals("")){
                tvDocuNote.setText("");
                tvDocuNote.setHint("길게 클릭하시면 입력창이 열립니다.");
                return;
            }

            displayNote(note);
        }
    };


    ClientListener clientListener = new ClientListener() {
        @Override
        public void clientComplete() {
            rvDocuClients.setAdapter(new ClientsListAdapter(DocumentActivity.this,clientListClickListener,1));
        }

        @Override
        public void clientComplete(String note) {
            rvDocuClients.setAdapter(new ClientsListAdapter(DocumentActivity.this,clientListClickListener,1));

            displayNote(note);
        }
    };



    public void setClientList(){
        rvDocuClients.setAdapter(new ClientsListAdapter(this,clientListClickListener,1));
        rvDocuClients.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvDocuClients.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
    }

    public void onBindAndListener(){
        rvDocuClients = findViewById(R.id.rv_docu_clients);
        tvDocuName = findViewById(R.id.tv_docu_name);
        tvDocuAge = findViewById(R.id.tv_docu_age);
        tvDocuGender = findViewById(R.id.tv_docu_gender);
        tvDocuNote = findViewById(R.id.tv_docu_note);

        btnDocu = findViewById(R.id.btn_docu);
        btnDocu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(client==null){
                    Toast.makeText(DocumentActivity.this, "조회하실 학습자를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(DocumentActivity.this,ScoreActivity.class);
                intent.putExtra("clientSet",client);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        tvDocuNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(client==null){
                    Toast.makeText(DocumentActivity.this, "학습자를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }

                new NoteDialog(DocumentActivity.this,client,clientListener).show();
                return true;
            }
        });

    }

    public void naviClick(View view){
        Intent naviIntent = null;

        switch (view.getId()){
            case R.id.btn_go_main:
                naviIntent = new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
            case R.id.btn_go_study:
                naviIntent = new Intent(this,WordSelectActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
            case R.id.btn_go_score:
                naviIntent = new Intent(this,DocumentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
            case R.id.btn_go_settings:
                naviIntent = new Intent(this,SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
        }
        finish();
        startActivity(naviIntent);
        overridePendingTransition(0,0);
    }

    public void displayNote(String note){
        if(note.length()>40){
            note = note.substring(0,40)+"...";
        }
        tvDocuNote.setText(note);
    }
}
