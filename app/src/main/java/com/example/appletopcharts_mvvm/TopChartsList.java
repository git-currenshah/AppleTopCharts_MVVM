package com.example.appletopcharts_mvvm;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appletopcharts_mvvm.adapters.ChartsRecyclerAdapter;
import com.example.appletopcharts_mvvm.adapters.OnGetChartListner;
import com.example.appletopcharts_mvvm.models.Results;
import com.example.appletopcharts_mvvm.requests.ChartsApi;
import com.example.appletopcharts_mvvm.requests.ChartsApiClient;
import com.example.appletopcharts_mvvm.requests.ServiceGenerator;
import com.example.appletopcharts_mvvm.requests.responses.ChartResponse;
import com.example.appletopcharts_mvvm.util.SpacingItemDecorator;
import com.example.appletopcharts_mvvm.viewmodel.ChartListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopChartsList extends BaseActivity implements OnGetChartListner {

    private static final String TAG = "TopChartsList";

    private ChartListViewModel mChartListViewModel;

    private RecyclerView mRecyclerView;

    private ChartsRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_list);

        mRecyclerView = findViewById(R.id.chart_list);

        mChartListViewModel = new ViewModelProvider(this).get(ChartListViewModel.class);

       // testRetrofit();
        initScreenTitle();

        subscribeObservers();
        initRecyclerView();

        if(!mChartListViewModel.isViewingCharts()){
            displayMainCategories();
        }


    }

    private void initRecyclerView(){

        mAdapter = new ChartsRecyclerAdapter(this);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(20);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void subscribeObservers(){

        mChartListViewModel.getCharts().observe(this, new Observer<List<Results>>(){

            @Override
            public void onChanged(List<Results> results) {

                if(results != null) {

                    if(mChartListViewModel.isViewingCharts()){

                        mChartListViewModel.setIsPerformingQuery(false);
                        mAdapter.setChart(results);
                        for (Results results1 : results) {
                            Log.d(TAG, "onChanged: " + results1.getName());

                    }
                    }

                }
            }
        });
    }

    private void getChartsApi(String country,String type, String category, String page){
        mAdapter.displayLoading();
        mChartListViewModel.getChartsApi(country,type,category,page);
    }

    private void initScreenTitle(){
        final TextView screenTitle = findViewById(R.id.screen_title);
        screenTitle.setText("TOP APPLE CHARTS");
    }

    private void testRetrofit() {

        getChartsApi("in","itunes-music","top-songs","50");
       /* ChartsApi chartsApi = ServiceGenerator.getChartsApi();

        Call<ChartResponse> responseCall = chartsApi
                .getChartResponse("itunes-music", "hot-tracks", "10");

        Log.d(TAG, "testRetrofit: " + "INSIDE FUNCTION");

        if (responseCall.isCanceled()) {
            Log.d(TAG, "testRetrofit: CALL CANCELLED");
        }

        responseCall.enqueue(new Callback<ChartResponse>() {

            @Override
            public void onResponse(Call<ChartResponse> call, Response<ChartResponse> response) {

                Log.d(TAG, "onResponse: RESPONSE: " + response.toString());

                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: BODY:" + response.body().toString());


                    List<Results> chartList = new ArrayList<>(response.body().getFeed().getResultsList());

                    for (Results result : chartList) {
                        Log.d(TAG, "onResponse: " + result.getName());

                        Log.d(TAG, "onResponse: " + result.getArtistName());

                    }
                } else {
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ChartResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: Call Failed" + t.getCause());

            }
        });
    }*/
    }

    private void displayMainCategories(){
        Log.d(TAG, "displaySearchCategories: called.");
        mChartListViewModel.setIsViewingChart(false);
        mAdapter.displayMainCategories();
    }

    @Override
    public void onMoreInfoClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        mAdapter.displayLoading();
        if(category == "Music") {
            getChartsApi("in","itunes-music", "top-songs", "50");
        }
        else if(category == "Movies"){
            getChartsApi("in","movies", "top-movies", "50");
        }else if(category == "TV Shows"){
            getChartsApi("us","tv-shows", "top-tv-seasons", "50");
        }
        else if(category == "Books"){
            getChartsApi("us","books", "top-paid", "50");
        }
        else if(category == "Podcasts"){
            getChartsApi("in","podcasts", "top-podcasts", "50");
        }
        else if(category == "iOS Apps"){
            getChartsApi("in","ios-apps", "top-free", "50");
        }


    }

    @Override
    public void onBackPressed() {
        if(mChartListViewModel.onBackPressed()){
            super.onBackPressed();
        }
        else{
            displayMainCategories();
        }
    }
}
