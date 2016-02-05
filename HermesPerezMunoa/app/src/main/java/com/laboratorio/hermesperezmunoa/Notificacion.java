package com.laboratorio.hermesperezmunoa;

/**
 * Created by Carolina on 29/01/2016.
 */
public class Notificacion {

    private int id;
    private String nombreChico;
    private String contenidoPictograma;
    private String categoriaPictograma;

    public Notificacion(String nombreChico, String contenidoPictograma, String categoriaPictograma) {
        this.nombreChico = nombreChico;
        this.contenidoPictograma = contenidoPictograma;
        this.categoriaPictograma = categoriaPictograma;
    }

    public Notificacion(int id, String nombreChico, String contenidoPictograma, String categoriaPictograma) {
        this.id = id;
        this.nombreChico = nombreChico;
        this.contenidoPictograma = contenidoPictograma;
        this.categoriaPictograma = categoriaPictograma;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getNombreChico() {
        return nombreChico;
    }

    public void setNombreChico(String nombreChico) {
        this.nombreChico = nombreChico;
    }

    public String getContenidoPictograma() {
        return contenidoPictograma;
    }

    public void setContenidoPictograma(String contenidoPictograma) {
        this.contenidoPictograma = contenidoPictograma;
    }

    public String getCategoriaPictograma() {
        return categoriaPictograma;
    }

    public void setCategoriaPictograma(String categoriaPictograma) {
        this.categoriaPictograma = categoriaPictograma;
    }
}
