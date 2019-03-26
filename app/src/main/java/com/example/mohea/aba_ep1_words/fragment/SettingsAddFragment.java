package com.example.mohea.aba_ep1_words.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsAddFragment extends Fragment {

    EditText etWordAdd;
    Button btnWordAdd;


    public SettingsAddFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_add, container, false);

        onBindAndListener(view);

        return view;
    }

    public void onBindAndListener(View view){
        etWordAdd = view.findViewById(R.id.et_word_add);
        btnWordAdd = view.findViewById(R.id.btn_word_add);
        btnWordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputData = etWordAdd.getText().toString();

                if(TextUtils.isEmpty(inputData)){
                    Toast.makeText(getContext(), "단어를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String datas[] = inputData.split(",");
                String answer = "";

                for(int i=0;i<datas.length;i++){

                    if(i!=0){
                        answer+="\n";
                    }

                    String word = datas[i].trim();

                    String pattern = "^[가-힣]*$";
                    boolean matchingResult = Pattern.matches(pattern,word);

                    // -1 : 글자수 초과/ -2 : 특수문자,숫자,영어,단문자 포함/
                    if(word.length()>6){
                        Toast.makeText(getContext(), "단어는 최대 6자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(!matchingResult){
                        Toast.makeText(getContext(), "단어에 특수문자,영어,숫자 등이 포함되어있습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(word.equals("")){
                        Toast.makeText(getContext(), "공백은 저장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int inputResult = new SQLiteHelper(getContext()).inputData(word);

                    switch (inputResult){
                        case 1:
                            answer += ("'"+word+"' 입력 성공");
                            etWordAdd.setText("");
                            break;
                        case 0:
                            answer += ("'"+word+"' 입력 실패");
                            break;
                        case -1:
                            answer += ("'"+word+"' 는 이미 존재하는 단어입니다.");
                            break;
                        case -2:
                            answer += ("'"+word+"' 은 이미 존재하는 단어입니다.");
                            break;
                    }
                }

                Toast.makeText(getContext(), answer, Toast.LENGTH_SHORT).show();
                Log.d("단어입력 테스트",answer);
            }
        });
    }

}
