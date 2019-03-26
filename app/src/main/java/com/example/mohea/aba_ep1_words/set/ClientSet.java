package com.example.mohea.aba_ep1_words.set;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class ClientSet implements Parcelable {
    long id;
    String name;
    String gender;
    int age;
    String note;

    public ClientSet() {
    }

    protected ClientSet(Parcel in) {
        id = in.readLong();
        name = in.readString();
        gender = in.readString();
        age = in.readInt();
        note = in.readString();
    }

    public static final Creator<ClientSet> CREATOR = new Creator<ClientSet>() {
        @Override
        public ClientSet createFromParcel(Parcel in) {
            return new ClientSet(in);
        }

        @Override
        public ClientSet[] newArray(int size) {
            return new ClientSet[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeInt(age);
        parcel.writeString(note);
    }
}
