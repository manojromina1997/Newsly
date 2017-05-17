package com.example.manoj.newsly;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    private static final int News_LOADER_ID = 1;
    private WebView browser;
    Uri uri;
    ProgressBar progressBar;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        //connecting to the text view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        //progressBar = (ProgressBar) findViewById(R.id.loading_indicator_detailed);

        Intent intent = getIntent();
        uri = intent.getParcelableExtra("NewsUrl");
        System.out.println("The uri is "+uri);

        browser = (WebView)findViewById(R.id.webView1);
        browser.setWebViewClient(new MyBrowser());



        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected())
        {


            String url = uri.toString();
            System.out.println("The url is "+url);
            browser.getSettings().setLoadsImagesAutomatically(true);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            browser.loadUrl(url);

        }
        else
        {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //View loadingIndicator = findViewById(R.id.loading_indicator);
            //loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }
    public void open(View view){

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);

            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

            //progressBar.setVisibility(View.GONE);
        }




    }

    public void refresh(View view)
    {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
