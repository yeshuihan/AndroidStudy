package com.yeshuihan.recyclerviewstudy.pictureshow;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PictureCallBack extends ItemTouchHelper.SimpleCallback {
    private List<Integer> data;
    private RecyclerView.Adapter adapter;

    public PictureCallBack(int swipeDirs, RecyclerView.Adapter adapter, List<Integer> data) {
        super(15, 0);
        this.data = data;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.i("fzw", "onSwiped:" + direction);
        Integer id = data.remove(viewHolder.getLayoutPosition());
        data.add(0, id);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxdistance = recyclerView.getWidth() * 0.5;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double faction = distance / maxdistance;
        faction = faction > 1 ? 1 : faction;

        int childCount = recyclerView.getChildCount();

        for (int i = 0 ; i < childCount - 1; i++) {
            View view = recyclerView.getChildAt(i);


            if (i == 0) {
                continue;
            }

            int level = childCount - 1 - i;
            if (level  > 0) {
                view.setTranslationY((float) (level * 30 - faction * 30));
                view.setScaleX((float) (1 - level * 0.05 + faction * 0.05 ));
                view.setScaleY((float) (1 - level * 0.05 + + faction * 0.05));
            }

        }


    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }
}
