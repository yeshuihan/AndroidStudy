// IAIDLStudy.aidl
package com.yeshuihan.aidlstudy;
import com.yeshuihan.aidlstudy.DataTest;
parcelable DataTest;
// Declare any non-default types here with import statements

interface IAIDLStudy {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getData();
    void setData(int data);
    void setData2(out DataTest data);
}