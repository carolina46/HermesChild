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
        Cursor chicos = db.rawQuery("select * from chico", null);
        if (chicos.moveToFirst()) {
            do {
                boolean[] categorias={chicos.getInt(5)==1,chicos.getInt(6)==1,chicos.getInt(7)==1,chicos.getInt(8)==1};
                aux.add(new Child(chicos.getColumnIndex("id_chico"), chicos.getString(1), chicos.getString(2), chicos.getInt(3)==0 /*sexo, 0=F*/, chicos.getInt(4),categorias));
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

    public void addChild(Child child) {
        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",child.getNombre());
        registro.put("apellido", child.getApellido());

        registro.put("sexo", child.getSexoF()?0:1 );
        registro.put("tamPictograma", child.getTamPictograma());

        boolean[] categorias=child.getCategorias();
        registro.put("pista", categorias[0]?1:0);
        registro.put("establo", categorias[1]?1:0);
        registro.put("necesidades", categorias[2]?1:0);
        registro.put("emociones", categorias[3]?1:0);
        db.insert("chico", null, registro);
        db.close();
    }

    public boolean childExist(String apellido, String nombre) {
        db = helper.getWritableDatabase();
        List<Child> aux = new ArrayList<Child>();
        Cursor chicos = db.rawQuery("select * from chico where nombre="+ nombre + ", apellido=" + apellido, null);
        boolean existe= chicos.moveToFirst();
        chicos.close();
        db.close();
        if(existe)return true;
        return false;
    }

    public void updateChild(Child child) {
        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre",child.getNombre());
        registro.put("apellido", child.getApellido());

        registro.put("sexo", child.getSexoF()?0:1 );
        registro.put("tamPictograma", child.getTamPictograma());

        boolean[] categorias=child.getCategorias();
        registro.put("pista", categorias[0]?1:0);
        registro.put("establo", categorias[1]?1:0);
        registro.put("necesidades", categorias[2]?1:0);
        registro.put("emociones", categorias[3] ? 1 : 0);
        db.update("chico", registro, "id_chico=" + child.getId(), null);
        db.close();



    }

    public boolean deleteChild(int id) {
        db = helper.getWritableDatabase();
        int cant=db.delete("chico", "id_chico=" + id, null);
        db.close();
        return cant==1;
    }


    public class DataBaseHelper extends SQLiteOpenHelper {

                private static final String DB_NAME = "comunicadorChild";
                private static final int DB_VERSION = 1;

                public DataBaseHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

                @Override
                public void onCreate(SQLiteDatabase db) {
                    db.execSQL("create table chico (id_chico Integer primary key autoincrement,nombre text, apellido text, sexo int, tamPictograma int, pista int, establo int, necesidades int, emociones int)");
                    db.execSQL("create table pictograma (id_pictograma Integer primary key autoincrement, nombre text, carpeta text)");
                    db.execSQL("create table pictogramaChico (id_pictogramaChico Integer primary key autoincrement, id_chico Integer, id_pictograma Integer)");
                    db.execSQL("create table configuracion ( id integer primary key, ip text, puerto text)");


                    //CARGA DEL UNICO REGISTRO DE LA TABLA CONFIGURACION
                    db.execSQL("insert into configuracion (id, ip, puerto) values (1, '192.56.100.69', '7065')");

                    //CARGA INICIAL DE TODOS LOS PICTOGRAMAS
                    db.execSQL("insert into pictograma (nombre, carpeta) values ('casco', 'pista')");

                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
            }

}
