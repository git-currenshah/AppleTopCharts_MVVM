package com.example.appletopcharts_mvvm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appletopcharts_mvvm.models.Results;
import com.example.appletopcharts_mvvm.repositories.ChartRepository;

import java.util.List;

public class ChartListViewModel extends ViewModel {

    private ChartRepository chartRepository;

    private boolean mIsViewingChart;

    private boolean mIsPerformingQuery;

    public ChartListViewModel() {
        chartRepository = ChartRepository.getInstance();
        mIsPerformingQuery = false;
    }

    public LiveData<List<Results>> getCharts(){
        return chartRepository.getResults();
    }

    public void getChartsApi(String country, String type, String category, String page){
        mIsViewingChart = true;
        mIsPerformingQuery = true;
        chartRepository.getChartsApi(country,type,category,page);
    }

    public boolean isViewingCharts() {
        return mIsViewingChart;
    }

    public void setIsViewingChart(boolean isViewingChart){
        mIsViewingChart = isViewingChart;
    }

    public boolean onBackPressed(){
        if(mIsPerformingQuery){
            chartRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
        if(mIsViewingChart){
            mIsViewingChart = false;
            return  false;
        }
        return true;
    }

    public void setIsPerformingQuery(boolean mIsPerformingQuery){
        this.mIsPerformingQuery = mIsPerformingQuery;
    }

    public boolean getIsPerformingQuery(){
        return mIsPerformingQuery;
    }
}
