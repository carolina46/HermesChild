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
                aux.add(new Child(chicos.getInt(0), chicos.getString(1), chicos.getString(2), chicos.getInt(3)==0 /*sexo, 0=F*/, chicos.getInt(4),categorias));
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
        Cursor chicos = db.rawQuery("select * from chico where nombre='"+nombre+"'" + " and apellido='"+apellido+"'", null);
        boolean existe= chicos.moveToFirst();
        chicos.close();
        db.close();
        return existe;
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
        int cant= db.delete("chico", "id_chico=" +Integer.toString(id), null);
        db.close();
    return cant>0;
    }

    public  List<Pictograma>  getPictogramasChild(int i) {
        db = helper.getWritableDatabase();
        Cursor pictogramas = db.rawQuery("select pictograma.id_pictograma, nombre, carpeta " +
                                    "from pictogramaChico inner join pictograma on (pictogramaChico.id_pictograma = pictograma.id_pictograma) " +
                                    "where id_chico="+Integer.toString(i), null);
        List<Pictograma> pictogramasChico = new ArrayList<Pictograma>();
        if (pictogramas.moveToFirst()) {
            do {
                pictogramasChico.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2)));
            }while (pictogramas.moveToNext());
        }

        db.close();
        pictogramas.close();
        return pictogramasChico;
    }


    public List<Pictograma> getPictogramasCategoria(String categoria) {
        db = helper.getWritableDatabase();
        Cursor pictogramas = db.rawQuery("select * from pictograma where carpeta='"+categoria+"'", null);
        List<Pictograma> pictogramasCategoria = new ArrayList<Pictograma>();
        if (pictogramas.moveToFirst()) {
            do {
                pictogramasCategoria.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2)));
            }while (pictogramas.moveToNext());
        }
        db.close();
        pictogramas.close();
        return pictogramasCategoria;
    }

    public void addPictogramaChico(int id_pictograma, int id_chico) {

        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("id_chico", id_chico);
        registro.put("id_pictograma", id_pictograma);
        db.insert("pictogramaChico", null, registro);
        db.close();

    }

    public void removePictogramaChico(int id_pictograma, int id_chico ) {
        db = helper.getWritableDatabase();
        int cant= db.delete("pictogramaChico", "id_pictograma="+Integer.toString(id_pictograma)+" and id_chico="+Integer.toString(id_chico) , null);
        db.close();
    }

    public List<Pictograma> getPictogramasCategoriaChico(String categoria, int id_nino) {
        db = helper.getWritableDatabase();
        Cursor pictogramas = db.rawQuery("select pictograma.id_pictograma, nombre, carpeta " +
                "from pictogramaChico inner join pictograma on (pictogramaChico.id_pictograma = pictograma.id_pictograma) " +
                "where id_chico="+Integer.toString(id_nino)+" and carpeta='"+categoria+"'", null);
        List<Pictograma> pictogramasChico = new ArrayList<Pictograma>();
        if (pictogramas.moveToFirst()) {
            do {
                pictogramasChico.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2)));
            }while (pictogramas.moveToNext());
        }

        db.close();
        pictogramas.close();
        return pictogramasChico;
    }

    public void deleteChildPictogramas(int id) {
        db = helper.getWritableDatabase();
        int cant= db.delete("pictogramaChico", "id_chico="+Integer.toString(id) , null);
        db.close();
    }


    public class DataBaseHelper extends SQLiteOpenHelper {

                private static final String DB_NAME = "comunicadorChild";
                private static final int DB_VERSION = 1;

                public DataBaseHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

                @Override
                public void onCreate(SQLiteDatabase db) {
                    db.execSQL("create table chico (id_chico Integer primary key autoincrement,nombre text, apellido text, sexo int, tamPictograma int, pista int, establo int, necesidades int, emociones int)");
                    db.execSQL("create table pictograma (id_pictograma Integer primary key, nombre text, carpeta text)");
                    db.execSQL("create table pictogramaChico (id_pictogramaChico Integer primary key autoincrement, id_chico Integer, id_pictograma Integer)");
                    db.execSQL("create table configuracion ( id integer primary key, ip text, puerto text)");
                    db.execSQL("create table notificacion (id_notificacion Integer primary key autoincrement, nombreChico text, contenidoPictograma text, categoriaPictograma text)");


                    //CARGA DEL UNICO REGISTRO DE LA TABLA CONFIGURACION
                    db.execSQL("insert into configuracion (id, ip, puerto) values (1, '192.56.100.69', '7065')");

                    //CARGA INICIAL DE TODOS LOS PICTOGRAMAS
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (1, 'casco', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (2, 'chapas', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (3, 'letras', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (4, 'cubos', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (5, 'maracas', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (6, 'palos', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (7, 'pato', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (8, 'pelota', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (9, 'riendas', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (10, 'burbujas', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (11, 'broches', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (12, 'aro', 'pista')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (13, 'tarima', 'pista')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (14, 'cepillo', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (15, 'limpieza', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (16, 'escarba', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (17, 'montura', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (18, 'matra', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (19, 'raqueta_dura', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (20, 'raqueta_blanda', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (21, 'pasto', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (22, 'zanahoria', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (23, 'caballo_b', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (24, 'caballo_m', 'establo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (25, 'caballo_n', 'establo')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (26, 'bano', 'necesidades')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (27, 'sed', 'necesidades')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (28, 'seed', 'necesidades')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (29, 'dolorida', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (30, 'dolorido', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (31, 'cansada', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (32, 'cansado', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (33, 'triste', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (34, 'tristee', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (35, 'sorprendida', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (36, 'sorprendido', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (37, 'asustado', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (38, 'asustada', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (39, 'contenta', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (40, 'contento', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (41, 'enojada', 'emociones')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta) values (42, 'enojado', 'emociones')");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
            }

}
