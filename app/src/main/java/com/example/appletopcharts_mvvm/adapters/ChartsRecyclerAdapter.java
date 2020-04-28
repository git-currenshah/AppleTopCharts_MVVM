package com.example.appletopcharts_mvvm.adapters;

import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.appletopcharts_mvvm.R;
import com.example.appletopcharts_mvvm.models.Results;
import com.example.appletopcharts_mvvm.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ChartsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "ChartsRecyclerAdapter";

    private static final int CHART_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private List<Results> mResults;
    private OnGetChartListner onGetChartListner;

    public ChartsRecyclerAdapter(OnGetChartListner onGetChartListner) {
        this.onGetChartListner = onGetChartListner;
        mResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case CHART_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chartlist_item, parent, false);
                return new ChartsViewHolder(view, onGetChartListner);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view,onGetChartListner);
            }
            case LOADING_TYPE: {

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);

            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chartlist_item, parent, false);
                return new ChartsViewHolder(view, onGetChartListner);
            }
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);

        if (itemViewType == CHART_TYPE) {

            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

            if (mResults.get(position).getArtworkUrl100() != null) {
                Glide.with(holder.itemView.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(mResults.get(position).getArtworkUrl100())
                        .into(((ChartsViewHolder) holder).artwork);
            }

            ((ChartsViewHolder) holder).name.setText(mResults.get(position).getName());
            ((ChartsViewHolder) holder).artistName.setText(mResults.get(position).getArtistName());
            ((ChartsViewHolder) holder).copyright.setText(mResults.get(position).getCopyright());
            String linkUrl = mResults.get(position).getArtistUrl();

            String linkedText = String.format("<a href=\"%s\">More Info</a> ", linkUrl);

            ((ChartsViewHolder) holder).moreinfo.setText(Html.fromHtml(linkedText));

            ((ChartsViewHolder) holder).moreinfo.setMovementMethod(LinkMovementMethod.getInstance());

        }

        if(itemViewType == CATEGORY_TYPE){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://com.example.appletopcharts_mvvm/drawable/" + mResults.get(position).getArtworkUrl100());
            Glide.with(((CategoryViewHolder)holder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(path)
                    .into(((CategoryViewHolder)holder).categoryImage);
            ((CategoryViewHolder)holder).categoryTitle.setText(mResults.get(position).getName());

            Log.d(TAG, "onBindViewHolder: Category "+ mResults.get(position).getName());
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(mResults.get(position).getArtistUrl() == "CATEGORY"){
            return CATEGORY_TYPE;
        }
        if (mResults.get(position).getName().equals("LOADING...")) {
            return LOADING_TYPE;
        }
        return CHART_TYPE;
    }

    private boolean isLoading() {

        if (mResults != null) {
            if (mResults.size() > 0) {

                if (mResults.get(mResults.size() - 1).getName().equals("LOADING..."))
                    return true;
            }

        }

        return false;
    }

    public void displayLoading() {

        if (!isLoading()) {
            Results result = new Results();
            result.setName("LOADING...");
            List<Results> loadingList = new ArrayList<>();
            loadingList.add(result);
            mResults = loadingList;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mResults != null) {
            return mResults.size();
        }
        return 0;
    }

    public void setChart(List<Results> results) {

        mResults = results;
        notifyDataSetChanged();

    }

    public void displayMainCategories(){
        List<Results> categories = new ArrayList<>();
        for(int i = 0; i < Constants.DEFAULT_MAIN_CATEGORIES.length; i++) {
            Results result = new Results();
            result.setName(Constants.DEFAULT_MAIN_CATEGORIES[i]);
            result.setArtworkUrl100(Constants.DEFAULT_MAIN_CATEGORY_IMAGES[i]);
            result.setArtistUrl("CATEGORY");
            categories.add(result);
        }
        mResults = categories;
        notifyDataSetChanged();
        }

    }

