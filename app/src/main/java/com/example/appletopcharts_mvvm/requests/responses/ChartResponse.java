package com.example.appletopcharts_mvvm.requests.responses;

import com.example.appletopcharts_mvvm.models.Results;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChartResponse {

    private Feed feed;

    public Feed getFeed() {
        return feed;
    }

    public static class Feed {


        @SerializedName("results")
        @Expose
        private List<Results> resultsList;


        public List<Results> getResultsList() {
            return resultsList;
        }

        @Override
        public String toString() {
            return "feed{" +
                    "resultsList=" + resultsList +
                    '}';
        }
    }


}
