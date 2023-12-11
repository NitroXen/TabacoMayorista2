package com.nitroxen.tabacomayorista2;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ActivityComprar extends AppCompatActivity {
    
    SQLiteDatabase db;
    Spinner spProductos;
    EditText cantidad;
    Button confirmar;
    ArrayList<Producto> datos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar);
        
        db = new AsistDB(this).getReadableDatabase();
        
        spProductos = findViewById(R.id.spProducto);
        cantidad = findViewById(R.id.etCantidad);
        confirmar = findViewById(R.id.btnConfirmarCompra);
        
        spProductos.setAdapter(getAdapter());

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirACesta();
                finish();
            }
        });
        
        
    }
    
    public ArrayAdapter<Producto> getAdapter(){
        datos = new ArrayList<>();
        Cursor c  =db.rawQuery("Select * from PRODUCTOS",null);

        int codNom = c.getColumnIndex("nomProd");
        int codPrecio = c.getColumnIndex("precio");

        if(c.moveToFirst()){
            do{datos.add(new Producto(c.getString(codNom),c.getDouble(codPrecio)));}while (c.moveToNext());
        }else{
            Toast.makeText(this, "No Hay Productos", Toast.LENGTH_SHORT).show();
        }
        c.close();
        return new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1,datos);
    }
    
    public void añadirACesta(){
        Producto p = (Producto) spProductos.getSelectedItem();

        int cant = cantidad.getText().toString().equals("")?0:Integer.parseInt(cantidad.getText().toString());
        double total = p.getPrecio()*cant;
        ContentValues cv = new ContentValues();
        cv.put("nombre",p.getNombre());
        cv.put("cantidad",cant);
        cv.put("precio",total);
        db.insert("CESTAS",null,cv);
    }
    
    
}