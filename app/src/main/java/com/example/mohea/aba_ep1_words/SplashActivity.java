package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    Animation animMain,animText,animEnd;
    ImageView ivSplash;
    TextView tvSplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        onBind();
        animMain.start();
    }

    public class SetBaseTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean result = new SQLiteHelper(SplashActivity.this).settingData(getResources().openRawResource(R.raw.base_words));

            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.putString("point", "255,0,0");
            editor.apply();

            animText.reset();

            ivSplash.setAnimation(animEnd);
            animEnd.start();

        }
    }


    public void onSetBase(){


        sharedPreferences = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst",true);

        if(isFirst){
            tvSplash.setAnimation(animText);
            animText.start();

            new SetBaseTask().execute();

        }else{
            ivSplash.setAnimation(animEnd);
            animEnd.start();
        }
    }


    public void onBind(){
        animMain = AnimationUtils.loadAnimation(this,R.anim.alpha);
        animMain.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onSetBase();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animText = AnimationUtils.loadAnimation(this,R.anim.alpha_text);

        animEnd = AnimationUtils.loadAnimation(this,R.anim.alpha_end);
        animEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                ivSplash.setVisibility(View.INVISIBLE);

                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                overridePendingTransition(0,0);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivSplash = findViewById(R.id.iv_splash);
        ivSplash.setAnimation(animMain);
        tvSplash = findViewById(R.id.tv_splash);
    }

    @Override
    public void onBackPressed() {

    }
}
