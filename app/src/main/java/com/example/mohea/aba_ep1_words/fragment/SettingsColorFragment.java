package com.example.mohea.aba_ep1_words.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.R;

import androidx.annotation.NonNull;

public class SettingsColorFragment extends Fragment {

    TextView tvPoint;
    SeekBar sbSettingsRed,sbSettingsGreen,sbSettingsBlue;
    Button btnSettingsColor;

    SharedPreferences sharedPreferences;


    int red = 0;
    int green = 0;
    int blue = 0;

    public SettingsColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String color = sharedPreferences.getString("point","255,0,0");

        String[] colors = color.split(",");

        red = Integer.parseInt(colors[0]);
        green = Integer.parseInt(colors[1]);
        blue = Integer.parseInt(colors[2]);

        View view = inflater.inflate(R.layout.fragment_settings_color, container, false);

        onBindAndListener(view);



        return view;
    }


    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {



            switch (seekBar.getId()){
                case R.id.sb_settings_red:
                    red = i;
                    break;
                case R.id.sb_settings_green:
                    green = i;
                    break;
                case R.id.sb_settings_blue:
                    blue = i;
                    break;
            }

            tvPoint.setTextColor(Color.rgb(red,green,blue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            setThumbStyle(seekBar);
        }
    };


    public void onBindAndListener(View view){

        tvPoint = view.findViewById(R.id.tv_point);
        sbSettingsRed = view.findViewById(R.id.sb_settings_red);
        sbSettingsBlue = view.findViewById(R.id.sb_settings_blue);
        sbSettingsGreen = view.findViewById(R.id.sb_settings_green);

        sbSettingsRed.setOnSeekBarChangeListener(seekBarChangeListener);
        sbSettingsBlue.setOnSeekBarChangeListener(seekBarChangeListener);
        sbSettingsGreen.setOnSeekBarChangeListener(seekBarChangeListener);

        sbSettingsRed.setProgress(red);
        sbSettingsGreen.setProgress(green);
        sbSettingsBlue.setProgress(blue);

        setThumbStyle(sbSettingsRed);
        setThumbStyle(sbSettingsBlue);
        setThumbStyle(sbSettingsGreen);



        btnSettingsColor = view.findViewById(R.id.btn_settings_color);
        btnSettingsColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("point",red+","+green+","+blue);
                editor.apply();
                Toast.makeText(getContext(), "세팅되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setThumbStyle(SeekBar seekBar){
        if(seekBar.getProgress()>180){
            seekBar.setThumb(getResources().getDrawable(R.drawable.good));
        }else if(seekBar.getProgress()>90&&seekBar.getProgress()<=180){
            seekBar.setThumb(getResources().getDrawable(R.drawable.soso));
        }else{
            seekBar.setThumb(getResources().getDrawable(R.drawable.bad));
        }
    }

    public void set(SharedPreferences sharedPreferences){

        this.sharedPreferences = sharedPreferences;

    }

}
