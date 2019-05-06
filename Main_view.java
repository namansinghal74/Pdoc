package com.example.project1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main_view extends AppCompatActivity {
    Button addrecord, viewrecord, personaldoctor, drughistory, logout, viewreports;
    TextView user, hlo;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        openHelper = new DatabaseHelper(this);
        addrecord = (Button) findViewById(R.id.AddRec);
        viewrecord = (Button) findViewById(R.id.ViewRec);
        personaldoctor = (Button) findViewById(R.id.pd);
        drughistory = (Button) findViewById(R.id.drughis);
        logout = (Button) findViewById(R.id.logout);
        user = (TextView) findViewById(R.id.tv3);
        hlo = (TextView) findViewById(R.id.tv2);
        viewreports=(Button) findViewById(R.id.viewrep);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("uname");
        user.setText(username);
        viewreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_view.this, Reports.class);
                intent.putExtra("uname", username);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_view.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_view.this, AddDetails.class);
                intent.putExtra("uname", username);
                startActivity(intent);
            }
        });
        personaldoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_view.this, PersonalDoc.class);
                startActivity(intent);
            }
        });
        viewrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = getalldata();

                if (res.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Doctor's Name: " + res.getString(0) + "\n");
                    buffer.append("Date of Visit: " + res.getString(1) + "\n");
                    buffer.append("Time of Visit: " + res.getString(2) + "\n");
                    buffer.append("Problem Description: " + res.getString(3) + "\n");
                    buffer.append("Medicines: " + res.getString(4) + "\n\n");
                   // buffer.append("Reports: " + res.getString(4) + "\n\n");

                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Visit Details");
                alertDialogBuilder
                        .setMessage(buffer.toString())
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        drughistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res1 = getalldrugs();
                if (res1.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
                StringBuffer buffer1 = new StringBuffer();
                while (res1.moveToNext()) {
                    buffer1.append("Drug Names: " + res1.getString(0) + "\t\t");
                    buffer1.append("Date: " + res1.getString(1) + "\n\n");

                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Drugs History");
                alertDialogBuilder
                        .setMessage(buffer1.toString())
                        .setCancelable(false)
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    private Cursor getalldata() {
        db = openHelper.getWritableDatabase();

        Cursor res = db.rawQuery("select " + DatabaseHelper.docname + ", " + DatabaseHelper.date + ", " + DatabaseHelper.time + ", " + DatabaseHelper.problem + ", " + DatabaseHelper.drugs + " from " + DatabaseHelper.Table_Name2 + " where " + DatabaseHelper.user2 + "=\"" + user.getText() + "\"", null);
        return res;
    }

    private Cursor getalldrugs() {
        db = openHelper.getWritableDatabase();

        Cursor res1 = db.rawQuery("select " + DatabaseHelper.drugs + ", " + DatabaseHelper.date + " from " + DatabaseHelper.Table_Name2 + " where " + DatabaseHelper.user2 + "=\"" + user.getText() + "\"", null);
        return res1;
    }
}

