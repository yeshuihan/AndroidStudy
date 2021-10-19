package com.yeshuihan.materialstudy

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.yeshuihan.recyclerviewstudy.R

public class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.custom_list_item,parent,false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("fzw", "onBindViewHolder:" + position)
        holder.textView?.text = "" + position
    }

    override fun getItemCount(): Int {
        return 50
    }

    public fun  isFirstOfGroup(position:Int):Boolean {
        return position % 10 == 0
    }

    public fun getGroupName(position:Int): String {
        return "第" + (position / 10 + 1) + "组"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView:TextView?

        init {
            textView = itemView.findViewById(R.id.text)
            textView?.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
//            textView?.setBackgroundColor(Color.BLUE)
        }
    }
}