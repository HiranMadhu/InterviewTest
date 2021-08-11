package com.example.axientatest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    public static final String EXTRA_MESSAGE="Extra.Message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void check(View view){
        progressBar=findViewById(R.id.progressBar);
        EditText userEmail=findViewById(R.id.editTextTextEmailAddress);
        EditText password=findViewById(R.id.editTextTextPassword);
        String QueryName=userEmail.getText().toString();
        String QueryPassword=password.getText().toString();
        new Asyncable(progressBar,this).execute(QueryName,QueryPassword);
    }
}