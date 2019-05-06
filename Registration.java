package com.example.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        openHelper=new DatabaseHelper(this);
        final EditText username= (EditText) findViewById(R.id.user);
        final EditText firstname= (EditText) findViewById(R.id.firstname);
        final EditText lastname= (EditText) findViewById(R.id.lastname);
        final EditText phone= (EditText) findViewById(R.id.phone);
        final EditText password=(EditText) findViewById(R.id.pass);
        final EditText age= (EditText) findViewById(R.id.age);
        TextView tologin= (TextView) findViewById(R.id.tologin);
        Button register= (Button) findViewById(R.id.regbutton);
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                try {
                    String user = username.getText().toString().trim();
                    String fname = firstname.getText().toString();
                    String lname = lastname.getText().toString();
                    String pass = password.getText().toString();
                    String _age = age.getText().toString().trim();
                    String ph = phone.getText().toString().trim();
                    Cursor c=db.rawQuery("select * from " + DatabaseHelper.Table_Name + " where " + DatabaseHelper.user + "=\"" + user + "\"",null);
                    if(user.length()==0 || fname.length()==0 || pass.length()==0 || _age.length()==0 || ph.length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Try Entering all the details", Toast.LENGTH_SHORT).show();
                    }
                   else if(c.getCount()>0)
                    {
                        Toast.makeText(getApplicationContext(), "User Already Exist!, Please Login", Toast.LENGTH_LONG).show();
                    }
                    else if (ph.length() != 10) {
                        Toast.makeText(getApplicationContext(), "Please Enter 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        insertdata(user, fname, lname, _age, ph, pass);
                        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                catch(Exception e)
                {
                   e.printStackTrace();
                }

            }
        });
    }
    private void insertdata(String user, String fname, String lname, String _age, String ph, String pass){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.user, user);
        contentValues.put(DatabaseHelper.fname, fname);
        contentValues.put(DatabaseHelper.lname, lname);
        contentValues.put(DatabaseHelper.age, _age);
        contentValues.put(DatabaseHelper.mobile, ph);
        contentValues.put(DatabaseHelper.pass, pass);
        long id = db.insert(DatabaseHelper.Table_Name, null, contentValues);
    }
}
