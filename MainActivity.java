package com.example.project1;

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

public class MainActivity extends AppCompatActivity {
    //DatabaseHelper mydb;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    EditText username,password;
    Button login;
    TextView reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mydb=new DatabaseHelper(this);
        openHelper=new DatabaseHelper(this);
        db=openHelper.getReadableDatabase();
        username= findViewById(R.id.loginusername);
        password= findViewById(R.id.loginpassword);
        login= findViewById(R.id.loginbutton);
        reg= findViewById(R.id.reg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db=openHelper.getReadableDatabase();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                //Boolean res = mydb.checkuser(user, pass);
                cursor=db.rawQuery("select * From " + DatabaseHelper.Table_Name + " where "+ DatabaseHelper.user + " =? and " + DatabaseHelper.pass + " =?;", new String[]{user,pass});
                if(username.length()==0 || password.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Please Enter both Username and Password", Toast.LENGTH_SHORT).show();
                }
               else if(cursor!=null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToNext();
                        //Toast.makeText(getApplicationContext(), "login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Main_view.class);
                        intent.putExtra("uname", user);
                        startActivity(intent);
                    }


                      else {
                        Toast.makeText(getApplicationContext(), "Error in Username or Password!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Register First", Toast.LENGTH_SHORT).show();
                }


            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}
