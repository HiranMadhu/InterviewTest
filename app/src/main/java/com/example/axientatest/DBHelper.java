package com.example.axientatest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);//name of the database as Userdata.db
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(name TEXT primary key, id TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists UserDetails");
    }
    //Create a function to insert user data
    public Boolean insertUserData(String name, String id,String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("id", id);
        contentValues.put("description", description);

        long result = DB.insert("UserDetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Create a function to update user data
    public Boolean updateUserData(String name, String id,String description) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("id", id);
        contentValues.put("description", description);

        Cursor cursor = DB.rawQuery("Select * from UserDetails where name = ?", new String[]{name});

        if (cursor.getCount() > 0) {
            long result = DB.update("UserDetails", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }

    //Create a function to delete data
    public Boolean deleteData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("UserDetails", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }


    //Create a function to get all the data in userDetails table
    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails",null);
        return cursor;
    }
    //function to get the date that corresponds to a particular name
    public Cursor getOneUserData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails where name =?",new String[]{name});
        return cursor;
    }

    //function to get the list that corresponds to a particular name
    public Cursor getList() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select list from UserDetails where name =?",new String[]{"user1"});
        return cursor;
    }

    //function to get the quantity that corresponds to a particular name
    public Cursor getQuantity() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select quantity from UserDetails where name =?",new String[]{"user1"});
        return cursor;
    }
}
