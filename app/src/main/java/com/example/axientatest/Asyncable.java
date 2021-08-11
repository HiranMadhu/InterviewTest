package com.example.axientatest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class Asyncable extends AsyncTask<String,Void,String> {

    private ProgressBar progressBar;
    private Context context;
    public static final String EXTRA_MESSAGE="Extra.Message";


    public Asyncable(ProgressBar pro,Context context){
        this.progressBar=pro;
        this.context=context;

    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtilities.getUserDetails(strings[0],strings[1]);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try{
            progressBar.setVisibility(View.VISIBLE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray jsonObject=new JSONArray(s);

            JSONObject jasonObjectTemp=jsonObject.getJSONObject(0);
            JSONArray jasonLogin=jasonObjectTemp.getJSONArray("LoginDetails");

            int i=0;
            String tempName=null;

            while (i<jasonLogin.length() && tempName==null){
                JSONObject firstJason=jasonLogin.getJSONObject(i);
                try{
                    tempName=firstJason.getString("FirstName");
                }catch (Exception e){e.printStackTrace();}
                i++;
                if (tempName!=null){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(context, "Welcome "+tempName, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,MainInterface.class);
                    intent.putExtra(EXTRA_MESSAGE,tempName);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
