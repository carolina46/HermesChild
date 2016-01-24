package com.laboratorio.hermesperezmunoa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BD extends SQLiteOpenHelper {

    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table chico (id_chico int primary key,nombre text, apellido text, sexo bool, tamPictograma int, pista bool, establo bool, necesidades bool, emociones bool)");
        db.execSQL("create table configuracion (ip int, puerto int)");
        db.execSQL("create table pictograma-chico (id_chico int, id_pictograma int)");

        db.execSQL("create table pictograma (id_pictograma int primary key, nombre text, carpeta text)");

        db.execSQL("insert into pictograma (nombre, carpeta) values ('casco', 'pista')");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
