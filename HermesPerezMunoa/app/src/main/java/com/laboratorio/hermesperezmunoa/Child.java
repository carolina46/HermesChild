package com.laboratorio.hermesperezmunoa;

/**
 * Created by Carolina on 11/12/2015.
 */


public class Child {
    private String nombre;
    private String apellido;

    public Child(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return this.getApellido()+", "+this.getNombre();
    }
}
