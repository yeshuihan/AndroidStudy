package com.yeshuihan.androidstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yeshuihan.recyclerviewstudy.RecyclerViewStudyActivity;
import com.yeshuihan.recyclerviewstudy.pictureshow.PictureShowActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, RecyclerViewStudyActivity.class));
//        startActivity(new Intent(this, PictureShowActivity.class));
    }
}