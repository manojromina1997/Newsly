package com.example.manoj.newsly;


import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by manoj on 2/19/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    Context context;
    //constructing the new news adapter
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);

    }

    //creatin the view with the modifed news_list_item format.
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }


        News currentNews = getItem(position);

        // Find the Image in the news_list_item.xml layout
       ImageView newsImageView = (ImageView) listItemView.findViewById(R.id.news_image);

        // set the image .
        //getContext for the image to be load on the page .
        //Picasso.with(getContext()).load(currentNews.getmNewsImage()).into(newsImageView);

        Glide.with(getContext())
                .load(currentNews.getmNewsImage())
                .into(newsImageView);

       // newsImageView.setImageResource(currentNews.getmNewsImage());


        // Find the TextView in the list_item.xml layout
        TextView newsTitleTextView = (TextView) listItemView.findViewById(R.id.news_title);
        if (Build.VERSION.SDK_INT >= 24)
        {
            newsTitleTextView.setText(Html.fromHtml(currentNews.getmNewsTitle() , Html.FROM_HTML_MODE_LEGACY));;
            newsTitleTextView.setText(Html.fromHtml(currentNews.getmNewsTitle() , Html.FROM_HTML_MODE_LEGACY));

        }
        else
        {
            newsTitleTextView.setText(Html.fromHtml(currentNews.getmNewsTitle()));
            newsTitleTextView.setText(Html.fromHtml(currentNews.getmNewsTitle()));
        }



        // Find the TextView in the list_item.xml layout
        TextView newsAuthorTextView = (TextView) listItemView.findViewById(R.id.news_author);

        // set this text on the name TextView
        newsAuthorTextView.setText(currentNews.getmNewsAuthor());




        // Find the TextView in the list_item.xml layout
        TextView newsDateTextView = (TextView) listItemView.findViewById(R.id.news_date);

        String createdate =
                null;
        try {
            createdate = new SimpleDateFormat("dd-MM-yyyy").format(
                    new SimpleDateFormat("yyyy-MM-dd").parse(currentNews.getmNewsDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newsDateTextView.setText(createdate);

        // set this text on the name TextView


        return listItemView;


    }
}
