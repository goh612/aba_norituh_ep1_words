package com.example.mohea.aba_ep1_words.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.Constant;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.StudyActivity;
import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.adapter.DialogWordsAdapter;
import com.example.mohea.aba_ep1_words.adapter.WordTypeSelectAdapter;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectFragment extends Fragment {

    GridView gvMain;

    int type;
    char element;

    WordSet wordSet;

    AlertDialog dialog;

    GridView gvOptionalWords;
    ListView lvSelectedWords;

    RadioGroup rgPosition;

    DialogWordsAdapter selectedWordsAdapter;

    ArrayList<WordSet> wordList = new ArrayList<>();
    ArrayList<WordSet> selectedList = new ArrayList<>();
    ArrayList<String> selectedWordList = new ArrayList<>();

    SQLiteHelper sqLiteHelper;

    public SelectFragment() {
        // Required empty public constructor

    }

    public void setType(int type){
        this.type = type;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        FrameLayout layout = rootView.findViewById(R.id.lay_select);

        sqLiteHelper = new SQLiteHelper(getContext());

        switch (type){
            case 0:
                layout.setBackgroundResource(R.color.myGreen);
                break;
            case 1:
                layout.setBackgroundResource(R.color.myBlue);
                break;
            case 2:
                layout.setBackgroundResource(R.color.myOrange);
                break;
        }



        gvMain = rootView.findViewById(R.id.gv_main);
        gvMain.setAdapter(new WordTypeSelectAdapter(getContext(), Constant.TYPE[type]));
        gvMain.setOnItemClickListener(listListener);

        return rootView;
    }

    AdapterView.OnItemClickListener listListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()){
                case R.id.gv_main:
                    element = Constant.TYPE_CODE[type][i];

                    settingDialog();
                    dialog.show();
                    settingParams(dialog);

                    break;
                case R.id.gv_optional_words:
                    if(selectedList.size()>=10){
                        Toast.makeText(getContext(), "최대 10개까지 입니다.", Toast.LENGTH_SHORT).show();
                    }else if(selectedWordList.contains(wordList.get(i).getWord())){
                        Toast.makeText(getContext(), "이미 있는 단어입니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        selectedList.add(wordList.get(i));
                        selectedWordList.add(wordList.get(i).getWord());
                        selectedWordsAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.lv_selected_words:
                    selectedList.remove(i);
                    selectedWordsAdapter.notifyDataSetChanged();
                    break;
            }


        }
    };


    public void settingDialog(){
        dialog = new AlertDialog.Builder(getContext()).create();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.clean_back);

        final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.word_select_dialog,null);

        onDialogBindAndListenerAndAdapter(dialogView);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
    }

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int id) {

            if(wordList!=null) {
                wordList.clear();
            }

            int position = 0;

            switch (id){
                case R.id.rb_start:
                    position = 1;
                    break;
                case R.id.rb_center:
                    position = 2;
                    break;
                case R.id.rb_end:
                    position = 3;
                    break;
            }


            wordSet = new WordSet();
            wordSet.setPosition(position);
            wordSet.setType(type);
            wordSet.setElement(element);

            wordList = sqLiteHelper.readData(wordSet,1);

            String result = "";
            for (int i = 0; i < wordList.size(); i++) {
                result += wordList.get(i).getWord() + ",";
            }

            Log.d("여기다 임마!", result);

            gvOptionalWords.setAdapter(new DialogWordsAdapter(getContext(), wordList));
        }
    };


    // ClickListener
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_close:
                    closeDialog();
                    break;
                case R.id.btn_research:
                    if(wordSet==null){
                        Toast.makeText(getContext(), "위치를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    wordList =sqLiteHelper.readData(wordSet,1);
                    gvOptionalWords.setAdapter(new DialogWordsAdapter(getContext(),wordList));
                    break;
                case R.id.btn_start:
                    if(selectedList.size()==0) {
                        Toast.makeText(getContext(), "선택된 단어가 없습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    Intent intentGoStudy = new Intent(getContext(), StudyActivity.class);
                    intentGoStudy.putParcelableArrayListExtra("list",selectedList);
                    getActivity().finish();
                    startActivity(intentGoStudy);
                    break;
            }

        }
    };

    public void closeDialog(){
        dialog.cancel();
        wordList.clear();
        selectedList.clear();
        wordSet = null;
        selectedWordList.clear();
    }

    public void settingParams(AlertDialog dialog){
        ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();

        Point point = new Point();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getSize(point);

        params.width = (int)((point.x)/1.1);
        params.height = (int)((point.y)/1.2);

        this.dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    public void onDialogBindAndListenerAndAdapter(View dialogView){

        rgPosition = dialogView.findViewById(R.id.rg_position);
        rgPosition.setOnCheckedChangeListener(checkedChangeListener);

        // Button
        Button btnClose = dialogView.findViewById(R.id.btn_close);
        Button btnResearch = dialogView.findViewById(R.id.btn_research);
        Button btnStart = dialogView.findViewById(R.id.btn_start);

        btnClose.setOnClickListener(clickListener);
        btnResearch.setOnClickListener(clickListener);
        btnStart.setOnClickListener(clickListener);

        //ListView

        lvSelectedWords = dialogView.findViewById(R.id.lv_selected_words);
        selectedWordsAdapter = new DialogWordsAdapter(getContext(),selectedList);
        lvSelectedWords.setAdapter(selectedWordsAdapter);
        lvSelectedWords.setOnItemClickListener(listListener);


        //GridView
        gvOptionalWords = dialogView.findViewById(R.id.gv_optional_words);
        gvOptionalWords.setOnItemClickListener(listListener);
    }

}