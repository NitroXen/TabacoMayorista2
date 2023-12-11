package com.nitroxen.tabacomayorista2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AsistDB extends SQLiteOpenHelper {

    private static final String NAME_DB= "Comprador.db";
    private static final int VER_DB = 1;

    public AsistDB(Context context) {
        super(context,NAME_DB, null,VER_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table PRODUCTOS(nomProd text primary key, precio double);";
        String sql2 = " create table CESTAS(idCesta integer primary key autoincrement, nombre text, cantidad integer, precio double);";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
