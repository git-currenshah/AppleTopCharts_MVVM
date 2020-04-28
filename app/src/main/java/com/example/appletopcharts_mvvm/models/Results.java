package com.example.appletopcharts_mvvm.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable {

        private String name;
        private String artistName;
        private String artworkUrl100;
        private String copyright;
        private String artistUrl;

    public Results(String name, String artistName, String artworkUrl100, String copyright, String artistUrl) {
        this.name = name;
        this.artistName = artistName;
        this.artworkUrl100 = artworkUrl100;
        this.copyright = copyright;
        this.artistUrl = artistUrl;
    }

    public Results() {
    }

    protected Results(Parcel in) {
        name = in.readString();
        artistName = in.readString();
        artworkUrl100 = in.readString();
        copyright = in.readString();
        artistUrl = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    @Override
    public String toString() {
        return "Results{" +
                "name='" + name + '\'' +
                ", artistName='" + artistName + '\'' +
                ", artworkUrl100='" + artworkUrl100 + '\'' +
                ", copyright='" + copyright + '\'' +
                ", artistUrl='" + artistUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(artistName);
        dest.writeString(artworkUrl100);
        dest.writeString(copyright);
        dest.writeString(artistUrl);
    }
}


