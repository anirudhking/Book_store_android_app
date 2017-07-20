package com.softwares.gyroscoped.bukstore;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by lanke on 7/16/2017.
 */

public class Utility
{



  static ArrayList<Book> parseJSONResponse(String jsonData)
 {

     String title="",publishedDate="",description="";
     JSONArray authors = null;
     String author="";
     String thumbnailString ="";
     Bitmap thumbnail = null;
     Log.v("Utility","parseJSONResponse()");
       ArrayList<Book> books = new ArrayList<Book>();
     try {
         JSONObject root = new JSONObject(jsonData);
         JSONArray items = root.getJSONArray("items");
         int totalBooks = items.length();

         for(int i = 0 ;i< totalBooks ; i++)
         {
             JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
              title = volumeInfo.getString("title");
             if(volumeInfo.has("authors"))
             {
              authors = volumeInfo.getJSONArray("authors");
              author = authors.getString(0);
             }
             else
             {
                 author = "";
             }
             if(volumeInfo.has("publishedDate")) {
                 publishedDate = volumeInfo.getString("publishedDate");
             }
             else
             {
                 publishedDate = "";
             }
             if(volumeInfo.has("description"))
             {
             description = volumeInfo.getString("description");
             description = description.substring(0,Math.min(description.length(), 150));
             description = description + "...";
             }
             else
             {
                 description = "";
             }

             if(volumeInfo.has("imageLinks")) {
                 JSONObject imageLinks = (JSONObject) volumeInfo.getJSONObject("imageLinks");
                 if (imageLinks.has("smallThumbnail")) {
                     thumbnailString = imageLinks.getString("smallThumbnail");
                     URL url = new URL(thumbnailString);
                     thumbnail = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                 }
             }
             books.add(new Book(title,author,publishedDate,description,thumbnail));

         }


     } catch (JSONException e) {
         Log.v("Utility","parseJSONResponse() : exception "+e);
     } catch (MalformedURLException e) {
         Log.v("utility","malformedURL exception");
     } catch (IOException e) {
         Log.v("utility","io exception");
     }


     return books;

 }

 public static ArrayList<Book> makeHttpConnection(String urlString)
 {
     InputStream inputStream = null;
     HttpURLConnection connection= null;
     ArrayList<Book> books = null;

     Log.v("Utility","makeHttpConnection()");

     URL url = makeURL(urlString);

     try {

           connection = (HttpURLConnection) url.openConnection();
           connection.setConnectTimeout(15000);
           connection.setReadTimeout(10000);
           connection.setRequestMethod("GET");
           connection.connect();

           if(connection.getResponseCode() == 200)
           {
               //connection successful
                inputStream = connection.getInputStream();
                String jsonString = readInputStream(inputStream);
                books = parseJSONResponse(jsonString);
           }

           else
           {
           //connection problem
               Log.v("Utility","makeHttpConnection():connection problem"+connection.getResponseMessage());
           }

     } catch (IOException e) {
         Log.v("Utility","makeHttpConnection() : ioException "+e.toString());
     }

     return books;
 }



 private static URL makeURL(String urlString)
 {    URL url = null;


     if(urlString.length() == 0)
       return null;

     try {
        url = new URL(urlString);
     } catch (MalformedURLException e) {
         Log.v("Utility","mkeURL() : exception"+e);
     }

   return url;
 }


 private static String readInputStream(InputStream inputStream) throws IOException
 {
     StringBuilder stringBuilder = new StringBuilder();
     if(inputStream != null) {
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         String line = bufferedReader.readLine();
         while (line != null)
         {
             stringBuilder.append(line);
             line = bufferedReader.readLine();
         }
     }
     return stringBuilder.toString();

 }


}
