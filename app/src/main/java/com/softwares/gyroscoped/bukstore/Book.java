package com.softwares.gyroscoped.bukstore;

import android.graphics.Bitmap;

/**
 * Created by lanke on 7/16/2017.
 */

public class Book
{

    private String bookName;
    private String author;
    private String date;
    private String description;
    private String imageSource;
    private Bitmap thumbnail;

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public Book(String bookName, String author, String date, String description, Bitmap thumbnail) {
        this.bookName = bookName;
        this.author = author;
        this.date = date;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Book(String bookName, String author, String date, String description) {
        this.bookName = bookName;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImageSource() {
        return imageSource;
    }
}
