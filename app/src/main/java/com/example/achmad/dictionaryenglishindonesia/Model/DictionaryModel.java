package com.example.achmad.dictionaryenglishindonesia.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by Achmad
 * 16 august 2018
 */

public class DictionaryModel implements Parcelable {
    private int id;
    private String word;
    private String description;

    public DictionaryModel() {

    }

    public DictionaryModel(String word, String description) {
        this.word = word;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.word);
        parcel.writeString(this.description);


    }

    private DictionaryModel(Parcel in) {
        this.id = in.readInt();
        this.word = in.readString();
        this.description = in.readString();

    }

    public static final Creator<DictionaryModel> CREATOR = new Creator<DictionaryModel>() {
        @Override
        public DictionaryModel createFromParcel(Parcel parcel) {
            return new DictionaryModel(parcel);
        }

        @Override
        public DictionaryModel[] newArray(int i) {
            return new DictionaryModel[i];
        }
    };
}
