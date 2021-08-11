package com.example.axientatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainInterface extends AppCompatActivity {
    public static final String EXTRA_MESSAGE="Extra.Message";
    public static String[] nameArray;
    public static String[] emailArray;
    public List<String> list;
    public List<String> list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);
        if (getIntent().hasExtra(Asyncable.EXTRA_MESSAGE)) {
            Intent intent = getIntent();
            String userName = intent.getStringExtra(Asyncable.EXTRA_MESSAGE);
            getSupportActionBar().setTitle(userName);
            list = new ArrayList<String>();
            list2 = new ArrayList<String>();
            AsyncTask<String, Void, Object[]> mu=new Asyncable2(list,list2).execute();
            try {
                Object[] fullList= mu.get();
                int halfValue=fullList.length/2;
                String[] tempNameList=new String[halfValue];
                String[] tempEmailList=new String[halfValue];
                for(int i=0;i<fullList.length/2;i++){
                    tempNameList[i]= String.valueOf(fullList[i]);
                    tempEmailList[i]= String.valueOf(fullList[i+halfValue]);
                }
                nameArray= tempNameList;
                emailArray= tempEmailList;
                recycle(nameArray,emailArray);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private void recycle(String[] names,String[] emails) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        RecyclerViewAdapter recyclerViewAdapter1;
        recyclerView=this.findViewById(R.id.recyclerView);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter1=new RecyclerViewAdapter(emails,names,this::onNoteClick);
        recyclerView.setAdapter(recyclerViewAdapter1);
        recyclerView.setHasFixedSize(true);
    }

    public void onNoteClick(int position) {
        Intent intent=new Intent(this, DisplayData.class);
        intent.putExtra( EXTRA_MESSAGE,nameArray[position]);
        startActivity(intent);
    }

}