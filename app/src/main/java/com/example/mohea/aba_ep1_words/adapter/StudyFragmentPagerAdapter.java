package com.example.mohea.aba_ep1_words.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mohea.aba_ep1_words.set.WordSet;
import com.example.mohea.aba_ep1_words.fragment.StudyFragment;
import com.example.mohea.aba_ep1_words.listener.StudyListener;

import java.util.ArrayList;

public class StudyFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<WordSet> wordList;
    StudyListener listener;
    String color;

    public StudyFragmentPagerAdapter(FragmentManager fm, ArrayList<WordSet> wordList, StudyListener listener,String color) {
        super(fm);
        this.wordList = wordList;
        this.listener = listener;
        this.color = color;
    }

    @Override
    public Fragment getItem(int i) {
        StudyFragment studyFragment = new StudyFragment();

        WordSet wordSet = wordList.get(i);
        studyFragment.inputSet(wordSet,wordList.size(),i,listener,wordSet.getPosition(),color);
        return studyFragment;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }
}
