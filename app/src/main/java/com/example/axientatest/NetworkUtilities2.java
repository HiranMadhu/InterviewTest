package com.example.axientatest;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtilities2 {
    private static final String LOG_TAG= NetworkUtilities2.class.getSimpleName();

    private static final String BASE_URL="https://jsonplaceholder.typicode.com/users";


    static String getUserDetails(){
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String jason=null;

        try{
            Uri builderURI=Uri.parse(BASE_URL).buildUpon().build();
            URL requestURL=new URL(builderURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();

            String line;
            while ((line=reader.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }

            if(builder.length()==0){
                return null;
            }
            jason=builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG,jason);

        return jason;
    }

}
