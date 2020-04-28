package com.example.appletopcharts_mvvm.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appletopcharts_mvvm.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView categoryImage;
    TextView categoryTitle;
    OnGetChartListner listener;

    public CategoryViewHolder(@NonNull View itemView, OnGetChartListner listener) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.category_image);
        categoryTitle = itemView.findViewById(R.id.category_title);
        this.listener = listener;

        itemView.setOnClickListener(this);
    }


    public TextView getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(TextView categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Override
    public void onClick(View v) {

        listener.onCategoryClick(categoryTitle.getText().toString());

    }
}
