package com.example.manoj.newsly;

/**
 * Created by manoj on 2/20/2017.
 */


import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

//it will recive the url from the mainactivity
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
        System.out.println("This is the Rebuild Url "+url);

    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"On Starting Loading");
        forceLoad();
    }

    //this method is similar to the asyntask lading in the background
    @Override
    public List<News> loadInBackground() {
        Log.e(LOG_TAG,"Loading in Background started");
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }

        List<News> result = NetworkUtils.fetchNewsData(mUrl);
        return result;

    }
}

