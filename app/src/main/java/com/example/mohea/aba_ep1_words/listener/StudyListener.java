package com.example.mohea.aba_ep1_words.listener;

public interface StudyListener {

    void studyScoreCheck(int kind, int isCorrect,int now);

    void studyTTS(String word);


}
