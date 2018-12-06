package com.example.nickjm6.pokeapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpRequest extends Application {
    private static String serverAddress;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public void onCreate(){
        super.onCreate();
        serverAddress = this.getString(R.string.serverAddress);
    }

    public static void get(String endpoint, AsyncHttpResponseHandler responseHandler){
        String fullURL = serverAddress +  "/" + endpoint;
        client.get(fullURL, responseHandler);
    }
}
