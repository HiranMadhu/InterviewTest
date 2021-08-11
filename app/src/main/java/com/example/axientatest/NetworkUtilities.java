package com.example.axientatest;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtilities {
    private static final String LOG_TAG=NetworkUtilities.class.getSimpleName();

    private static final String BASE_URL="https://www.axienta.lk/VantageCoreWebAPI/api/avLogin/Get";
    private static final String USER_EMAIL="id";
    private static final String USER_PASSWORD="password";
    private static final String USER_MAC="macaddress";
    private static final String USER_VER="versionnumber";
    private static final String USER_DEV="deviceid";

    static String getUserDetails(String queryEmail,String queryPassword){
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String jason=null;

        try{
            Uri builderURI=Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(USER_EMAIL,queryEmail)
                    .appendQueryParameter(USER_PASSWORD,queryPassword)
                    .appendQueryParameter(USER_MAC,"123")
                    .appendQueryParameter(USER_VER,"123")
                    .appendQueryParameter(USER_DEV,"123")
                    .build();
            URL requestURL=new URL(builderURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            System.out.println("hello");
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
