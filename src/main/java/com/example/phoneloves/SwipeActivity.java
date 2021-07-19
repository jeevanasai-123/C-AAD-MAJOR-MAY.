package com.example.phoneloves;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SwipeActivity extends AppCompatActivity {
    EditText name,phone;
    Button insert,update,delete,view;
    DBHelper DB;

   float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String phoneTXT = phone.getText().toString();

                Boolean checkinsertdata = DB.insertuserdata(nameTXT,phoneTXT);
                if(checkinsertdata==true)
                    Toast.makeText(SwipeActivity.this,"New Entry Inserted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SwipeActivity.this,"New Entry Not Inserted",Toast.LENGTH_SHORT).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String phoneTXT = phone.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(nameTXT,phoneTXT);
                if(checkupdatedata==true)
                    Toast.makeText(SwipeActivity.this," Entry Updated",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SwipeActivity.this,"New Entry Not Updated",Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();


                Boolean checkdeletedata = DB.deletedata(nameTXT);
                if(checkdeletedata==true)
                    Toast.makeText(SwipeActivity.this," Entry Deleted",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SwipeActivity.this,"Entry Not Deleted",Toast.LENGTH_SHORT).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(SwipeActivity.this,"No Entry Exists",Toast.LENGTH_SHORT);
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name:"+res.getString(0)+"\n");
                    buffer.append("Phone:"+res.getString(1)+"\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(SwipeActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1<x2){
                    Intent i = new Intent(SwipeActivity.this,SwipeLeft.class);
                    startActivity(i);
                }else if(x1>x2) {
                    Intent i = new Intent(SwipeActivity.this,SwipeRight.class);
                    startActivity(i);

                }
                break;
        }
        return false;
    }
}