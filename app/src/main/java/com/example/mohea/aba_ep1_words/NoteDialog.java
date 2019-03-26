package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.adapter.ScoreListAdapter;
import com.example.mohea.aba_ep1_words.listener.ClientListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class NoteDialog {

    Activity activity;

    AlertDialog noteDialog;

    ClientSet clientSet;

    ClientListener clientListener;


    TextView tvNoteName;
    TextView tvNoteAge;
    TextView tvNoteGender;

    EditText etNote;
    TextView tvNote;

    Button btnNote;

    boolean isEditable = false;


    public NoteDialog(Activity activity, ClientSet clientSet, ClientListener clientListener) {
        this.activity = activity;
        this.clientSet = clientSet;
        this.clientListener = clientListener;
        basicSet();
    }

    public void basicSet(){

        View noteDialogView = LayoutInflater.from(activity).inflate(R.layout.note_dialog,null);

        onBindAndListener(noteDialogView);

        noteDialog = new AlertDialog.Builder(activity).setView(noteDialogView).create();
        noteDialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);
    }

    public void show(){

        setData();
        noteDialog.show();
    }


    public void setData(){

        String name = clientSet.getName();
        String age = String.valueOf(clientSet.getAge());
        String gender;
        String note = clientSet.getNote();


        if(clientSet.getGender().equals("m")){
            gender = "남";
        }else{
            gender = "여";
        }

        tvNoteName.setText(name);
        tvNoteAge.setText(age);
        tvNoteGender.setText(gender);

        if(note!=null){
            etNote.setText(note);
            tvNote.setText(note);
        }

    }


    public void onBindAndListener(View view){
        tvNoteName = view.findViewById(R.id.tv_note_name);
        tvNoteAge = view.findViewById(R.id.tv_note_age);
        tvNoteGender = view.findViewById(R.id.tv_note_gender);

        etNote = view.findViewById(R.id.et_note);
        tvNote = view.findViewById(R.id.tv_note);

        btnNote = view.findViewById(R.id.btn_note);

        tvNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tvNote.setVisibility(View.INVISIBLE);
                etNote.setVisibility(View.VISIBLE);
                btnNote.setText("저장");
                btnNote.setBackgroundResource(R.drawable.orange_corner_back);                                       //todo EditText 랑 버튼 처리 안됐음..!!!!!!
                return isEditable=true;
            }
        });

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEditable) {

                    String note = etNote.getText().toString();

                    clientSet.setNote(note);

                    boolean result = new SQLiteHelper(activity).updateClientNote(clientSet);

                    if (result) {
                        isEditable = false;
                        Toast.makeText(activity, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                        tvNote.setText(note);
                        tvNote.setVisibility(View.VISIBLE);
                        etNote.setVisibility(View.INVISIBLE);

                        btnNote.setText("확인");
                        btnNote.setBackgroundResource(R.drawable.green_corner_back);
                        clientListener.clientComplete(note);
                    } else {
                        Toast.makeText(activity, "실패...", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    noteDialog.dismiss();
                }

            }
        });

    }


}

