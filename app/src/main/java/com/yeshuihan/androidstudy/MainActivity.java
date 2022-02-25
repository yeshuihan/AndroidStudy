package com.yeshuihan.androidstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.yeshuihan.aidlstudy.DataTest;
import com.yeshuihan.aidlstudy.IAIDLStudy;
import com.yeshuihan.aidlstudy.StudyService;
import com.yeshuihan.recyclerviewstudy.RecyclerViewStudyActivity;
import com.yeshuihan.recyclerviewstudy.pictureshow.PictureShowActivity;

import java.security.Permission;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(this, RecyclerViewStudyActivity.class));
//        startActivity(new Intent(this, PictureShowActivity.class));
        Test.INSTANCE.test();


        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                Log.i("fzw", "subscribe: currentThread:" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
            }
        }).subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Throwable {
                        Log.i("fzw", "flatMap:" + integer + ", currentThread:" + Thread.currentThread().getName());
                        return Observable.just(integer);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Throwable {
                        Log.i("fzw", "map:" + integer + ", currentThread:" + Thread.currentThread().getName());
                        return integer;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Throwable {
                Log.i("fzw", "accept:" + integer + ", currentThread:" + Thread.currentThread().getName());
            }
        });
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);

        Log.i("fzw", "1111++" + activityManager.getMemoryClass());

//        requestPermission();
    }



    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;
    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {  //判断是否需要请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
            } else {
                // 这里权限已经被用户拒绝，并且再申请系统也不会弹出申请框了。
                // 这时需要自己弹框提示用户手动开启权限
            }
        } else {
            doSomeAfterHavePermission();
        }
    }

    private void doSomeAfterHavePermission() {
        //做一些有权限之后的事情
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSomeAfterHavePermission();
                } else {
                    //用户拒绝了权限
                }
            }
        } else {
            //其他请求的返回结果
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IAIDLStudy study = IAIDLStudy.Stub.asInterface(service);
            try {
                DataTest data =new DataTest();
                study.setData2(data);

                Log.i("fzw", "" + data);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void click(View view) {
        Intent intent =new Intent(this, AActivity.class);

        startActivity(intent);
    }
}