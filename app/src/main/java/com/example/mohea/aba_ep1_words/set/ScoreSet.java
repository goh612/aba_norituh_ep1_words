package com.example.mohea.aba_ep1_words.set;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreSet implements Parcelable{

    long userId;
    long scoreId;
    String score;
    String subCode;
    String data;

    public ScoreSet() {
    }

    protected ScoreSet(Parcel in) {
        userId = in.readLong();
        scoreId = in.readLong();
        score = in.readString();
        subCode = in.readString();
        data = in.readString();
    }

    public static final Creator<ScoreSet> CREATOR = new Creator<ScoreSet>() {
        @Override
        public ScoreSet createFromParcel(Parcel in) {
            return new ScoreSet(in);
        }

        @Override
        public ScoreSet[] newArray(int size) {
            return new ScoreSet[size];
        }
    };

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userId);
        parcel.writeLong(scoreId);
        parcel.writeString(score);
        parcel.writeString(subCode);
        parcel.writeString(data);
    }
}



