package com.example.nickjm6.pokeapp;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpRequest {
    private static String serverAddress;
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String serverAddress, String endpoint,  AsyncHttpResponseHandler responseHandler){
        String fullURL = serverAddress + endpoint;
        client.get(fullURL, responseHandler);
    }

    public static void get(String serverAddress, String endpoint, RequestParams params, AsyncHttpResponseHandler responseHandler){
        String fullUrl = serverAddress + endpoint;
        client.get(fullUrl, params, responseHandler);
    }
}
