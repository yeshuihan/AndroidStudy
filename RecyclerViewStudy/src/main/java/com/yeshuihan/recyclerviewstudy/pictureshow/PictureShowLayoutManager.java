package com.yeshuihan.recyclerviewstudy.pictureshow;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PictureShowLayoutManager extends RecyclerView.LayoutManager {
    public static int MAX_SHOW_PICTURE = 4;
    public static float SCALE_NUM = 0.05f;
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);

        int itemCount = getItemCount();

        int bottomPosition = 0;

        if (itemCount < MAX_SHOW_PICTURE) {
            bottomPosition = 0;
        } else {
            bottomPosition = itemCount - MAX_SHOW_PICTURE;
        }

        for (int i = bottomPosition; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);

            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            int left = widthSpace / 2;
            int right = getWidth() - widthSpace / 2;
            int top = heightSpace / 2;
            int bottom = getHeight() - heightSpace / 2;

            layoutDecoratedWithMargins(view, left, top, right ,bottom);
            int level = itemCount - 1 - i;

            if (i == bottomPosition) {
                level = level - 1;
            }

            if (level  > 0) {
                view.setTranslationY(level * 30);
                view.setScaleX(1 - level * SCALE_NUM);
                view.setScaleY(1 - level * SCALE_NUM);
            }
        }
    }


}
