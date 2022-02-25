package com.yeshuihan.androidstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private Paint mFirstWavePaint;
    private Paint mSecondWavePaint;
    private Path mFirstPath;
    private Path mSecondPath;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


        /*初始化波浪画笔*/
        mFirstWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstWavePaint.setAntiAlias(true);
        mFirstWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mFirstWavePaint.setColor(Color.RED);
        mFirstWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        mSecondWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondWavePaint.setAntiAlias(true);
        mSecondWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mSecondWavePaint.setColor(Color.GREEN);
        mSecondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        mFirstPath = new Path();
        mSecondPath = new Path();
    }

    private int mAmplitude = 50;
    /**
     * 角速度
     */
    private static final float sPalstance = 0.5F;
    /**
     * 最高水位
     */
    private static final float sMaxWaterProgress = 100;
    /**
     * 开始 波动水位
     */
    private static final int sWhatStartWave = 100;
    /**
     * 刷新频率
     */
    private static final long sRefreshInterval = 30;

    private int mWaveSize = 200;
    private int mAngle = 360;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);




    }
}
