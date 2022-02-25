package com.yeshuihan.aidlstudy;

import android.os.Parcel;
import android.os.Parcelable;


public class DataTest implements Parcelable {
    public DataTest(){

    }

    protected DataTest(Parcel in) {
    }

    public static final Creator<DataTest> CREATOR = new Creator<DataTest>() {
        @Override
        public DataTest createFromParcel(Parcel in) {
            return new DataTest(in);
        }

        @Override
        public DataTest[] newArray(int size) {
            return new DataTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public void readFromParcel(Parcel parcel) {

    }

}
