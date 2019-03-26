package com.example.mohea.aba_ep1_words.sql;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.Constant;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.WordParser;
import com.example.mohea.aba_ep1_words.set.WordSet;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SQLiteHelper extends SQLiteOpenHelper {

    WordParser wordParser;

    public SQLiteHelper(@Nullable Context context) {
        super(context, "WordsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createWordsTable = "CREATE TABLE IF NOT EXISTS wordstbl(word varchar(25) primary key, code varchar(30), state varchar(3))";
        String createClientsTable = "CREATE TABLE IF NOT EXISTS clientstbl(id bigint primary key, name varchar(30), gender varchar(3), age tinyint, note text)";
        String createScoreTable = "CREATE TABLE IF NOT EXISTS scoretbl(scoreid bigint primary key,userid bigint, score varchar(40), subcode varchar(10), data text)";
        sqLiteDatabase.execSQL(createWordsTable);
        sqLiteDatabase.execSQL(createClientsTable);
        sqLiteDatabase.execSQL(createScoreTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // 초기 단어 데이터를 읽어와 SQL 에 입력하는 메소드
    public boolean settingData(InputStream inputBase){
        SQLiteDatabase db = getWritableDatabase();
        Scanner scanner = new Scanner(inputBase,"UTF-8");
        while(scanner.hasNextLine()){
            String word = scanner.nextLine();
            String code = new WordParser().parsingWordToCode(word);
            try {
                db.execSQL("INSERT INTO wordstbl VALUES('" + word + "','" + code + "','o')");
            }catch (SQLiteConstraintException e){
                e.printStackTrace();
            }
        }
        return true;
    }



    public int inputData(String word){
        // return값
        // 1: 성공 / 0 : 실패(오류) / -1,-2 : 같은 단어 있음(은/는 구분)
       String code = new WordParser().parsingWordToCode(word);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT code FROM wordstbl WHERE code = '"+code+"'";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            char lastChar = code.charAt(code.length()-1);
            if(lastChar=='a'){
                return -1;
            }else{
                return -2;
            }
        }

       try{
           db.execSQL("INSERT INTO wordstbl VALUES('"+word+"','"+code+"','c')");
           return 1;
       }catch (SQLiteConstraintException e){
           e.printStackTrace();
           return 0;
       }

    }

    // SQLite 에서 데이터를 읽어와서 단어리스트를 만드는 메소드
    // 매개변수 / element : 문자 / type : 자음,모음,받침 / position : 글자 위치(처음,중간,끝)
    public ArrayList<WordSet> readData(WordSet wordSet,int kind){

        SQLiteDatabase db = getReadableDatabase();
        ArrayList<WordSet> list = new ArrayList<>();

        int type = wordSet.getType();
        int position = wordSet.getPosition();
        char element = wordSet.getElement();

        String globResourse=setGlobCode(element,type,position);

        String sql;
        if(kind==1) {
            sql = "SELECT * FROM wordstbl WHERE code GLOB '*" + globResourse + "*' ORDER BY random() limit 20";
        }else{
            sql = "SELECT * FROM wordstbl WHERE code GLOB '*" + globResourse + "*' ORDER BY random() limit 10";
        }

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();

            do{
                WordSet newWordSet = new WordSet();

                String code = cursor.getString(cursor.getColumnIndex("code"));

                newWordSet.setWord(cursor.getString(cursor.getColumnIndex("word")));
                newWordSet.setCode(code);
                newWordSet.setType(type);
                newWordSet.setElement(element);
                if(position>3) {
                    newWordSet.setPosition(new WordParser().positionParsing(newWordSet));
                }else{
                    newWordSet.setPosition(position);
                }


                list.add(newWordSet);
            }while (cursor.moveToNext());

         return list;
        }else{
            return new ArrayList<>();
        }
    }

    public boolean addClient(ClientSet clientSet){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id",System.currentTimeMillis());
        contentValues.put("name",clientSet.getName());
        contentValues.put("gender",clientSet.getGender());
        contentValues.put("age",clientSet.getAge());

        long result = db.insert("clientstbl",null,contentValues);

        return result != -1;
    }

    public ArrayList<ClientSet> getClientList(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ClientSet> list= new ArrayList<>();

        String sql = "SELECT * FROM clientstbl";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount()>0){

            cursor.moveToFirst();
            do{
                ClientSet newClientSet = new ClientSet();

                newClientSet.setId(cursor.getLong(cursor.getColumnIndex("id")));
                newClientSet.setName(cursor.getString(cursor.getColumnIndex("name")));
                newClientSet.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                newClientSet.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                newClientSet.setNote(cursor.getString(cursor.getColumnIndex("note")));

                list.add(newClientSet);
            }while (cursor.moveToNext());

        }else{
            return new ArrayList<>();
        }
        return list;
    }

    public boolean updateClientNote(ClientSet clientSet){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.CLIENT_NOTE,clientSet.getNote());

        int result = db.update(Constant.TABLE_CLIENT,contentValues,Constant.CLIENT_ID+"=?"
                ,new String[]{String.valueOf(clientSet.getId())});

        return result==1;
    }

    public boolean deleteClient(long id){
        SQLiteDatabase db = getWritableDatabase();

        String[] deleteId = new String[]{String.valueOf(id)};

        int result = db.delete(Constant.TABLE_CLIENT,Constant.CLIENT_ID+"=?",deleteId);
        db.delete(Constant.TABLE_SCORE,Constant.SCORE_USER_ID+"=?",deleteId);

        return result==1;
    }



    public boolean writeScore(ScoreSet scoreSet){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constant.SCORE_USER_ID,scoreSet.getUserId());
        contentValues.put(Constant.SCORE_SCORE_ID,scoreSet.getScoreId());
        contentValues.put(Constant.SCORE_SCORE, scoreSet.getScore());
        contentValues.put(Constant.SCORE_SUBCODE, scoreSet.getSubCode());
        contentValues.put(Constant.SCORE_DATA, scoreSet.getData());

        long result = db.insert(Constant.TABLE_SCORE, null, contentValues);

        return result != -1;
    }

    public ArrayList<ScoreSet> readScore(long userId){
        ArrayList<ScoreSet> scoreSetArrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String readScoreSql = "SELECT * FROM scoretbl WHERE userid = "+userId;
        Cursor cursor = db.rawQuery(readScoreSql,null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();

            do {
                ScoreSet scoreSet = new ScoreSet();
                scoreSet.setUserId(cursor.getLong(cursor.getColumnIndex("userid")));
                scoreSet.setScoreId(cursor.getLong(cursor.getColumnIndex("scoreid")));
                scoreSet.setSubCode(cursor.getString(cursor.getColumnIndex("subcode")));
                scoreSet.setScore(cursor.getString(cursor.getColumnIndex("score")));
                scoreSet.setData(cursor.getString(cursor.getColumnIndex("data")));

                scoreSetArrayList.add(scoreSet);

            } while (cursor.moveToNext());

            return scoreSetArrayList;
        }else {
            return new ArrayList<>();
        }
    }

    public ArrayList<String> readScoreSubcode(long userId,int code){
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String readScoreSql = "SELECT subcode FROM scoretbl WHERE userid = "+userId;
        Cursor cursor = db.rawQuery(readScoreSql,null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();

            do {
               arrayList.add(cursor.getString(cursor.getColumnIndex("subcode")));
            } while (cursor.moveToNext());

            return arrayList;
        }else {
            return new ArrayList<>();
        }
    }



    public boolean deleteScore(long scoreId){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete("scoretbl","scoreid=?",new String[]{String.valueOf(scoreId)});

        return result==1;
    }


    public String setGlobCode(char element,int type,int position){
        String globResourse="";
        String subResourse = "";

        for(int i=0;i<3;i++){
            if(i==type){
                subResourse += String.valueOf(element);
            }else{
                subResourse += "?";
            }
        }

        if(position==4){
            globResourse = "[1-2]"+subResourse;
        }else if(position==5){
            globResourse = "[1-3]"+subResourse+"*' and not code glob '*2"+subResourse;
        }else if(position==6){
            globResourse = "[2-3]"+subResourse;
        }else if(position==7){
            globResourse = "[1-3]"+subResourse;
        }else{
            globResourse = String.valueOf(position)+subResourse;
        }

        return globResourse;
    }


    public ArrayList<ScoreSet> readScoreGraphList(char element,int type,long userId){

        ArrayList<ScoreSet> scoreSetArrayList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String readScoreSql = "SELECT * FROM scoretbl WHERE userid="+userId+" AND subcode GLOB '?"+type+element+"'";

        Cursor cursor = db.rawQuery(readScoreSql,null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();


            do {
                ScoreSet scoreSet = new ScoreSet();
                scoreSet.setUserId(cursor.getLong(cursor.getColumnIndex("userid")));
                scoreSet.setScoreId(cursor.getLong(cursor.getColumnIndex("scoreid")));
                scoreSet.setSubCode(cursor.getString(cursor.getColumnIndex("subcode")));
                scoreSet.setScore(cursor.getString(cursor.getColumnIndex("score")));
                scoreSet.setData(cursor.getString(cursor.getColumnIndex("data")));

                scoreSetArrayList.add(scoreSet);

            } while (cursor.moveToNext());

            return scoreSetArrayList;
        }else {
            return new ArrayList<>();
        }
    }

    public ScoreSet readScoreSet(long scoreId){

        SQLiteDatabase db = getReadableDatabase();

        String readScoreSql = "SELECT * FROM scoretbl WHERE scoreid = "+scoreId;
        Cursor cursor = db.rawQuery(readScoreSql,null);

        if(cursor.getCount()>0) {
            cursor.moveToFirst();

            ScoreSet scoreSet = new ScoreSet();
            scoreSet.setUserId(cursor.getLong(cursor.getColumnIndex("userid")));
            scoreSet.setScoreId(cursor.getLong(cursor.getColumnIndex("scoreid")));
            scoreSet.setSubCode(cursor.getString(cursor.getColumnIndex("subcode")));
            scoreSet.setScore(cursor.getString(cursor.getColumnIndex("score")));
            scoreSet.setData(cursor.getString(cursor.getColumnIndex("data")));

            return scoreSet;
        }else {
            return new ScoreSet();
        }
    }


    public boolean resetWordsList(){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.WORD_STATE,"o");
        db.update(Constant.TABLE_WORD,contentValues,Constant.WORD_STATE+"=?",new String[]{"x"});
        int result = db.delete(Constant.TABLE_WORD,Constant.WORD_STATE+"=?",new String[]{"c"});

        return result>0;

    }

    public boolean resetClientList(){

        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(Constant.TABLE_CLIENT,null,null);
        db.delete(Constant.TABLE_SCORE,null,null);

        return result>0;
    }

    public boolean resetAll(){
        boolean wordResult = resetWordsList();
        boolean clientResult = resetClientList();
        return wordResult||clientResult;

    }


}
