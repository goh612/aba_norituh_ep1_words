package com.example.mohea.aba_ep1_words;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mohea.aba_ep1_words.adapter.StandardFragmentPagerAdapter;
import com.example.mohea.aba_ep1_words.fragment.SelectFragment;

import java.util.ArrayList;

public class WordSelectActivity extends AppCompatActivity {

    TabLayout tlWordSelect;
    ViewPager vpWordSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_select);

        onBind();   // findViewById 의 공간
        setData();

    }

    public void setData(){
        ArrayList<Fragment> selectTypeFragmentList = new ArrayList<>();
        ArrayList<String> selectTypeTitleList = new ArrayList<>();

        String[] titles = {"자음","모음","받침"};

        for(int i=0;i<3;i++){
            SelectFragment selectFragment = new SelectFragment();
            selectFragment.setType(i);

            selectTypeFragmentList.add(selectFragment);
            selectTypeTitleList.add(titles[i]);

        }

        vpWordSelect.setAdapter(new StandardFragmentPagerAdapter(getSupportFragmentManager(),selectTypeFragmentList,selectTypeTitleList));
        tlWordSelect.setupWithViewPager(vpWordSelect);


    }

    public void onBind(){
        tlWordSelect = findViewById(R.id.tl_word_select);
        vpWordSelect = findViewById(R.id.vp_word_select);
    }

    public void naviClick(View view){
        Intent naviIntent = null;

        switch (view.getId()){
            case R.id.btn_go_main:
                naviIntent = new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.btn_go_study:
                naviIntent = new Intent(this,WordSelectActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
