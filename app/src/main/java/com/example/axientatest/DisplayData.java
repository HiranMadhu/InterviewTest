package com.example.axientatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Typeface.BOLD;

public class DisplayData extends AppCompatActivity {
    public String listStringId=null;
    public String listStringDis=null;
    DBHelper db;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        if (getIntent().hasExtra(MainInterface.EXTRA_MESSAGE)) {
            Intent intent = getIntent();
            String userName = intent.getStringExtra(MainInterface.EXTRA_MESSAGE);
            getSupportActionBar().setTitle(userName);
            name=userName;
        }
        db=new DBHelper(this);
        refresh();
    }

    public void onStart() {
        super.onStart();
        Button saveButton=findViewById(R.id.saveButton);
        Button canselButton=findViewById(R.id.canselButton);
        TextView buttonAdd=findViewById(R.id.addButton);
        TextView buttonClear=findViewById(R.id.clearButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView idEdit=findViewById(R.id.taskId);
                TextView disEdit=findViewById(R.id.taskDescription);
                String newItemDis=disEdit.getText().toString();
                String newItemId=idEdit.getText().toString();

                if(listStringId==null){
                    listStringId=newItemId;
                    listStringDis=newItemDis;
                }else {
                    listStringId = listStringId+ "." + newItemId ;
                    listStringDis = listStringDis+ "." + newItemDis ;
                }
                db.insertUserData(name,listStringId,listStringDis);
                db.updateUserData(name,listStringId,listStringDis);
                refresh();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStringId=null;
                String[] separatedItems ={};
                db.deleteData(name);
                setList(separatedItems,separatedItems);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button okButton=findViewById(R.id.saveButton);
                Button notOkButton=findViewById(R.id.canselButton);
                EditText editTaskId=findViewById(R.id.taskId);
                EditText editDescription=findViewById(R.id.taskDescription);
                editTaskId.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.VISIBLE);
                okButton.setVisibility(View.VISIBLE);
                notOkButton.setVisibility(View.VISIBLE);
            }
        });

        canselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button okButton=findViewById(R.id.saveButton);
                Button notOkButton=findViewById(R.id.canselButton);
                EditText editText=findViewById(R.id.taskId);
                EditText editText2=findViewById(R.id.taskDescription);
                editText.setVisibility(View.GONE);
                editText2.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
                notOkButton.setVisibility(View.GONE);
            }
        });


    }


    public void setList(String[] items,String[] items2){
        String[] display=new String[items.length];
        TextView noOFItem=findViewById(R.id.itemNoOnList);
        if(items.length==0){
            noOFItem.setVisibility(View.INVISIBLE);
        }
        else{
            noOFItem.setVisibility(View.VISIBLE);
            noOFItem.setText(String.valueOf(items.length)+"  items");
        }

        for(int i=0;i<items.length;i++){
            SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder("");
            SpannableString spanText =new SpannableString(items[i]+"\n"+items2[i]);
            spannableStringBuilder.append(spanText);
            display[i]= String.valueOf(spannableStringBuilder);
        }
        ArrayAdapter<String> adapter;
        ListView listView;
        listView=findViewById(R.id.listView_data);
        adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,display);
        listView.setAdapter(adapter);
    }

    public void refresh(){
        Cursor res = db.getOneUserData(name);
        if (res.getCount() == 0) {
            Toast.makeText(DisplayData.this, "No data Available", Toast.LENGTH_LONG).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(1));
            buffer2.append(res.getString(2));
        }
        String allData=buffer.toString();
        String[] separatedItems = allData.split("\\.");
        String allData2=buffer2.toString();
        String[] separatedItems2 = allData2.split("\\.");
        setList(separatedItems,separatedItems2);
    }
}