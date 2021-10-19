package com.yeshuihan.recyclerviewstudy;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yeshuihan.materialstudy.CustomAdapter;
import com.yeshuihan.materialstudy.CustomItemDecoration;

public class RecyclerViewStudyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_study);
        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerView.setAdapter(new CustomAdapter());
        recyclerView.addItemDecoration(new CustomItemDecoration(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
