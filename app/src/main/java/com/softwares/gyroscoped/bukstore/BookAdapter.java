package com.softwares.gyroscoped.bukstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by lanke on 7/16/2017.
 */

public class BookAdapter extends ArrayAdapter<Book>
{

    public BookAdapter(Context context, ArrayList<Book> books)
    {
        super(context,0,books);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View dataView = convertView;
        if(dataView == null)
        {
            dataView = LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
        }

        Book book = getItem(position);
        TextView nameOfBook = (TextView)dataView.findViewById(R.id.book_name_text_view);
        TextView author = (TextView)dataView.findViewById(R.id.auther_name_text_view);
        TextView date = (TextView)dataView.findViewById(R.id.year_text_view);
        TextView description = (TextView)dataView.findViewById(R.id.description_text_view);
        ImageView thumbnailImage = (ImageView)dataView.findViewById(R.id.book_image_view);

        thumbnailImage.setImageBitmap(book.getThumbnail());
        nameOfBook.setText(book.getBookName());
        author.setText(book.getAuthor());
        date.setText(book.getDate());
        description.setText(book.getDescription());

        return dataView;
    }
}
