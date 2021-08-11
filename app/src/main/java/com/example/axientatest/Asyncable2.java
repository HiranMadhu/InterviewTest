package com.example.axientatest;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Asyncable2 extends AsyncTask<String, Void, Object[]> {

    public static final String EXTRA_MESSAGE="Extra.Message";
    public List temp1;
    public List temp2;

    public Asyncable2(List my,List my2){
        this.temp1=my;
        this.temp2=my2;
    }

    @Override
    protected Object[] doInBackground(String... strings) {
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String jason=null;
        try{
            Uri builderURI=Uri.parse("https://jsonplaceholder.typicode.com/users").buildUpon().build();
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
        try {
            JSONArray jsonObject=new JSONArray(jason);
            int i=0;
            String tempName=null;
            String tempEmail=null;

            while (i<jsonObject.length()){
                JSONObject firstJason=jsonObject.getJSONObject(i);
                try{
                    tempName=firstJason.getString("name");
                    tempEmail=firstJason.getString("email");
                }catch (Exception e){e.printStackTrace();}
                i++;
                if (tempName!=null){
                    System.out.println(temp1);
                    temp1.add(tempName);
                    temp2.add(tempEmail);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> newList = new ArrayList<>(temp2.size() + temp1.size());
        newList.addAll(temp1);
        newList.addAll(temp2);
        return newList.toArray();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}
