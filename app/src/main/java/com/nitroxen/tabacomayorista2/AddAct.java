package com.nitroxen.tabacomayorista2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddAct extends AppCompatActivity {

    SQLiteDatabase db;
    EditText addProd, precioProd;
    Button action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db= new AsistDB(this).getWritableDatabase();
        addProd = findViewById(R.id.etAddProd);
        precioProd = findViewById(R.id.etPrecioProd);
        action = findViewById(R.id.btnAdd);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
                finish();
            }
        });


    }

    public void añadir(){
        if(!addProd.getText().toString().equals("")&&!precioProd.getText().toString().equals("")){
            ContentValues cv = new ContentValues();
            cv.put("nomProd",addProd.getText().toString());
            cv.put("precio",Double.parseDouble(precioProd.getText().toString()));
            db.insert("PRODUCTOS", null, cv);
        }
    }
}