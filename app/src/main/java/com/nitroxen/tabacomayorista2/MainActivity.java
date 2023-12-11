package com.nitroxen.tabacomayorista2;

import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView lvCesta;
    Button compra, confirmar;
    ActivityResultLauncher<Intent> resultLauncher;
    TextView tvContabilidad;
    ArrayList<Cesta> datos;

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db  = new AsistDB(this).getReadableDatabase();
        lvCesta = findViewById(R.id.lvCesta);
        compra = findViewById(R.id.btnCompra);
        confirmar  = findViewById(R.id.btnConfirmar);
        tvContabilidad = findViewById(R.id.tvContabilidad);

        actUI();
        compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAComprar();
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {borrarTodo();}
        });

        lvCesta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cesta c = datos.get(i);
                db.execSQL("delete from CESTAS Where nombre=?",new String[]{c.getNomProd()});
                actUI();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        actUI();
    }

    public void actUI(){
        lvCesta.setAdapter(getAdapter());
        tvContabilidad.setText(getTextContabilidad());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.producto_menu,menu);
        return true;
    }

    public String getTextContabilidad(){
        StringBuilder msg = new StringBuilder();
        int numProd = 0;
        double precioTotal = 0;

        Cursor c = db.rawQuery("Select * from CESTAS",null);

        if(c.moveToFirst()){
            do {
                numProd+=c.getInt(2);
                precioTotal+=c.getDouble(3);
            }while (c.moveToNext());
        }


        msg.append("Productos=");
        msg.append(numProd);
        msg.append(" Precio:");
        msg.append(precioTotal);
        return msg.toString();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.idCreate:
                i = new Intent(this,AddAct.class);
                startActivity(i);
                return true;
            case R.id.idMod:
                i = new Intent(this,ModActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void irAComprar(){
        i = new Intent(this,ActivityComprar.class);
        startActivity(i);
    }

    public void borrarTodo(){
        db.execSQL("delete from CESTAS");
        actUI();
    }

    public ArrayAdapter<Cesta> getAdapter(){
        datos = new ArrayList<>();

        Cursor c = db.rawQuery("Select * from CESTAS",null);
        int codNom = c.getColumnIndex("nombre");
        int codCant = c.getColumnIndex("cantidad");
        int codPrecio = c.getColumnIndex("precio");

        if(c.moveToFirst()){
            do{
                Cesta cesta = new Cesta(c.getString(codNom),c.getInt(codCant),c.getDouble(codPrecio));
                datos.add(cesta);
            }while(c.moveToNext());
        }
        c.close();
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datos);
    }
}