package com.example.mohea.aba_ep1_words.set;

import android.os.Parcel;
import android.os.Parcelable;

public class WordSet implements Parcelable {

    int type;
    int position;
    char element;
    String word;
    String code;

    public WordSet() {
    }

    public WordSet(int type, int position, char element) {
        this.type = type;
        this.position = position;
        this.element = element;
    }

    protected WordSet(Parcel in) {
        type = in.readInt();
        position = in.readInt();
        element = (char) in.readInt();
        word = in.readString();
        code = in.readString();
    }

    public static final Creator<WordSet> CREATOR = new Creator<WordSet>() {
        @Override
        public WordSet createFromParcel(Parcel in) {
            return new WordSet(in);
        }

        @Override
        public WordSet[] newArray(int size) {
            return new WordSet[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getElement() {
        return element;
    }

    public void setElement(char element) {
        this.element = element;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(type);
        parcel.writeInt(position);
        parcel.writeInt((int) element);
        parcel.writeString(word);
        parcel.writeString(code);
    }
}
