package com.example.manoj.newsly;

/**
 * Created by manoj on 2/18/2017.
 */

public class News {

    //string for the news image
    public String mNewsImage;

    //string for the news title
    public String mNewsTitle;

    //string for the news author
    public String mNewsAuthor;

    //string for the news date
    public String mNewsDate;


    //string for the news url
    public String mNewsUrl;

    //string for the news description
    public String mNewsDescription;



    public News(String newsImage,String newsTitle,String newsAuthor,String newsDate,String newsDescription,String newsUrl)
    {
        mNewsImage = newsImage;
        mNewsTitle = newsTitle;
        mNewsAuthor = newsAuthor;
        mNewsDate = newsDate;

        mNewsDescription = newsDescription;
        mNewsUrl = newsUrl;



    }

    //when this methods will be called they will return some values.

    public String getmNewsImage() {
        return mNewsImage;
    }

    public String getmNewsTitle()
    {
        return mNewsTitle;
    }

    public String getmNewsAuthor()
    {
        return mNewsAuthor;
    }

    public String getmNewsDate()
    {
        return mNewsDate;
    }


    public String getmNewsUrl()
    {
        return mNewsUrl;
    }

    public String getmNewsDescription()
    {
        return mNewsDescription;
    }




}
