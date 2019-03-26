package com.example.mohea.aba_ep1_words;

import android.util.Log;

import com.example.mohea.aba_ep1_words.set.CheckSet;
import com.example.mohea.aba_ep1_words.set.WordSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WordParser {

    public WordParser() {
    }

    // 단어를 읽어서 코드화 하는 메소드
    public String parsingWordToCode(String word){
        String code = "";

        int wordLength = word.length();
        String startC = "";
        String middleC = "";
        String endC = "";

        code+=Constant.WORD_LENGTH_SUB[wordLength];

        for(int i=0;i<wordLength;i++) {

            char piece = word.charAt(i);
            int temp = piece - 44032;
            int support = temp % 28;
            int vowel = ((temp - support) / 28) % 21;
            int consonant = (((temp - support) / 28) - vowel) / 21;

            char[] code_piece = {Constant.CONSONANTS_SUB[consonant],Constant.VOWELS_SUB[vowel],Constant.SUPPORTS_SUB[support]};
            String codeC = String.valueOf(code_piece);
            if(i==0){
                startC = (1+codeC);
            }else if(i==wordLength-1){
                endC = (3+codeC);
            }else{
                middleC+=(2+codeC);
            }
        }
        code += (startC+middleC+endC);
        return code;
    }

    // 코드와 요소를 가지고 위치를 알아내는 메소
    public int positionParsing(WordSet wordSet){

        String code = wordSet.getCode();
        char element = wordSet.getElement();
        int centerPosition = wordSet.getWord().length()*4-3;

        int position = 0;

        ArrayList<String> subcodes = new ArrayList<>();

        String startCode = code.substring(1,5);
        String centerCode = "";
        String endCode = "";
        if(code.contains("2")) {
            centerCode = code.substring(5, centerPosition);
            endCode = code.substring(centerPosition);
        }else if(code.contains("3")){
            endCode = code.substring(5);
        }

        String matchStr = null;

        switch (wordSet.getType()){
            case 0:
                matchStr = "[1-3]"+element+"..";
                break;
            case 1:
                matchStr = "[1-3]."+element+".";
                break;
            case 2:
                matchStr = "[1-3].."+element;
                break;
        }

        if(startCode.matches(matchStr)){
            position =1;
        }else if(endCode.matches(matchStr)){
            position =3;
        }else{
            position=2;
        }

        return position;
    }

    // 부분 코드로 단어 리스트를 GLOB 로 조회할 수 있는 와일드카드로 변환하는 메소드
    public String subcodeParsing(String subcode){
        int type = Integer.parseInt(subcode.charAt(1)+"");
        char elementPiece = subcode.charAt(2);

        char[] elementsK = null;
        char[] elementsE = null;
        String element = "";

        switch (type){
            case 0:
                elementsK = Constant.CONSONANTS;
                elementsE = Constant.CONSONANTS_SUB;
                element += "자음 ";
                break;
            case 1:
                elementsK = Constant.VOWELS;
                elementsE = Constant.VOWELS_SUB;
                element += "모음 ";
                break;
            case 2:
                elementsK = Constant.SUPPORTS_NO;
                elementsE = Constant.SUPPORTS_NO_SUB;
                element += "받침 ";
                break;
        }


        for(int j=0;j< elementsK.length;j++){
            if(elementsE[j]==elementPiece){
                element += elementsK[j];
            }
        }

        return element;
    }

    // 코드 배열로 문자 배열의 매칭 값을 가져오는 메소드
    public char[] elementParsing(int type,char[] element_sub) {

        char[] elementsK = null;
        char[] elementsE = null;
        char[] element = new char[element_sub.length];

        switch (type) {
            case 0:
                elementsK = Constant.CONSONANTS;
                elementsE = Constant.CONSONANTS_SUB;
                break;
            case 1:
                elementsK = Constant.VOWELS;
                elementsE = Constant.VOWELS_SUB;
                break;
            case 2:
                elementsK = Constant.SUPPORTS_NO;
                elementsE = Constant.SUPPORTS_NO_SUB;
                break;
        }


        for (int i = 0; i < element_sub.length; i++) {

            char elementPiece = element_sub[i];

            for (int j = 0; j < elementsK.length; j++) {
                if (elementsE[j] == elementPiece) {
                    element[i] = elementsK[j];
                }
            }
        }

        return element;
    }





    public ArrayList<WordSet> jsonParsing(String json){
        ArrayList<WordSet> wordSetArrayList = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);

            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                WordSet wordSet = new WordSet();
                wordSet.setType(jsonObject.getInt("type"));
                wordSet.setPosition(jsonObject.getInt("position"));
                wordSet.setElement(jsonObject.getString("element").charAt(0));
                wordSet.setWord(jsonObject.getString("word"));
                wordSet.setCode(jsonObject.getString("code"));
                wordSetArrayList.add(wordSet);

                Log.d("JSONParsing",wordSet.getElement()+"/"+jsonObject.get("element"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return wordSetArrayList;
    }

    public ArrayList<CheckSet> checkParsing(String checkData){
        ArrayList<CheckSet> checkSets = new ArrayList<>();

        String[] datas = checkData.split(",");

        for(int i=0;i<datas.length;i++){

            String data = datas[i];
            int beforeS = data.indexOf('s');
            int beforeR = data.indexOf('r');

            int num = Integer.parseInt(data.substring(0,beforeS));
            String score = data.substring(beforeS+1,beforeR);
            String rate = data.substring(beforeR+1);

            CheckSet newCheckSet = new CheckSet(num,score,rate);

            checkSets.add(newCheckSet);
        }

        return checkSets;
    }

}
