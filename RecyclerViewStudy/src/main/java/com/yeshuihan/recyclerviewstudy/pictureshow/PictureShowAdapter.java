package com.yeshuihan.recyclerviewstudy.pictureshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yeshuihan.recyclerviewstudy.R;

import java.util.List;

public class PictureShowAdapter extends RecyclerView.Adapter<PictureShowAdapter.ViewHolder> {
    private List<Integer> data;

    PictureShowAdapter(List<Integer> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(data.get(position));
        holder.nameTv.setText("pic:" + position);
        holder.pageTv.setText((position + 1) + "/" + getItemCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTv;
        TextView pageTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTv = itemView.findViewById(R.id.name);
            pageTv = itemView.findViewById(R.id.page);
        }
    }
}
