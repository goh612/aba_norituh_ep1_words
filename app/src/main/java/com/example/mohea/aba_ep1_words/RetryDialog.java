package com.example.mohea.aba_ep1_words;

import android.app.Activity;
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
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.adapter.DialogRetryCheckAdapter;
import com.example.mohea.aba_ep1_words.adapter.DialogRetryListAdapter;
import com.example.mohea.aba_ep1_words.set.CheckSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class RetryDialog {

    Activity activity;

    AlertDialog retryDialog;

    ScoreSet scoreSet;
    String name;


    TextView tvRetryName;
    TextView tvRetryElement;
    TextView tvRetryDate;

    TextView tvRetryScore;
    TextView tvRetryStartScore;
    TextView tvRetryCenterScore;
    TextView tvRetryEndScore;


    Button btnRetryClose;
    Button btnRetryResearch;
    Button btnRetryStart;
    Button btnRetryDetail;

    RecyclerView rvRetryWords;
    RecyclerView rvRetryCheck;

    ArrayList<WordSet> wordSetArrayList;
    ArrayList<CheckSet> checkSetArrayList;

    SQLiteHelper sqLiteHelper;

    View detailView;

    AlertDialog detailDialog;


    public RetryDialog(Activity activity) {
        this.activity = activity;
        basicSet();
        sqLiteHelper = new SQLiteHelper(activity);
    }

    public void basicSet(){

        View retryDialogView = LayoutInflater.from(activity).inflate(R.layout.retry_dialog,null);

        onBindAndListener(retryDialogView);

        retryDialog = new AlertDialog.Builder(activity).setView(retryDialogView).create();
        retryDialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);
    }

    public void show(long scoreId,String name){

        scoreSet = sqLiteHelper.readScoreSet(scoreId);
        this.name = name;

        setData();

        retryDialog.show();
        settingParams(retryDialog,1);
    }

    public void settingParams(AlertDialog dialog,int code){
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();

        Point point = new Point();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getSize(point);

        if(code==1) {
            params.width = (int) ((point.x) / 1.1);
            params.height = (int) ((point.y) / 1.2);
        }else{
            params.width = (int) ((point.x) / 1.6);
            params.height = (int) ((point.y) / 1.2);
        }

        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
    }


    public void setData(){

        WordParser wordParser = new WordParser();

        String element = wordParser.subcodeParsing(scoreSet.getSubCode());

        String[] datas = scoreSet.getData().split("//");

        wordSetArrayList = wordParser.jsonParsing(datas[0]);
        checkSetArrayList = wordParser.checkParsing(datas[1]);

        String date = DateFormat.format("yyyy-MM-dd / kk:mm",scoreSet.getScoreId()).toString();

        String[] scores = scoreSet.getScore().split(",");

        tvRetryName.setText(name);
        tvRetryElement.setText(element);
        tvRetryDate.setText(date);
        tvRetryScore.setText(scores[0]);
        tvRetryStartScore.setText(scores[1]);
        tvRetryCenterScore.setText(scores[2]);
        tvRetryEndScore.setText(scores[3]);

        rvRetryWords.setAdapter(new DialogRetryListAdapter(activity,wordSetArrayList));
        rvRetryCheck.setAdapter(new DialogRetryCheckAdapter(activity,wordSetArrayList,checkSetArrayList));


    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_retry_close:
                    retryDialog.dismiss();
                    break;
                case R.id.btn_retry_research:
                    String subCode = scoreSet.getSubCode();
                    char element = subCode.charAt(2);
                    int type = Integer.parseInt(String.valueOf(subCode.charAt(1)));
                    int position = Integer.parseInt(String.valueOf(subCode.charAt(0)));
                    wordSetArrayList = sqLiteHelper.readData(new WordSet(type,position,element),2);

                case R.id.btn_retry_start:
                    Intent intentGoStudy = new Intent(activity, StudyActivity.class);
                    intentGoStudy.putParcelableArrayListExtra("list",wordSetArrayList);
                    activity.finish();
                    activity.startActivity(intentGoStudy);
                    activity.overridePendingTransition(0,0);
                    break;
                case R.id.btn_retry_detail:
                    detailDialog.show();
                    settingParams(detailDialog,2);
                    break;
            }
        }
    };



    public void onBindAndListener(View view){
        tvRetryName = view.findViewById(R.id.tv_retry_name);
        tvRetryElement = view.findViewById(R.id.tv_retry_element);
        tvRetryDate = view.findViewById(R.id.tv_retry_date);

        tvRetryScore = view.findViewById(R.id.tv_retry_score);
        tvRetryStartScore = view.findViewById(R.id.tv_retry_start_score);
        tvRetryCenterScore = view.findViewById(R.id.tv_retry_center_score);
        tvRetryEndScore = view.findViewById(R.id.tv_retry_end_score);

        btnRetryClose = view.findViewById(R.id.btn_retry_close);
        btnRetryResearch = view.findViewById(R.id.btn_retry_research);
        btnRetryStart = view.findViewById(R.id.btn_retry_start);
        btnRetryDetail = view.findViewById(R.id.btn_retry_detail);

        btnRetryClose.setOnClickListener(clickListener);
        btnRetryResearch.setOnClickListener(clickListener);
        btnRetryStart.setOnClickListener(clickListener);
        btnRetryDetail.setOnClickListener(clickListener);

        rvRetryWords = view.findViewById(R.id.rv_retry_words);

        detailView = LayoutInflater.from(activity).inflate(R.layout.retry_detail_dialog,null);
        rvRetryCheck = detailView.findViewById(R.id.rv_check_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2, LinearLayoutManager.VERTICAL,false);
        rvRetryWords.setLayoutManager(gridLayoutManager);
        rvRetryCheck.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));

        detailDialog = new AlertDialog.Builder(activity).setView(detailView).create();
        detailDialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);
    }


}

