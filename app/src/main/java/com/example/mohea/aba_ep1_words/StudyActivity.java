package com.example.mohea.aba_ep1_words;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.adapter.DialogWriteAdapter;
import com.example.mohea.aba_ep1_words.adapter.StudyFragmentPagerAdapter;
import com.example.mohea.aba_ep1_words.listener.ClientListener;
import com.example.mohea.aba_ep1_words.listener.StudyListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity {

    ArrayList<WordSet> wordList;

    ViewPager vpStudy;
    Button btnBack;
    Button btnFinish;

    TextView tvStudyScore;

    int allScore = 0;
    int nowScore = 0;

    int startNowScore = 0;
    int startAllScore = 0;

    int centerNowScore = 0;
    int centerAllScore = 0;

    int endNowScore = 0;
    int endAllScore = 0;

    int[] numCorrect;
    int[] numTry;

    TextToSpeech tts;




    boolean isScoreVisible = false;

    AlertDialog adFinish;
    AlertDialog adWrite;

    ListView lvWriteClient;

    ScoreSet scoreSet;
    ClientSet clientSet;

    String score;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        wordList = getIntent().getParcelableArrayListExtra("list");

        numCorrect = new int[wordList.size()];
        numTry = new int[wordList.size()];

        onBind();

        vpStudy.setAdapter(new StudyFragmentPagerAdapter(getSupportFragmentManager(), wordList, new StudyListener() {
            @Override
            public void studyScoreCheck(int kind, int isCorrect,int now) {
                if(isCorrect==1){

                    numTry[now]++;
                    numCorrect[now]++;

                    allScore++;
                    nowScore++;
                    switch (kind){
                        case 1:
                            startNowScore++;
                            startAllScore++;
                            break;
                        case 2:
                            centerNowScore++;
                            centerAllScore++;
                            break;
                        case 3:
                            endNowScore++;
                            endAllScore++;
                            break;
                    }

                }else{
                    numTry[now]++;
                    allScore++;
                    switch (kind){
                        case 1:
                            startAllScore++;
                            break;
                        case 2:
                            centerAllScore++;
                            break;
                        case 3:
                            endAllScore++;
                            break;
                    }

                }

                setScore();



            }
            @Override
            public void studyTTS(String word) {
                tts.speak(word,TextToSpeech.QUEUE_FLUSH,null,null);
            }
        },color));

    }

    public void setScore(){

        score = nowScore + "/" + allScore;

        if(isScoreVisible) {
            tvStudyScore.setText(score);
        }else{
            tvStudyScore.setText("점수표시");
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_back:
                    finish();
                    startActivity(new Intent(StudyActivity.this,WordSelectActivity.class));
                    overridePendingTransition(0,0);
                    break;
                case R.id.btn_finish:
                    adFinish = settingDialog(1);
                    adFinish.show();
                    break;
                case R.id.tv_study_score:
                    if(isScoreVisible){
                        isScoreVisible = false;
                        setScore();
                    }else{
                        isScoreVisible = true;
                        setScore();
                    }
                    break;
            }

        }
    };

    public AlertDialog settingDialog(int what){
        View dialogView;
        if(what==1){
            dialogView = LayoutInflater.from(StudyActivity.this).inflate(R.layout.study_finish_dialog,null);

            RelativeLayout layFinish = dialogView.findViewById(R.id.lay_finish);
            TextView tvFinish = dialogView.findViewById(R.id.tv_finish);



            switch ((int)(Math.random()*1)){
                case 0:
                    layFinish.setBackgroundResource(R.drawable.finish_image1);
                    break;
                /*case 1:
                    layFinish.setBackgroundResource(R.drawable.finish_image2);
                    break;
                case 2:
                    layFinish.setBackgroundResource(R.drawable.finish_image3);
                    break;
                case 3:
                    layFinish.setBackgroundResource(R.drawable.finish_image4);
                    break;
                case 4:
                    layFinish.setBackgroundResource(R.drawable.finish_image5);
                    break;
                case 5:
                    layFinish.setBackgroundResource(R.drawable.finish_image6);
                    break;
                case 6:
                    layFinish.setBackgroundResource(R.drawable.finish_image7);
                    break;
                case 7:
                    layFinish.setBackgroundResource(R.drawable.finish_image8);
                    break;
                case 8:
                    layFinish.setBackgroundResource(R.drawable.finish_image9);
                    break;
                case 9:
                    layFinish.setBackgroundResource(R.drawable.finish_image10);
                    break;
                case 10:
                    layFinish.setBackgroundResource(R.drawable.finish_image11);
                    break;
                case 11:
                    layFinish.setBackgroundResource(R.drawable.finish_image12);
                    break;
                case 12:
                    layFinish.setBackgroundResource(R.drawable.finish_image13);
                    break;*/
            }

            switch ((int)(Math.random()*10)){
                case 0:
                    tvFinish.setText("너무 잘했어요!");
                    break;
                case 1:
                    tvFinish.setText("진짜 잘했어요!");
                    break;
                case 2:
                    tvFinish.setText("대단한걸!");
                    break;
                case 3:
                    tvFinish.setText("엄청난 노력,\n칭찬해요!");
                    break;
                case 4:
                    tvFinish.setText("진짜 대단해요!");
                    break;
                case 5:
                    tvFinish.setText("너무 열심히\n잘했어요!");
                    break;
                case 6:
                    tvFinish.setText("칭찬합니다!");
                    break;
                case 7:
                    tvFinish.setText("짱이야!");
                    break;
                case 8:
                    tvFinish.setText("대단해요!");
                    break;
                case 9:
                    tvFinish.setText("잘했어요!");
                    break;
            }




            onFinishDialogBindAndListener(dialogView);
        }else{
            dialogView = LayoutInflater.from(StudyActivity.this).inflate(R.layout.write_score_dialog,null);
            onWriteDialogBindAndListenerAndAdapter(dialogView);
        }
        AlertDialog dialog = new AlertDialog.Builder(StudyActivity.this).create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);
        dialog.setView(dialogView);
        return dialog;
    }

    View.OnClickListener dialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.btn_finish_write:
                    if(score==null){
                        Toast.makeText(StudyActivity.this, "점수가 없는 학습은 기록할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adWrite = settingDialog(2);
                    adWrite.show();
                    break;
                case R.id.btn_finish_end:
                    startActivity(new Intent(StudyActivity.this,WordSelectActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    adFinish.dismiss();
                    adFinish = null;
                    finish();
                    overridePendingTransition(0,0);
                    break;
                case R.id.btn_finish_restart:
                    adFinish.dismiss();
                    adFinish = null;
                    startActivity(new Intent(StudyActivity.this,StudyActivity.class).putParcelableArrayListExtra("list",wordList));
                    finish();
                    overridePendingTransition(0,0);
                    break;
                case R.id.btn_write_register:   // 회원 추가
                    new ClientAdd(StudyActivity.this, clientListener).clientAddDialogShow();
                    break;
                case R.id.btn_write_close:
                    adWrite.cancel();
                    break;
            }


        }
    };

    ClientListener clientListener = new ClientListener() {
        @Override
        public void clientComplete() {
            lvWriteClient.setAdapter(new DialogWriteAdapter(StudyActivity.this));
        }

        @Override
        public void clientComplete(String note) {

        }
    };

    AdapterView.OnItemClickListener clientListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            clientSet = (ClientSet) adapterView.getItemAtPosition(i);

            new AlertDialog.Builder(StudyActivity.this).setMessage(clientSet.getName()+" 님의 기록지에 점수를 기록하시겠습니까?").setTitle("기록").setCancelable(false)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            scoreSet = makeNewScoreSet(clientSet.getId());

                            boolean result = new SQLiteHelper(StudyActivity.this).writeScore(scoreSet);

                            if(result){
                                new AlertDialog.Builder(StudyActivity.this).setTitle("입력성공").setMessage("점수가 입력되었습니다. 기록보기 화면으로 이동하시겠습니까?").setCancelable(false)
                                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                adFinish.dismiss();
                                                adFinish = null;
                                                finish();
                                                startActivity(new Intent(StudyActivity.this,ScoreActivity.class).putExtra("clientSet",clientSet));
                                                overridePendingTransition(0,0);
                                            }
                                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        adFinish.dismiss();
                                        adFinish = null;
                                        finish();
                                        startActivity(new Intent(StudyActivity.this,WordSelectActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        overridePendingTransition(0,0);
                                    }
                                }).create().show();
                            }else{
                                Toast.makeText(StudyActivity.this, "입력실패....ㅎ", Toast.LENGTH_SHORT).show();
                            }

                            adWrite.dismiss();
                            adWrite = null;
                        }
                    }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).create().show();

        }
    };




    public ScoreSet makeNewScoreSet(long userId){
        ScoreSet scoreSet = new ScoreSet();

        String scoreData = score+","+startNowScore+"/"+startAllScore+","+centerNowScore+"/"+centerAllScore+","+endNowScore+"/"+endAllScore;

        scoreSet.setUserId(userId);
        scoreSet.setScoreId(System.currentTimeMillis());
        scoreSet.setScore(scoreData);
        scoreSet.setSubCode(makeCode());
        scoreSet.setData(makeData());

        return scoreSet;
    }

    // 학습 단어 목록을 점수 데이터에 기록하기 위해 JSON 형식으로 변환하는 메소드
    public String makeData(){
        String data = "";
        JSONArray jsonArray = new JSONArray();

        for(int i=0;i<wordList.size();i++) {
            JSONObject jsonObject = new JSONObject();
            WordSet toDataWordSet = wordList.get(i);
            try {
                jsonObject.put(Constant.WORD_TYPE,toDataWordSet.getType());
                jsonObject.put(Constant.WORD_POSITION,toDataWordSet.getPosition());
                jsonObject.put(Constant.WORD_ELEMENT,String.valueOf(toDataWordSet.getElement()));
                jsonObject.put(Constant.WORD_WORD,toDataWordSet.getWord());
                jsonObject.put(Constant.WORD_CODE,toDataWordSet.getCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }

        String checkData = "";

        for(int i=0;i<wordList.size();i++){
            int correct = numCorrect[i];
            int trys = numTry[i];

            String rate;
            if(correct==0){
                rate = "0";
            }else{
                rate = String.valueOf(correct*100/trys);
            }

            checkData+= i+"s"+correct+"/"+trys+"r"+rate+",";

        }


        data = jsonArray.toString()+"//"+checkData;

        Log.d("저장 데이터 확인중",data);

        return data;
    }


    public String makeCode(){
        String code = "";
        boolean isStart = false;
        boolean isCenter = false;
        boolean isEnd = false;

        int position = 0;

        WordSet wordSet = wordList.get(0);

        for(int i=0;i<wordList.size();i++){
            position = wordList.get(i).getPosition();
            if(position==1){
                isStart = true;
            }else if(position==2){
                isCenter = true;
            }else if(position==3){
                isEnd = true;
            }
        }

        position = new Check().checkPosition(isStart,isCenter,isEnd);

        code += position;
        code += wordSet.getType();
        code += wordSet.getElement();
        return code;
    }


    public void onFinishDialogBindAndListener(View view){
        Button btnFinishEnd = view.findViewById(R.id.btn_finish_end);
        Button btnFinishWrite = view.findViewById(R.id.btn_finish_write);
        Button btnFinishRestart = view.findViewById(R.id.btn_finish_restart);

        btnFinishEnd.setOnClickListener(dialogClickListener);
        btnFinishWrite.setOnClickListener(dialogClickListener);
        btnFinishRestart.setOnClickListener(dialogClickListener);
    }

    public void onWriteDialogBindAndListenerAndAdapter(View view){
        Button btnWriteRegister = view.findViewById(R.id.btn_write_register);
        Button btnWriteClose = view.findViewById(R.id.btn_write_close);

        lvWriteClient = view.findViewById(R.id.lv_write_client);

        btnWriteRegister.setOnClickListener(dialogClickListener);
        btnWriteClose.setOnClickListener(dialogClickListener);

        lvWriteClient.setAdapter(new DialogWriteAdapter(StudyActivity.this));
        lvWriteClient.setOnItemClickListener(clientListItemClickListener);

    }

    public void onBind(){
        vpStudy = findViewById(R.id.vp_study);

        btnBack = findViewById(R.id.btn_back);
        btnFinish = findViewById(R.id.btn_finish);

        btnBack.setOnClickListener(clickListener);
        btnFinish.setOnClickListener(clickListener);

        tvStudyScore = findViewById(R.id.tv_study_score);
        tvStudyScore.setOnClickListener(clickListener);

        color = getSharedPreferences("setting", Activity.MODE_PRIVATE).getString("point","255,0,0");

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setSpeechRate(0.8f);
                tts.setPitch(1.2f);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();

    }
}
