package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mohea.aba_ep1_words.adapter.StandardFragmentPagerAdapter;
import com.example.mohea.aba_ep1_words.fragment.CreditFragment;
import com.example.mohea.aba_ep1_words.fragment.SettingsAddFragment;
import com.example.mohea.aba_ep1_words.fragment.SettingsClientFragment;
import com.example.mohea.aba_ep1_words.fragment.SettingsColorFragment;
import com.example.mohea.aba_ep1_words.fragment.SettingsResetFragment;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    TabLayout tlSettings;
    ViewPager vpSettings;

    String pointColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        onBind();
    }

    public void onBind(){
        tlSettings = findViewById(R.id.tl_settings);
        vpSettings = findViewById(R.id.vp_settings);

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("setting", Activity.MODE_PRIVATE);

        SettingsColorFragment settingsColorFragment = new SettingsColorFragment();
        settingsColorFragment.set(sharedPreferences);


        fragmentArrayList.add(new SettingsAddFragment());
        fragmentArrayList.add(settingsColorFragment);
        fragmentArrayList.add(new SettingsClientFragment());
        fragmentArrayList.add(new CreditFragment());
        fragmentArrayList.add(new SettingsResetFragment());

        titles.add("단어추가");
        titles.add("강조 색");
        titles.add("학습자 삭제");
        titles.add("만든이");
        titles.add("초기화");

        vpSettings.setAdapter(new StandardFragmentPagerAdapter(getSupportFragmentManager(),fragmentArrayList,titles));
        tlSettings.setupWithViewPager(vpSettings);
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
