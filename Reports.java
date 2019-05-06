package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    TextView user;
    GridView gridView;
    ArrayList<image> list;
    imageadapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        openHelper=new DatabaseHelper(this);
        user=(TextView) findViewById(R.id.tv3);
        Intent intent=getIntent();
        final String username=intent.getStringExtra("uname");
        user.setText(username);
        gridView=(GridView) findViewById(R.id.gridView);
        list=new ArrayList<>();
        adapter=new imageadapter(this, R.layout.images, list);
        gridView.setAdapter(adapter);

        Cursor res1=getallimages();
        list.clear();
        while(res1.moveToNext())
        {
            byte[] image=res1.getBlob(0);
            list.add(new image(image));
        }
        adapter.notifyDataSetChanged();
    }
    private Cursor getallimages() {
        db = openHelper.getWritableDatabase();

        Cursor res1 = db.rawQuery("select " + DatabaseHelper.reports + " from " + DatabaseHelper.Table_Name2 + " where " + DatabaseHelper.user2 + "=\"" + user.getText() + "\"", null);
        return res1;
    }
}
