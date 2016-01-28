package com.laboratorio.hermesperezmunoa;

/**
 * Created by Carolina on 28/01/2016.
 */
public class Pictograma {

    private String nombre;
    private String carpeta;
    private int id;

    public Pictograma(int id, String nombre, String carpeta ){
        this.nombre=nombre;
        this.carpeta=carpeta;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
