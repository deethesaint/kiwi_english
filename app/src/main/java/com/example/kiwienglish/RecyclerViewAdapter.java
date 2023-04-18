package com.example.kiwienglish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiwienglish.Database.CourseModel;
import com.example.kiwienglish.Interface.IClickItemListener;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CourseViewHolder> {

    private List<CourseModel> list;
    private IClickItemListener iClickItemListener;
    private Context context;
    public RecyclerViewAdapter(Context context, IClickItemListener iClickItemListener) {
        this.context = context;
        this.iClickItemListener = iClickItemListener;
    }
    public void setData(List<CourseModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        final CourseModel course = list.get(position);
        holder.tvCousreName.setText(course.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemListener.onClickItem(course);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCousreName;
        private CardView cardView;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCousreName = itemView.findViewById(R.id.course_name);
            cardView = itemView.findViewById(R.id.cv);
        }

    }
}
