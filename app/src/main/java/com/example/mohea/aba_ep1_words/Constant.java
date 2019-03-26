package com.example.mohea.aba_ep1_words;

import android.util.Log;

import com.example.mohea.aba_ep1_words.set.WordSet;

import java.util.ArrayList;

public class Constant {

    public final static char[] CONSONANTS = {'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    public final static char[] VOWELS = {'ㅏ','ㅐ','ㅑ','ㅒ','ㅓ','ㅔ','ㅕ','ㅖ','ㅗ','ㅘ','ㅙ','ㅚ','ㅛ','ㅜ','ㅝ','ㅞ','ㅟ','ㅠ','ㅡ','ㅢ','ㅣ'};
    public final static char[] SUPPORTS = {' ','ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ','ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ','ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
    public final static char[] SUPPORTS_NO = {'ㄱ','ㄲ','ㄳ','ㄴ','ㄵ','ㄶ','ㄷ','ㄹ','ㄺ','ㄻ','ㄼ','ㄽ','ㄾ','ㄿ','ㅀ','ㅁ','ㅂ','ㅄ','ㅅ','ㅆ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};

    public final static char[] WORD_LENGTH_SUB = {' ','a','b','c','d','e','f'};
    public final static char[] CONSONANTS_SUB = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s'};
    public final static char[] VOWELS_SUB = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u'};
    public final static char[] SUPPORTS_SUB = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','8','9'};

    public final static char[] SUPPORTS_NO_SUB = {'b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','8','9'};


    public final static String TABLE_WORD = "wordstbl";
    public final static String TABLE_CLIENT = "clientstbl";
    public final static String TABLE_SCORE = "scoretbl";

    public final static String GRAPH_POSITION_START = "처음";
    public final static String GRAPH_POSITION_CENTER = "중간";
    public final static String GRAPH_POSITION_END = "끝";




    public final static String REQUEST_POSITON = "위치를 입력해 주세요.";


    public final static String WORD_TYPE = "type";
    public final static String WORD_POSITION = "position";
    public final static String WORD_ELEMENT = "element";
    public final static String WORD_WORD = "word";
    public final static String WORD_CODE = "code";
    public final static String WORD_STATE = "state";

    public final static String CLIENT_ID ="id";
    public final static String CLIENT_NAME ="name";
    public final static String CLIENT_GENDER ="gender";
    public final static String CLIENT_AGE ="age";
    public final static String CLIENT_NOTE ="note";

    public final static String SCORE_USER_ID = "userid";
    public final static String SCORE_SCORE_ID = "scoreid";
    public final static String SCORE_SCORE = "score";
    public final static String SCORE_SUBCODE = "subcode";
    public final static String SCORE_DATA = "data";





    public final static char[][] TYPE = {CONSONANTS,VOWELS,SUPPORTS_NO};
    public final static char[][] TYPE_CODE = {CONSONANTS_SUB,VOWELS_SUB,SUPPORTS_NO_SUB};





}
