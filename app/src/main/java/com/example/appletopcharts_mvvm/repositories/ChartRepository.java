package com.example.appletopcharts_mvvm.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appletopcharts_mvvm.models.Results;
import com.example.appletopcharts_mvvm.requests.ChartsApiClient;

import java.util.List;

public class ChartRepository {

    private static ChartRepository instance;


    private ChartsApiClient mChartsApiClient;

    public static ChartRepository getInstance(){

        if (instance == null){
            instance = new ChartRepository();
        }
        return instance;
    }

    private ChartRepository(){
       mChartsApiClient = ChartsApiClient.getInstance();
    }
    public LiveData<List<Results>> getResults(){
        return mChartsApiClient.getResults();
    }

    public void getChartsApi(String country, String type, String category, String page){
        if(Integer.parseInt(page)%10 != 0){
            page = "10";
        }
        mChartsApiClient.getChartsApi(country,type,category,page);
    }

    public void cancelRequest(){

        mChartsApiClient.cancelRequest();
    }
}
