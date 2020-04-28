package com.example.appletopcharts_mvvm.requests;

import com.example.appletopcharts_mvvm.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static ChartsApi chartsApi = retrofit.create(ChartsApi.class);

    public  static  ChartsApi getChartsApi(){
        return chartsApi;
    }


}
