package com.example.mohea.aba_ep1_words;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.adapter.StandardFragmentPagerAdapter;
import com.example.mohea.aba_ep1_words.fragment.ScoreGraphFragment;
import com.example.mohea.aba_ep1_words.fragment.ScoreListFragment;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    ViewPager vpScore;
    TextView tvScoreClient;

    ClientSet clientSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        clientSet = getIntent().getParcelableExtra("clientSet");
        onBind();
        setData();

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(ScoreActivity.this,DocumentActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    public void setData(){
        ArrayList<Fragment> scoreFragmentList = new ArrayList<>();
        ArrayList<String> scoreTitleList = new ArrayList<>();

        scoreTitleList.add("리스트");
        scoreTitleList.add("그래프");

        ScoreListFragment scoreListFragment = new ScoreListFragment();
        scoreListFragment.setData(clientSet.getName(),clientSet.getId());

        ScoreGraphFragment scoreGraphFragment = new ScoreGraphFragment();
        scoreGraphFragment.setData(clientSet);

        scoreFragmentList.add(scoreListFragment);
        scoreFragmentList.add(scoreGraphFragment);

        vpScore.setAdapter(new StandardFragmentPagerAdapter(getSupportFragmentManager(),scoreFragmentList,scoreTitleList));

    }


    public void onBind(){
        vpScore = findViewById(R.id.vp_score);
        tvScoreClient = findViewById(R.id.tv_score_client);

        String gender;

        if(clientSet.getGender().equals("m")){
            gender = "남";
        }else{
            gender = "여";
        }

        tvScoreClient.setText("이름 : "+clientSet.getName()+"   나이 : "+clientSet.getAge()+"세   성별 : "+gender);
        tvScoreClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpScore.setCurrentItem(1,true);
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

}
