package com.yeshuihan.androidstudy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityCreated");
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityStarted");
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityResumed");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityPaused");
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.i("fzw", activity.getClass().getSimpleName() + ":onActivityDestroyed");
            }
        });


    }
}
