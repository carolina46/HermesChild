package com.laboratorio.hermesperezmunoa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carolina on 25/01/2016.
 */
public class DataBaseManager {

    private DataBaseHelper helper;
    private SQLiteDatabase db;
    private List<String> configuration;

    public DataBaseManager(Context context) {
        helper = new DataBaseHelper(context);

    }

    public List<Child> listChild(){
        db = helper.getWritableDatabase();
        List<Child> aux = new ArrayList<Child>();
        Cursor chicos = db.rawQuery("select nombre,apellido,id_chico from chico", null);
        if (chicos.moveToFirst()) {
            do {
                aux.add(new Child(chicos.getString(0), chicos.getString(1), Integer.parseInt(chicos.getString(2))));
            }while (chicos.moveToNext());
        }
        chicos.close();
        db.close();
        return aux;
    }



    public List<String> getConfiguration(){
        db = helper.getWritableDatabase();
        List<String> aux = new ArrayList<String>();
        Cursor configuracionGeneral = db.rawQuery("select ip, puerto, id from configuracion", null);
        if (configuracionGeneral.moveToFirst()) {
            aux.add(configuracionGeneral.getString(0));
            aux.add(configuracionGeneral.getString(1));
        }
        configuracionGeneral.close();
        db.close();
        return aux;
    }



    public void setConfiguration(List<String> configuration) {
        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("ip", configuration.get(0));
        registro.put("puerto", configuration.get(1));
        db.update("configuracion", registro, "id=" + 1, null);
        db.close();

    }


    public class DataBaseHelper extends SQLiteOpenHelper {

                private static final String DB_NAME = "comunicadorChild";
                private static final int DB_VERSION = 1;

                public DataBaseHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

                @Override
                public void onCreate(SQLiteDatabase db) {
                    db.execSQL("create table chico (id_chico Integer primary key autoincrement,nombre text, apellido text, sexo bool, tamPictograma int, pista bool, establo bool, necesidades bool, emociones bool)");
                    db.execSQL("create table pictograma (id_pictograma Integer primary key autoincrement, nombre text, carpeta text)");
                    db.execSQL("create table pictogramaChico (id_pictogramaChico Integer primary key autoincrement, id_chico Integer, id_pictograma Integer)");
                    db.execSQL("create table configuracion ( id integer primary key, ip text, puerto text)");


                    //CARGA DEL UNICO REGISTRO DE LA TABLA CONFIGURACION
                    db.execSQL("insert into configuracion (id, ip, puerto) values (1, '192.56.100.69', '7065')");

                    //CARGA INICIAL DE TODOS LOS PICTOGRAMAS
                    db.execSQL("insert into pictograma (nombre, carpeta) values ('casco', 'pista')");

                    //EJEMPLO DE NINO
                    db.execSQL("insert into chico (nombre, apellido ) values ('Sandra', 'Gulli')");
                    db.execSQL("insert into chico (nombre, apellido) values ('Carolina', 'Perez')");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
            }

}
