package com.yeshuihan.materialstudy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

public class CustomItemDecoration: RecyclerView.ItemDecoration {
    var headHeight:Int = 0
    lateinit var headPaint:Paint
    lateinit var drawTextPaint:Paint
    lateinit var drawOverTextPaint:Paint
    lateinit var textRect:Rect
    lateinit var content:Context

    constructor(content: Context):super() {
        this.content = content
        headHeight = dp2p(content, 50f);
        headPaint = Paint();
        headPaint.textSize = 50f
        headPaint.color = Color.RED

        drawTextPaint = Paint()
        drawTextPaint.textSize = 50f
        drawTextPaint.color = Color.BLACK

        drawOverTextPaint = Paint()
        drawOverTextPaint.textSize = 50f
        drawOverTextPaint.color = Color.BLUE

        textRect = Rect()
    }



    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter is CustomAdapter) {
            var adapter = parent.adapter as CustomAdapter
            var position = parent.getChildAdapterPosition(view);
            val isFirstOfGroup = adapter.isFirstOfGroup(position);
            if (isFirstOfGroup) {
                outRect.set(0,headHeight,0,0)
            } else {
                outRect.set(0,4,0,0)
            }

        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        var adapter = parent.adapter as CustomAdapter
        val left = parent.paddingLeft.toFloat()
        var right = parent.width - parent.paddingRight.toFloat()

        for (index in 0 until parent.childCount) {
            val view = parent.getChildAt(index);
            var position = parent.getChildAdapterPosition(view);
            val isFirstOfGroup = adapter.isFirstOfGroup(position);

            if (view.top - headHeight - parent.paddingTop <= 0) {
                continue
            }

            if (isFirstOfGroup) {
                val top = Math.max(view.top - headHeight, parent.paddingTop)
                c.drawRect(left, top.toFloat(), right, view.top.toFloat(), drawTextPaint)
                val name = adapter.getGroupName(position)
                headPaint.getTextBounds(name,0,name.length, textRect)
                c.drawText(name, left + 20, view.top.toFloat() - headHeight / 2 + getBaseLine(headPaint) , headPaint)
            } else {
                c.drawRect(left, view.top.toFloat() - 4, right, view.top.toFloat(), drawTextPaint)
            }
        }


    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        var adapter = parent.adapter as CustomAdapter
        val left = parent.paddingLeft.toFloat()
        var right = parent.width - parent.paddingRight.toFloat()
        var top = parent.paddingTop.toFloat()

        val position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        val view = parent.findViewHolderForAdapterPosition(position)?.itemView;

        val isFirstOfGroup = adapter.isFirstOfGroup(position + 1);
        if (isFirstOfGroup) {
            val bottom = view!!.bottom.toFloat()
//            val top = view!!.bottom.toFloat() - headHeight
            c.drawRect(left, top, right, bottom, drawOverTextPaint)
            c.clipRect(left, top, right, bottom)
            val name = adapter.getGroupName(position)
            c.drawText(adapter.getGroupName(position), left + 20, bottom - headHeight + headHeight / 2 + getBaseLine(headPaint), headPaint)
        } else {
            c.drawRect(left, top, right, top + headHeight, drawOverTextPaint)
            val name = adapter.getGroupName(position)
            headPaint.getTextBounds(name,0,name.length,textRect)
            c.drawText(adapter.getGroupName(position), left + 20, top + headHeight / 2 + getBaseLine(headPaint), headPaint)
        }

    }



    private fun dp2p(content:Context, dp: Float): Int {
        return TypedValue.applyDimension(COMPLEX_UNIT_DIP,dp,content.resources.displayMetrics).toInt()
    }

    private fun getBaseLine(paint: Paint):Int {
        return (paint.getFontMetricsInt().bottom - paint.getFontMetricsInt().top) / 2 - paint.getFontMetricsInt().bottom
    }

}