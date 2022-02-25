package com.yeshuihan.aidlstudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import com.yeshuihan.aidlstudy.DataTest;

import java.util.List;

public class StudyService  extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new StudyImpl();
    }



    private class StudyImpl extends IAIDLStudy.Stub {

        @Override
        public int getData() throws RemoteException {
            Log.i("fzw111", "" + Thread.currentThread().getName());
            Log.i("fzw111", "" + System.getProperty("http.agent"));
            return 0;
        }

        @Override
        public void setData(int data) throws RemoteException {

        }

        @Override
        public void setData2(DataTest data) throws RemoteException {

        }


    }




}
