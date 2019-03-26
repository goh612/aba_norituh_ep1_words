package com.example.mohea.aba_ep1_words.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.Check;
import com.example.mohea.aba_ep1_words.GraphView;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.adapter.ScoreGraphElementAdapter;
import com.example.mohea.aba_ep1_words.listener.ScoreGraphElementListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.GraphSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;
import com.github.mikephil.charting.charts.LineChart;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreGraphFragment extends Fragment {


    ArrayList<ScoreSet> graphScoreSetArrayList;

    // 0 : 처음
    // 1 : 중간
    // 2 : 끝
    ArrayList<GraphSet>[] scoreLists = new ArrayList[3];

    LineChart lcScoreGraph;

    RadioGroup rgScoreGraph;

    CheckBox[] cbScoreGraphPosition = new CheckBox[3];

    RecyclerView rvScoreGraph;

    int position = 7;
    ClientSet clientSet;

    GraphView graphView;

    char[][] elements = new char[3][];

    SQLiteHelper sqLiteHelper;


    public ScoreGraphFragment() {
        // Required empty public constructor
        listReset();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score_graph,null);
        sqLiteHelper = new SQLiteHelper(getContext());
        setElementList(sqLiteHelper.readScoreSubcode(clientSet.getId(),0));
        onBindAndListener(view);
        graphDisplay();
        return view;
    }



    RadioGroup.OnCheckedChangeListener rgCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {
            elementDisplay(id);
        }
    };

    CompoundButton.OnCheckedChangeListener cbCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            boolean isStart = cbScoreGraphPosition[0].isChecked();
            boolean isCenter = cbScoreGraphPosition[1].isChecked();
            boolean isEnd = cbScoreGraphPosition[2].isChecked();

            position = new Check().checkPosition(isStart,isCenter,isEnd);

        }
    };

    public void graphDisplay(){
        graphView.setGraph(scoreLists,position,clientSet.getName());
    }

    ScoreGraphElementListener scoreGraphElementListener = new ScoreGraphElementListener() {
        @Override
        public void onClick(char element,int type) {
            graphScoreSetArrayList = sqLiteHelper.readScoreGraphList(element,type,clientSet.getId());
            scoreSetParsing();
            graphDisplay();
        }
    };

    public void elementDisplay(int id){

        ScoreGraphElementAdapter adapter = new ScoreGraphElementAdapter(getContext(),scoreGraphElementListener);

        int type = -1;

        switch (id) {
            case R.id.rb_score_graph_consonant:
                type = 0;
                break;
            case R.id.rb_score_graph_vowel:
                type = 1;
                break;
            case R.id.rb_score_graph_support:
                type = 2;
                break;
        }

        String result = "";
        for(int i=0;i<elements[type].length;i++){
            result += elements[type][i]+",";
        }

        Log.d("데이터 확인~",result);

        adapter.setType(type,elements[type]);
        rvScoreGraph.setAdapter(adapter);
    }

    public void scoreSetParsing(){

        listReset();

        for(int i=0;i<graphScoreSetArrayList.size();i++){
            ScoreSet scoreSet = graphScoreSetArrayList.get(i);
            String[] scorePiece = scoreSet.getScore().split(",");

            for(int j=0;j<3;j++){

                // 점수의 앞점수,뒷점수
                String[] scores = scorePiece[j+1].split("/");

                int correctScore = Integer.parseInt(scores[0]); //맞춘 점수
                int allScore = Integer.parseInt(scores[1]);     //전체 점수

                if(allScore==0){
                    continue;
                }

                float perScore = correctScore*100/allScore;

                Log.d("여기를 확인하세요~",j+"번째는 "+perScore+"% 입니다.");

                GraphSet graphSet = new GraphSet();
                graphSet.setScore(perScore);
                graphSet.setScoreId(scoreSet.getScoreId());
                scoreLists[j].add(graphSet);
            }
        }
    }

    public void setElementList(ArrayList<String> arrayList){
        ArrayList<Character>[] cArray = new ArrayList[3];
        cArray[0] = new ArrayList<>();
        cArray[1] = new ArrayList<>();
        cArray[2] = new ArrayList<>();

        for(int i=0;i<arrayList.size();i++){
            String subCode = arrayList.get(i);

            int type = Integer.parseInt(subCode.substring(1,2));
            char element = subCode.charAt(2);

            if(!cArray[type].contains(element)){
                cArray[type].add(element);
            }
        }

        for(int i=0;i<elements.length;i++) {

            elements[i] = new char[cArray[i].size()];

            for (int j = 0; j < cArray[i].size(); j++) {
                elements[i][j] = cArray[i].get(j);
            }
            Arrays.sort(elements[i]);
        }



    }

    public void listReset(){
        for(int i=0;i<3;i++){
            if(scoreLists[i]==null) {
                scoreLists[i] = new ArrayList<>();
            }else{
                scoreLists[i].clear();
            }
        }
    }

    public void onBindAndListener(View view){
        lcScoreGraph = view.findViewById(R.id.lc_score_graph);

        rgScoreGraph = view.findViewById(R.id.rg_score_graph);
        cbScoreGraphPosition[0] = view.findViewById(R.id.cb_score_graph_position1);
        cbScoreGraphPosition[1] = view.findViewById(R.id.cb_score_graph_position2);
        cbScoreGraphPosition[2] = view.findViewById(R.id.cb_score_graph_position3);

        for(int i=0;i<cbScoreGraphPosition.length;i++){
            cbScoreGraphPosition[i].setChecked(true);
            cbScoreGraphPosition[i].setOnCheckedChangeListener(cbCheckedChangeListener);
        }

        rgScoreGraph.setOnCheckedChangeListener(rgCheckedChangeListener);

        rvScoreGraph = view.findViewById(R.id.rv_score_graph);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvScoreGraph.setLayoutManager(linearLayoutManager);

        graphView = new GraphView(lcScoreGraph,getActivity());
    }

    public void setData(ClientSet clientSet){
        this.clientSet = clientSet;
    }

}
