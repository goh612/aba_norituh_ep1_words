package com.example.mohea.aba_ep1_words.fragment;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.adapter.ClientsListAdapter;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsResetFragment extends Fragment {

    Button btnSettginsWordReset,btnSettginsClientReset,btnSettginsAllReset;

    SQLiteHelper sqLiteHelper;

    AlertDialog dialog;



    public SettingsResetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_reset, container, false);

        onBindAndListener(view);


        return view;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_settings_word_reset:
                    dialog = new AlertDialog.Builder(getContext()).setTitle("단어 리스트 초기화").setMessage("단어 리스트를 초기화 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = sqLiteHelper.resetWordsList();
                                    if (result) {
                                        Toast.makeText(getContext(), "단어리스트 초기화 성공", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "초기화 할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    dialog.dismiss();
                                    dialog = null;

                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).create();
                    dialog.show();
                    break;
                case R.id.btn_settings_client_reset:
                    dialog = new AlertDialog.Builder(getContext()).setTitle("학습자 리스트 초기화").setMessage("학습자 리스트를 초기화 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = sqLiteHelper.resetClientList();
                                    if (result) {
                                        Toast.makeText(getContext(), "학습자리스트 초기화 성공", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "초기화 할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                    dialog.dismiss();
                                    dialog = null;

                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).create();
                    dialog.show();
                    break;
                case R.id.btn_settings_all_reset:
                    dialog = new AlertDialog.Builder(getContext()).setTitle("모든 데이터 초기화").setMessage("모든 데이터를 초기화 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = sqLiteHelper.resetAll();
                                    if (result) {
                                        Toast.makeText(getContext(), "데이터 초기화 성공", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getContext(), "초기화 할 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                    dialog = null;

                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).create();
                    dialog.show();
                    break;
            }
        }
    };

    public void onBindAndListener(View view){
        btnSettginsWordReset = view.findViewById(R.id.btn_settings_word_reset);
        btnSettginsClientReset = view.findViewById(R.id.btn_settings_client_reset);
        btnSettginsAllReset = view.findViewById(R.id.btn_settings_all_reset);

        btnSettginsWordReset.setOnClickListener(clickListener);
        btnSettginsClientReset.setOnClickListener(clickListener);
        btnSettginsAllReset.setOnClickListener(clickListener);

        sqLiteHelper = new SQLiteHelper(getContext());

    }


}
