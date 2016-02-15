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
        Cursor pictogramas = db.rawQuery("select pictograma.id_pictograma, nombre, carpeta, nombreParaNoti " +
                                    "from pictogramaChico inner join pictograma on (pictogramaChico.id_pictograma = pictograma.id_pictograma) " +
                                    "where id_chico="+Integer.toString(i)+ " order by carpeta", null);
        List<Pictograma> pictogramasChico = new ArrayList<Pictograma>();
        if (pictogramas.moveToFirst()) {
            do {
                pictogramasChico.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2), pictogramas.getString(3)));
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
                pictogramasCategoria.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2),pictogramas.getString(3) ));
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
        Cursor pictogramas = db.rawQuery("select pictograma.id_pictograma, nombre, carpeta, nombreParaNoti " +
                "from pictogramaChico inner join pictograma on (pictogramaChico.id_pictograma = pictograma.id_pictograma) " +
                "where id_chico="+Integer.toString(id_nino)+" and carpeta='"+categoria+"'", null);
        List<Pictograma> pictogramasChico = new ArrayList<Pictograma>();
        if (pictogramas.moveToFirst()) {
            do {
                pictogramasChico.add(new Pictograma(pictogramas.getInt(0), pictogramas.getString(1),pictogramas.getString(2),pictogramas.getString(3)));
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

    public void addNotificacion(String nombreChico, String contenidoPictograma, String categoriaPictograma) {
        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombreChico", nombreChico);
        registro.put("contenidoPictograma", contenidoPictograma);
        registro.put("categoriaPictograma", categoriaPictograma);
        db.insert("notificacion", null, registro);
        db.close();

    }

    public  List<Notificacion> getNotifications() {
        db = helper.getWritableDatabase();
        Cursor dbQuery = db.rawQuery("select * from notificacion", null);
        List<Notificacion> notificaciones = new ArrayList<Notificacion>();

        if (dbQuery.moveToFirst()) {
            do {
                notificaciones.add(new Notificacion(dbQuery.getInt(0), dbQuery.getString(1), dbQuery.getString(2), dbQuery.getString(3)));
            }while (dbQuery.moveToNext());
        }

        db.close();
        dbQuery.close();
        return notificaciones;
    }

    public void deleteNotifications(List<Notificacion> notificationList) {
        db = helper.getWritableDatabase();
        for (Notificacion notification :notificationList) {
            db.delete("notificacion", "id_notificacion='"+ notification.getId() +"'" , null);
        }
        db.close();
    }

    public void addChildSelected(int id) {
        db = helper.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("id",id);
        db.update("chicoSeleccionado", registro, "id_chicoSeleccionado=" + 1, null);
        db.close();
    }

    public Child getChildSelected(){
        Child c= null;
        db = helper.getWritableDatabase();
        Cursor dbQuery = db.rawQuery("select * from chicoSeleccionado", null);
        if (dbQuery.moveToFirst()) {
            Cursor chicos = db.rawQuery("select * from chico where id_chico="+ dbQuery.getInt(1), null);
            if(chicos.moveToFirst()){
                boolean[] categorias={chicos.getInt(5)==1,chicos.getInt(6)==1,chicos.getInt(7)==1,chicos.getInt(8)==1};
                c=new Child(chicos.getInt(0), chicos.getString(1), chicos.getString(2), chicos.getInt(3) == 0 /*sexo, 0=F*/, chicos.getInt(4),categorias);
            }


        }

        db.close();
        dbQuery.close();
        return c;

    }

    public class DataBaseHelper extends SQLiteOpenHelper {

                private static final String DB_NAME = "comunicadorChild";
                private static final int DB_VERSION = 1;

                public DataBaseHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);}

                @Override
                public void onCreate(SQLiteDatabase db) {
                    db.execSQL("create table chico (id_chico Integer primary key autoincrement,nombre text, apellido text, sexo int, tamPictograma int, pista int, establo int, necesidades int, emociones int)");
                    db.execSQL("create table pictograma (id_pictograma Integer primary key, nombre text, carpeta text, nombreParaNoti text)");
                    db.execSQL("create table pictogramaChico (id_pictogramaChico Integer primary key autoincrement, id_chico Integer, id_pictograma Integer)");
                    db.execSQL("create table configuracion ( id integer primary key, ip text, puerto text)");
                    db.execSQL("create table notificacion (id_notificacion Integer primary key autoincrement, nombreChico text, contenidoPictograma text, categoriaPictograma text)");
                    db.execSQL("create table chicoSeleccionado (id_chicoSeleccionado Integer primary key autoincrement, id int)");

                    //Reservo lugar para child seleccionado
                    db.execSQL("insert into chicoSeleccionado (id_chicoSeleccionado, id) values (1, 0)");

                    //CARGA DEL UNICO REGISTRO DE LA TABLA CONFIGURACION
                    db.execSQL("insert into configuracion (id, ip, puerto) values (1, '192.168.100.69', '8765')");

                    //CARGA INICIAL DE TODOS LOS PICTOGRAMAS
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (1, 'casco', 'pista', 'Casco')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (2, 'chapas', 'pista', 'Chapas')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (3, 'letras', 'pista', 'Letras')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (4, 'cubos', 'pista', 'Cubos')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (5, 'maracas', 'pista', 'Maracas')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (6, 'palos', 'pista', 'Palos')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (7, 'pato', 'pista', 'Pato')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (8, 'pelota', 'pista', 'Pelota')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (9, 'riendas', 'pista', 'Riendas')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (10, 'burbujas', 'pista', 'Burbujas')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (11, 'broches', 'pista', 'Broches')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (12, 'aro', 'pista', 'Aro')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (13, 'tarima', 'pista', 'Tarima')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (14, 'cepillo', 'establo', 'Cepillo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (15, 'limpieza', 'establo', 'Limpieza')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (16, 'escarba', 'establo', 'Escarba vasos')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (17, 'montura', 'establo', 'Montura')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (18, 'matra', 'establo', 'Matra')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (19, 'raqueta_dura', 'establo', 'Raqueta Dura')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (20, 'raqueta_blanda', 'establo', 'Raqueta Blanda')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (21, 'pasto', 'establo', 'Pasto')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (22, 'zanahoria', 'establo', 'Zanahoria')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (23, 'caballo_b', 'establo', 'Caballo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (24, 'caballo_m', 'establo', 'Caballo')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (25, 'caballo_n', 'establo', 'Caballo')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (26, 'bano', 'necesidades', 'Bano')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (27, 'sed', 'necesidades', 'Sed')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (28, 'seed', 'necesidades', 'Sed')");

                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (29, 'dolorida', 'emociones', 'Dolorida')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (30, 'dolorido', 'emociones', 'Dolorido')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (31, 'cansada', 'emociones', 'Cansada')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (32, 'cansado', 'emociones', 'Cansado')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (33, 'triste', 'emociones', 'Triste')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (34, 'tristee', 'emociones', 'Triste')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (35, 'sorprendida', 'emociones', 'Sorprendida')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (36, 'sorprendido', 'emociones', 'Sorprendido')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (37, 'asustado', 'emociones', 'Asustado')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (38, 'asustada', 'emociones', 'Asustada')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (39, 'contenta', 'emociones', 'Contenta')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (40, 'contento', 'emociones', 'Contento')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (41, 'enojada', 'emociones', 'Enojada')");
                    db.execSQL("insert into pictograma (id_pictograma, nombre, carpeta, nombreParaNoti) values (42, 'enojado', 'emociones', 'Enojado')");
                }

                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
            }

}
