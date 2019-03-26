package com.example.mohea.aba_ep1_words.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.listener.StudyListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

    WordSet wordSet;
    TextView tvCount;
    int allCount = 0;
    int nowCount = 0;
    int kind = 0;
    int[] colors = new int[3];

    int now;



    StudyListener listener;

    Button btnCorrect;
    Button btnWrong;

    TextView[][] tvWordArray;

    TextView tvPoint;

    RelativeLayout rl;
    CardView cvStudy;

    boolean isPoint = false;

    public StudyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View studyView = inflater.inflate(R.layout.fragment_study, container, false);

        onBind(studyView);
        setWord(wordSet);
        tvPoint = setPoint(wordSet);

        setListener();

        return studyView;
    }

    public void inputSet(WordSet wordSet,int all,int now, StudyListener listener,int kind,String color){
        this.wordSet = wordSet;
        allCount = all;
        nowCount = now+1;
        this.now = now;
        this.listener = listener;
        this.kind = kind;

        String[] colorsStr = color.split(",");
        for(int i=0;i<3;i++){
            colors[i] = Integer.parseInt(colorsStr[i]);
        }



    }

    public void setListener(){
        btnCorrect.setOnClickListener(clickListener);
        btnWrong.setOnClickListener(clickListener);
    }

    public void setWord(WordSet ws){

        String word = ws.getWord();
        for(int i=0;i<word.length();i++){
            for(int j=0;j<3;j++){
                tvWordArray[i][j].setText(String.valueOf(word.charAt(i)));
            }
        }
    }

    public TextView setPoint(WordSet ws){
        TextView tv = null;

        int type = ws.getType();
        int position = ws.getPosition();
        int wordLength = ws.getWord().length();

        switch (position){
            case 1:
                tv = tvWordArray[0][type];
                break;
            case 2:
                int detailPosition = positionSet(ws);
                tv = tvWordArray[detailPosition][type];
                break;
            case 3:
                tv = tvWordArray[wordLength-1][type];
                break;
        }
        return tv;
    }

    public int positionSet(WordSet ws){
        String word = ws.getWord();
        String code = ws.getCode();
        String subCode = code.substring(6,word.length()*4-3);
        String strArray[] = subCode.split("2");
        String matchStr = null;

        int pointPosition = -1;


        switch (ws.getType()){
            case 0:
                matchStr = ws.getElement()+"..";
                break;
            case 1:
                matchStr = "."+ws.getElement()+".";
                break;
            case 2:
                matchStr = ".."+ws.getElement();
                break;
        }

        for(int i=0;i<strArray.length;i++){
            if(strArray[i].matches(matchStr)){
                pointPosition = i;
            }
        }

        return pointPosition+1;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_correct:
                    listener.studyScoreCheck(kind,1,now);
                    break;
                case R.id.btn_wrong:
                    listener.studyScoreCheck(kind,0,now);
                    break;
            }
        }
    };

    View.OnClickListener textColorChangeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(isPoint){
                tvPoint.setTextColor(Color.BLACK);
                isPoint = false;
            }else {
                tvPoint.setTextColor(Color.rgb(colors[0],colors[1],colors[2]));
                isPoint = true;
                listener.studyTTS(wordSet.getWord());
            }
        }
    };


    public void onBind(View studyView){
        tvCount = studyView.findViewById(R.id.tv_count);
        tvCount.setText(nowCount+"/"+allCount);

        btnCorrect = studyView.findViewById(R.id.btn_correct);
        btnWrong = studyView.findViewById(R.id.btn_wrong);

        tvWordArray = new TextView[6][3];
        tvWordArray[0][0] = studyView.findViewById(R.id.tv_study_1_1);
        tvWordArray[0][1] = studyView.findViewById(R.id.tv_study_1_2);
        tvWordArray[0][2] = studyView.findViewById(R.id.tv_study_1_3);
        tvWordArray[1][0] = studyView.findViewById(R.id.tv_study_2_1);
        tvWordArray[1][1] = studyView.findViewById(R.id.tv_study_2_2);
        tvWordArray[1][2] = studyView.findViewById(R.id.tv_study_2_3);
        tvWordArray[2][0] = studyView.findViewById(R.id.tv_study_3_1);
        tvWordArray[2][1] = studyView.findViewById(R.id.tv_study_3_2);
        tvWordArray[2][2] = studyView.findViewById(R.id.tv_study_3_3);
        tvWordArray[3][0] = studyView.findViewById(R.id.tv_study_4_1);
        tvWordArray[3][1] = studyView.findViewById(R.id.tv_study_4_2);
        tvWordArray[3][2] = studyView.findViewById(R.id.tv_study_4_3);
        tvWordArray[4][0] = studyView.findViewById(R.id.tv_study_5_1);
        tvWordArray[4][1] = studyView.findViewById(R.id.tv_study_5_2);
        tvWordArray[4][2] = studyView.findViewById(R.id.tv_study_5_3);
        tvWordArray[5][0] = studyView.findViewById(R.id.tv_study_6_1);
        tvWordArray[5][1] = studyView.findViewById(R.id.tv_study_6_2);
        tvWordArray[5][2] = studyView.findViewById(R.id.tv_study_6_3);

        rl = studyView.findViewById(R.id.lay_study_word);
        rl.setOnClickListener(textColorChangeListener);

        cvStudy = studyView.findViewById(R.id.cv_study);
        cvStudy.setOnClickListener(textColorChangeListener);

    }

}
