package com.yeshuihan.androidstudy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;



public class WaveView extends View {
    private Paint mBgPaint;
    private Paint mFirstWavePaint;
    private Paint mSecondWavePaint;
    private Path mFirstPath;
    private Path mSecondPath;
    private Bitmap mBitmap;
    private Canvas mBitmapCanvas;

    private int mBgColor;
    private int mFirstWaveColor;
    private int mSecondWaveColor;

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
    /**
     * 水波的速度
     */
    private int mSpeed;
    /**
     * 振幅
     */
    private int mAmplitude;
    /**
     * 水位高度
     */
    private float mWaterProgress;
    /**
     * 控件尺寸
     */
    private int mWaveSize;
    private int mAngle = 360;

    private WaveHandler mWaveHandler;
    private WaveThread mWaveThread;
    private boolean mCanLoopWave = true;

    public WaveView(Context context) {
        super(context);

    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWaveSize, mWaveSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mFirstPath.reset();
        mSecondPath.reset();
        canvas.drawCircle(mWaveSize / 2, mWaveSize / 2, mWaveSize / 2, mBgPaint);
//        mBitmapCanvas.drawCircle(mWaveSize / 2, mWaveSize / 2, mWaveSize / 2, mBgPaint);

        /**水位线*/
        float waterLine = (sMaxWaterProgress - mWaterProgress) * mWaveSize * 0.01F;
        /* x、y*/
        mFirstPath.moveTo(0, waterLine);
        mSecondPath.moveTo(0, waterLine);

        int x1, y1, x2, y2;
        for (int i = 0; i < mWaveSize / 4; i++) {
            x1 = i;
            x2 = i;
            y1 = (int) (mAmplitude * Math.sin((i * sPalstance + mAngle) * Math.PI / 180) + waterLine);
            y2 = (int) (mAmplitude * Math.sin((i * sPalstance + mAngle - 90) * Math.PI / 180) + waterLine);
//            mFirstPath.quadTo(x1, y1, x1 + 1, y1);
//            mSecondPath.quadTo(x2, y2, x2 + 1, y2);
        }


        mFirstPath.lineTo(mWaveSize, mWaveSize);
        mFirstPath.lineTo(0, mWaveSize);
        mFirstPath.close();

        mSecondPath.quadTo(mWaveSize / 2, -mAmplitude, mWaveSize, waterLine);
        mSecondPath.quadTo(mWaveSize / 2, -mAmplitude, mWaveSize, waterLine);

        mSecondPath.lineTo(mWaveSize, mWaveSize);
        mSecondPath.lineTo(0, mWaveSize);
        mSecondPath.close();

        mSecondPath.addRoundRect(new RectF(0,0,300,300 ), 30,30, Path.Direction.CCW);

        if (mWaterProgress == sMaxWaterProgress) {
            mBitmapCanvas.drawCircle(mWaveSize / 2, mWaveSize / 2, mWaveSize / 2, mSecondWavePaint);
        } else {
            if (mWaterProgress != 0) {
                //将贝塞尔曲线绘制到Bitmap的Canvas上
//                canvas.drawPath(mFirstPath, mFirstWavePaint);
                canvas.drawPath(mSecondPath, mSecondWavePaint);
            }
        }
//        canvas.setDrawFilter(drawFilter);
        //canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private PaintFlagsDrawFilter drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private void initView(Context context,AttributeSet attrs) {

        mBgColor =  Color.parseColor("#ff74D49D");
        mFirstWaveColor = Color.parseColor("#667BE082");
        mSecondWaveColor = Color.parseColor("#ff7BE082");
        mWaterProgress = 50;
        mAmplitude = dpToPx(20);
        mSpeed = 3;
        mWaveSize = 600;


        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setAntiAlias(true);

        mBitmap = Bitmap.createBitmap(mWaveSize, mWaveSize, Bitmap.Config.ARGB_8888);
        mBitmapCanvas = new Canvas(mBitmap);
        mBitmapCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        /*初始化波浪画笔*/
        mFirstWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstWavePaint.setAntiAlias(true);
        mFirstWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mFirstWavePaint.setColor(mFirstWaveColor);
        mFirstWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        mSecondWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondWavePaint.setAntiAlias(true);
        mSecondWavePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mSecondWavePaint.setColor(mSecondWaveColor);
        mSecondWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        mFirstPath = new Path();
        mSecondPath = new Path();

        mWaveThread = new WaveThread();
        mWaveHandler = new WaveHandler();
        /*开启线程*/
//        mWaveThread.start();
    }

    /**
     * 波浪 波动的 线程
     */
    private class WaveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (mCanLoopWave) {
                mAngle = mAngle - 1 * mSpeed;
                if (mAngle == 0) {
                    mAngle = 360;
                }
                mWaveHandler.sendEmptyMessage(sWhatStartWave);
                SystemClock.sleep(sRefreshInterval);
                if (mWaterProgress == sMaxWaterProgress) {
                    break;
                }
            }
        }
    }

    /**
     * 波浪 波动的 操作者
     */
    private  class WaveHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (sWhatStartWave == msg.what) {
                invalidate();
            }
        }
    }

    /**
     * 设置 水位的进度 [0-100]
     *
     * @param waterProgress 水位进度
     */
    public WaveView setWaterProgress(float waterProgress) {
        if (waterProgress <= 0) {
            waterProgress = 0;
        } else if (waterProgress >= 100) {
            waterProgress = 100;
        }

        mWaterProgress = waterProgress;
        invalidate();
        return this;
    }

    /**
     * 设置第一条波浪线的颜色
     */
    public WaveView setFirstWaveColor(int firstWaveColor) {
        mFirstWaveColor = firstWaveColor;
        mFirstWavePaint.setColor(firstWaveColor);
        invalidate();
        return this;
    }

    /**
     * 设置第二条波浪线的 颜色
     */
    public WaveView setSecondWaveColor(int secondWaveColor) {
        this.mSecondWaveColor = secondWaveColor;
        mSecondWavePaint.setColor(mSecondWaveColor);
        invalidate();
        return this;
    }

    /**
     * 设置 波动速读
     */
    public WaveView setSpeed(int speed) {
        if (speed < 1) {
            speed = 1;
        }
        if (speed > 10) {
            speed = 10;
        }
        mSpeed = speed;
        return this;
    }

    /**
     * 设置 振幅
     *
     * @param amplitude 单位 dp
     */
    public WaveView setAmplitude(int amplitude) {
        if (amplitude <= 0) {
            amplitude = 0;
        }
        mAmplitude = amplitude;
        invalidate();
        return this;
    }


    /**
     * 数据转换: dp---->px
     */
    private int dpToPx(float dp) {
        return (int) (dp * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCanLoopWave = false;
        if (mWaveThread != null) {
            mWaveThread.interrupt();
            mWaveThread = null;
        }
        if (mWaveHandler != null) {
            mWaveHandler.removeMessages(sWhatStartWave);
            mWaveHandler = null;
        }
    }
}
