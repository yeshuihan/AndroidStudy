package com.yeshuihan.recyclerviewstudy.pictureshow;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yeshuihan.recyclerviewstudy.R;

import java.util.ArrayList;
import java.util.List;

public class PictureShowActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show);

        List<Integer> data = new ArrayList<>();
        data.add(R.drawable.pc1);
        data.add(R.drawable.pc2);
        data.add(R.drawable.pc3);
        data.add(R.drawable.pc4);
        data.add(R.drawable.pc5);
        data.add(R.drawable.pc6);
        data.add(R.drawable.pc7);


        recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new PictureShowLayoutManager());
        PictureShowAdapter adapter = new PictureShowAdapter(data);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new PictureCallBack(15, adapter, data));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
