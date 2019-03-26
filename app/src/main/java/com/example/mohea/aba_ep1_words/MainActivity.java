package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button btnStudy,btnDocument,btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onBind();   // findViewById 의 공간


    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.btn_study:
                    intent = new Intent(MainActivity.this,WordSelectActivity.class);
                    break;
                case R.id.btn_document:
                    intent = new Intent(MainActivity.this, DocumentActivity.class);
                    break;
                case R.id.btn_setting:
                    intent =  new Intent(MainActivity.this,SettingsActivity.class);
                    break;
            }

            if(intent!=null) {
                startActivity(intent);
                overridePendingTransition(0,0);
            }else{
                Toast.makeText(MainActivity.this, "실패", Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void onBind(){
        btnStudy = findViewById(R.id.btn_study);
        btnDocument = findViewById(R.id.btn_document);
        btnSetting = findViewById(R.id.btn_setting);

        btnStudy.setOnClickListener(clickListener);
        btnDocument.setOnClickListener(clickListener);
        btnSetting.setOnClickListener(clickListener);

    }



}
