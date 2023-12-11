package com.nitroxen.tabacomayorista2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ModActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Spinner spProd;
    EditText etPrecio;
    Button btnAct;
    ArrayList<Producto> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);
        db = new AsistDB(this).getReadableDatabase();
        spProd = findViewById(R.id.spProducto);
        etPrecio = findViewById(R.id.etModPrecio);
        btnAct = findViewById(R.id.btnAct);
        spProd.setAdapter(getAdapter());

        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mod();
                finish();
            }
        });

    }




    public void anadirPrecio(){
        spProd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Producto p = (Producto) parent.getItemAtPosition(position);
                etPrecio.setText(Double.toString(p.getPrecio()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public ArrayAdapter<Producto> getAdapter(){
        datos = new ArrayList<>();
        Cursor c  =db.rawQuery("Select * from PRODUCTOS",null);

        int codNom = c.getColumnIndex("nomProd");
        int codPrecio = c.getColumnIndex("precio");
        while(c.moveToNext()){
            datos.add(new Producto(c.getString(codNom),c.getDouble(codPrecio)));
        }
        c.close();
        anadirPrecio();
        return new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1,datos);
    }

    public void mod(){
        Producto p = (Producto) spProd.getSelectedItem();
        double newPrecio = etPrecio.getText().toString().equals("")?0:Double.parseDouble(etPrecio.getText().toString());
        if(newPrecio == 0){
            db.execSQL("delete from PRODUCTOS where nomProd =?",new Object[]{p.getNombre()});
        }else{
            db.execSQL("UPDATE PRODUCTOS SET precio =? WHERE nomProd =?",new Object[]{newPrecio,p.getNombre()});
        }
    }

}