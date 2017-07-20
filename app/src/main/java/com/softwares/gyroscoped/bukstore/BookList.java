package com.softwares.gyroscoped.bukstore;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BookList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>>{

    ArrayList<Book> books;
    static String URL = "";
    TextView emptyView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        emptyView = (TextView)findViewById(R.id.empty_text_view);
        emptyView.setText("Search books by category");
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        ListView listView = (ListView)findViewById(R.id.list_book);
        listView.setEmptyView(emptyView);

        Button searchButton =  (Button)findViewById(R.id.button_search);
        final EditText categoryEditText = (EditText)findViewById(R.id.edit_text_category);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isNetworkConnected()) {
                    emptyView.setText("No network connection");

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    emptyView.setText("");
                    getSupportLoaderManager().destroyLoader(0);
                    String queryParameter = categoryEditText.getText().toString();
                    if (queryParameter.trim() != "") {
                        queryParameter = queryParameter.trim();
                        queryParameter = queryParameter.replace(" ", "%20");
                        URL = "https://www.googleapis.com/books/v1/volumes?q=" + queryParameter + "&maxResults=20";
                        Log.v("URL : ", URL);
                        getSupportLoaderManager().initLoader(0, null, BookList.this).forceLoad();
                    } else {
                        BookAdapter bookAdapter = new BookAdapter(getBaseContext(), new ArrayList<Book>());
                        ListView listView = (ListView) findViewById(R.id.list_book);
                        listView.setAdapter(bookAdapter);
                    }
                }
            }
        });






    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(getBaseContext(),URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {


        progressBar.setVisibility(View.GONE);

        if(data != null) {

            BookAdapter bookAdapter = new BookAdapter(getBaseContext(), data);
            ListView listView = (ListView) findViewById(R.id.list_book);
            listView.setAdapter(bookAdapter);
        }
        else
        {
            emptyView.setText("No Data Found");
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {

    }


    private boolean isNetworkConnected()
    {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = (NetworkInfo)connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }




}
