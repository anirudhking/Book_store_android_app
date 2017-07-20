package com.softwares.gyroscoped.bukstore;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lanke on 7/18/2017.
 */

public class BooksLoader extends AsyncTaskLoader<ArrayList<Book>> {

    String urlString;

    public BooksLoader(Context context,String url) {
        super(context);
        Log.v("BookLoader","constructor");
        urlString = url;
    }

    @Override
    public ArrayList<Book> loadInBackground()
    {
        Log.v("BookLoader","LoadInBackground()");
        return Utility.makeHttpConnection(urlString);
    }


}
