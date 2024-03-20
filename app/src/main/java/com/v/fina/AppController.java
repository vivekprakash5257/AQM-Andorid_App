package com.v.fina;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    private static AppController sInstance;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static synchronized AppController getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public void addToRequestQueue(Request request, String tag)
    {
        request.setTag(tag);
        getRequestQueue().add(request);

    }

/*
Cancel all the requests matching with the given tag
     */

    public void cancelAllRequests(String tag)
    {
        getRequestQueue().cancelAll(tag);
    }

}
