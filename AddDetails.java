package com.example.project1;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static com.example.project1.DatabaseHelper.Table_Name2;
import static com.example.project1.DatabaseHelper.user2;

public class AddDetails extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Button addvisit,back,addreports;
    TextView user;
    EditText docname,date,time,drugs,prob;
    ImageView i1;
    final int rcg=999;
    public static final String id = "ID";
    public static final String TAG="AddDetails";
    public DatePickerDialog.OnDateSetListener onDateSetListener;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int hours;
    int minutes;
    String ampm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        openHelper=new DatabaseHelper(this);
        addvisit=(Button) findViewById(R.id.addvisit);
        prob=(EditText) findViewById(R.id.problem);
        addreports=(Button) findViewById(R.id.addrep);
        i1=(ImageView) findViewById(R.id.iv1);
        back=(Button) findViewById(R.id.back);
        user=(TextView) findViewById(R.id.tv3);
        docname=(EditText) findViewById(R.id.docname);
        date=(EditText) findViewById(R.id.date);
        time=(EditText) findViewById(R.id.time);
        drugs=(EditText) findViewById(R.id.medicines);
        //reports=(EditText) findViewById(R.id.reports);
        Intent intent=getIntent();
        final String username=intent.getStringExtra("uname");
        user.setText(username);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        AddDetails.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        onDateSetListener,
                        year,month,day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "-" + dayOfMonth + "-" + year);
                String _date=dayOfMonth + "/" + month + "/" +year;
                date.setText(_date);

            }
        };
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                hours=calendar.get(Calendar.HOUR);
                minutes=calendar.get(Calendar.MINUTE);
                timePickerDialog=new TimePickerDialog(AddDetails.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        /*if(hour>=12)
                        {
                            ampm="P.M.";
                        }
                        else
                        {
                            ampm="A.M.";
                        }*/
                        time.setText(String.format("%02d:%02d", hour, minute));
                       // time.setText(hour + ":" + minute);
                    }
                }, hours, minutes, false);
                timePickerDialog.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDetails.this, Main_view.class);
                intent.putExtra("uname", username);
                startActivity(intent);
            }
        });
        addvisit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                /*db.execSQL("Drop table if exists " + Table_Name2);

                String CREATE_CONTACTS_TABLE = "CREATE TABLE " + Table_Name2 + "("
                        + id + " INTEGER primary key autoincrement," + user2 + " text not null,"
                        + docname1 + " text not null," + date1 + " date not null," + time1 + " text not null," + drugs1 + " text not null," + reports1 + " text)";
                db.execSQL(CREATE_CONTACTS_TABLE);*/


                 try {
                    String uname = user.getText().toString();
                    String doctname = docname.getText().toString();
                    String _date = date.getText().toString();
                    String _time = time.getText().toString();
                    String _med = drugs.getText().toString();
                    String problem=prob.getText().toString();
                    //String _reports = reports.getText().toString();
                    insertdata(uname, doctname, _date, _time, problem, _med, imageViewToByte(i1));
                    Toast.makeText(getApplicationContext(), "Visit Added Successfully", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AddDetails.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        rcg
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == rcg){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, rcg);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "You Don't Have Permission to access files!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==rcg && resultCode==RESULT_OK && data!= null)
        {
            Uri uri=data.getData();
            try {
                //assert uri != null;
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                i1.setImageBitmap(bitmap);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void insertdata(String uname, String doctname, String date, String time, String prob, String drugs, byte[] reports){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.user2, uname);
        contentValues.put(DatabaseHelper.docname, doctname);
        contentValues.put(DatabaseHelper.date, date);
        contentValues.put(DatabaseHelper.time, time);
        contentValues.put(DatabaseHelper.problem, prob);
        contentValues.put(DatabaseHelper.drugs, drugs);
        contentValues.put(DatabaseHelper.reports, reports);
        db.insert(DatabaseHelper.Table_Name2, null, contentValues);
    }
    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytearray=stream.toByteArray();
        return bytearray;
    }
}
