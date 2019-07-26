package com.example.khongmuonchepphat.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLHelper";
    static final String DB_Name="User.db";
    static final String DB_NameTable="User";
    static final int version=2;

    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLHelper(Context context) {
        super(context, DB_Name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //trường cột ko viết hoa  oki a ^^
        String queryTable= "CREATE TABLE User (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "username Text," +
                "password Text )";
        sqLiteDatabase.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1){
            sqLiteDatabase.execSQL("drop table if exists "+ DB_NameTable);
            onCreate(sqLiteDatabase);
        }
    }

    public void insertUser(String username, String password){
        sqLiteDatabase = getWritableDatabase();
        contentValues=new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        sqLiteDatabase.insert(DB_NameTable, null, contentValues);
        closeDB();
    }

    public void getAllUser(){
        sqLiteDatabase= getReadableDatabase();
        cursor= sqLiteDatabase.query(false, DB_NameTable, null, null, null,
        null, null, null, null);

        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String name= cursor.getString(cursor.getColumnIndex("username"));
            String pass= cursor.getString(cursor.getColumnIndex("password"));
            Log.d(TAG, "getAllUser: "+"id - "+id+" - username - "+name+" - password - "+pass);
        }
        closeDB();
    }

    public List<User> getAllUserAdvanced(){
        List<User> users= new ArrayList<>();
        User user;
        sqLiteDatabase= getReadableDatabase();
        cursor= sqLiteDatabase.query(false, DB_NameTable, null, null, null, null, null,
                null, null);

        while (cursor.moveToNext()){
            String name= cursor.getString(cursor.getColumnIndex("username"));
            String pass= cursor.getString(cursor.getColumnIndex("password"));
            user= new User(name, pass);
            users.add(user);
        }
        closeDB();
        return users;
    }
    private void closeDB(){
        if(sqLiteDatabase!=null)    sqLiteDatabase.close();
        if (contentValues!=null)    contentValues.clear();
        if (cursor!=null) cursor.close();
    }
}
