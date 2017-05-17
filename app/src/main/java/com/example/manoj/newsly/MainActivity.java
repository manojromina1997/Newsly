package com.example.manoj.newsly;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

//implementing loader
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int News_LOADER_ID = 1;

    //defining the log tag
    private static final String LOG_TAG = MainActivity.class.getName();

    //the url from where the we will get the data
    private static final String REQUEST_URL =
            "https://newsapi.org/v1/articles";

    //creating a variable of Earthquakeadapter
    private NewsAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //connecting to the text view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);






        // connecting the activity_mains listview/
        ListView newsListView = (ListView) findViewById(R.id.list);

        //setting the content of the view to empty
       newsListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
       newsListView.setAdapter(mAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
             News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmNewsUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(MainActivity.this,DetailedActivity.class);
                websiteIntent.putExtra("NewsUrl", newsUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });



        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(News_LOADER_ID, null, this);
        }
        else
        {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    //when the loader will be created this method will be called.
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG,"Creating the Loader");

        //preference manager will send the data here
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String source = sharedPrefs.getString(
                getString(R.string.news_source_key),
                getString(R.string.news_source_default));


        String sortBy = sharedPrefs.getString(
                getString(R.string.news_sortby_key),
                getString(R.string.news_sortby_default)
        );

        System.out.println("Source "+source);
        System.out.println("SortBy "+sortBy);

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        uriBuilder.appendQueryParameter("source", source);
        uriBuilder.appendQueryParameter("sortBy", sortBy);
        uriBuilder.appendQueryParameter("apiKey","790083b0e56d4c689c823d5627fe7898" );

        return new NewsLoader(this, uriBuilder.toString());


    }


    //when the loading task is finished this method will be called.
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        Log.e(LOG_TAG,"Loading Finished");
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_news_found);


        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    //when the loader is to reset then this method will be calles.

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.e(LOG_TAG,"Loader Reset");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    //this is for above setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //if the setting is selected then the setting activity will be called

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

