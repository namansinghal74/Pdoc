package com.example.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String Database_Name = "epres.db";
    public static final String Table_Name = "patient";
    public static final String fname = "Firstname";
    public static final String lname = "Lastname";
    public static final String age = "Age";
    public static final String mobile = "Phone";
    public static final String user = "Username";
    public static final String pass = "Password";
    public static final String Table_Name2 = "details";
    public static final String id = "ID";
    public static final String user2 = "Username";
    public static final String date = "Date";
    public static final String time = "Time";
    public static final String drugs = "Drugs";
    public static final String reports = "Reports";
    public static final String problem= "Problem";
    public static final String docname = "Doctor_Name";
    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Table_Name2 + "("
            + id + " INTEGER primary key autoincrement," + user2 + " text not null,"
            + docname + " text not null," + date + " date not null," + time + " text not null," + problem + " text," + drugs + " text not null," + reports + " blob, foreign key (" + user2 + ") references " + Table_Name + "(" + user + "));";

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, 1);
        //SQLiteDatabase db=this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL("create table " + Table_Name + "(" + user + " text primary key not null," + fname + " text not null," + lname + " text," + age + " int not null," + mobile + " int," + pass + " text not null)");
        db.execSQL(CREATE_CONTACTS_TABLE);
        /* CREATE_CONTACTS_TABLE = "CREATE TABLE " + Table_Name2 + "("
                + id + " INTEGER primary key autoincrement," + user2 + " text not null,"
                + docname + " text not null," + date + " date not null," + time + " text not null," + drugs + " text not null," + reports + " text)";
        db.execSQL(CREATE_CONTACTS_TABLE);//"create table " + Table_Name2 + "(" + id + " int primary key autoincrement," + user2 + " text not null," + docname + " text not null," + date + " date not null," + time + " text not null," + drugs + " text not null," + reports + " text"+")");
        // foreign key (" + user2 + ") references " + Table_Name + "(" + user + "))");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + Table_Name);
        db.execSQL("Drop table if exists " + Table_Name2);
        onCreate(db);
    }

}

