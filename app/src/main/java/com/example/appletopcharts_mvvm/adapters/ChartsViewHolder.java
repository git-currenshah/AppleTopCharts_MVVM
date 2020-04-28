package com.example.appletopcharts_mvvm.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appletopcharts_mvvm.R;

public class ChartsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name, artistName, copyright, moreinfo;

    AppCompatImageView artwork;

    OnGetChartListner onGetChartListner;

    public ChartsViewHolder(@NonNull View itemView, OnGetChartListner onGetChartListner ) {
        super(itemView);

        this.onGetChartListner = onGetChartListner;
        name = itemView.findViewById(R.id.name_title);
        artistName = itemView.findViewById(R.id.artist_name);
        copyright = itemView.findViewById(R.id.copyright);
        moreinfo = itemView.findViewById(R.id.link);
        artwork = itemView.findViewById(R.id.artwork_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        onGetChartListner.onMoreInfoClick(getAdapterPosition());
    }
}
