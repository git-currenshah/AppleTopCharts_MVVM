package com.example.appletopcharts_mvvm.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appletopcharts_mvvm.AppExecutors;
import com.example.appletopcharts_mvvm.models.Results;
import com.example.appletopcharts_mvvm.requests.responses.ChartResponse;


import java.io.IOException;
import java.io.InterruptedIOException;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import retrofit2.Call;
import retrofit2.Response;

import static com.example.appletopcharts_mvvm.util.Constants.NETWORK_TIMEOUT;

public class ChartsApiClient {

    private RetrieveChartsRunnable mRetrieveChartsRunnable;

    private static final String TAG = "ChartsApiClient";

    private static ChartsApiClient instance;

    private MutableLiveData<List<Results>> mResults;

    public static ChartsApiClient getInstance(){
        if(instance == null){
            instance = new ChartsApiClient();
        }
        return instance;
    }
    private ChartsApiClient(){

        mResults = new MutableLiveData<>();

    }

    public LiveData<List<Results>> getResults(){
        return mResults;
    }

    public void getChartsApi(String country,String type,String category, String page ){

        if(mRetrieveChartsRunnable != null){
            mRetrieveChartsRunnable = null;
        }
        mRetrieveChartsRunnable = new RetrieveChartsRunnable(country,type,category,page);
        final Future handler = AppExecutors.getInstance().networkIO().submit(mRetrieveChartsRunnable);

        // Set a timeout for the data refresh
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveChartsRunnable implements Runnable{

        private String country;
        private String type;
        private String category;
        private String page;
        boolean cancelRequest;


        public RetrieveChartsRunnable(String country, String type, String category, String page) {
            this.country = country;
            this.type = type;
            this.category = category;
            this.page = page;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getCharts(country,type,category,page).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    List<Results> list = ((ChartResponse)response.body()).getFeed().getResultsList();
                    if(page.equals("10")){
                        mResults.postValue(list);
                    }
                    else {
                        List<Results> currentResults = list.subList(10,list.size());
                       // currentResults = currentResults.subList(10,currentResults.size());
                        mResults.postValue(list);
                    }

                }else {

                    String error = response.errorBody().toString();
                    Log.e(TAG, "run: "+error);
                    mResults.postValue(null);
                }
            }
            catch (InterruptedIOException e){
                e.printStackTrace();
                mResults.postValue(null);
            }
            catch (IOException e) {
                e.printStackTrace();
                mResults.postValue(null);

                Log.d(TAG, "run: ChartsAPIClient ERROR");
            }


        }
        



        private Call<ChartResponse> getCharts(String country, String type, String category, String page){
            return ServiceGenerator.getChartsApi().getChartResponse(country,type,category,page);
        }
        
        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: cancelling the request");
            cancelRequest = true;
        }
    }

    public void cancelRequest(){
        if(mRetrieveChartsRunnable != null){
            mRetrieveChartsRunnable.cancelRequest();
        }
    }
}
