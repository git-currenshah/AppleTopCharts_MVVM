package com.example.appletopcharts_mvvm.requests;

import com.example.appletopcharts_mvvm.requests.responses.ChartResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChartsApi {

    @GET("api/v1/{country}/{type}/{category}/all/{page}/explicit.json")
    Call<ChartResponse> getChartResponse(
            @Path("country") String country,
            @Path("type") String type,
            @Path("category") String category,
            @Path("page") String page
    );


}
