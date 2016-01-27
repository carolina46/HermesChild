package com.laboratorio.hermesperezmunoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carolina on 11/12/2015.
 */


public class Child implements Serializable{
    private int id;
    private String nombre;
    private String apellido;
    private boolean sexoF;
    private int tamPictograma;
    private boolean[] categorias = new boolean[4];
    //[pista,establo,necesidades,emociones]

    public Child(int id, String nombre, String apellido, boolean sexoF, Integer tamPictograma,boolean[] categorias  ) {
        this.id=id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexoF=sexoF;
        this.tamPictograma=tamPictograma;
        this.categorias=categorias;

    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}

    public boolean getSexoF() {return sexoF;}

    public void setSexoF(boolean sexoF) {this.sexoF = sexoF;}

    public int getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public int getTamPictograma() {return tamPictograma;}

    public void setTamPictograma(Integer tamPictograma) {this.tamPictograma = tamPictograma;}

    public boolean[] getCategorias() {return categorias;}

    public void setCategorias(boolean[] categorias) {this.categorias = categorias;}

    public List<String> categoriasHabilitadas(){
        List<String> categoriasHabilitadas= new ArrayList<String>();
        if(categorias[0]) categoriasHabilitadas.add("Pista");
        if(categorias[1]) categoriasHabilitadas.add("Establo");
        if(categorias[2]) categoriasHabilitadas.add("Necesidades");
        if(categorias[3]) categoriasHabilitadas.add("Emociones");
        return categoriasHabilitadas;
    }

    @Override
    public String toString() {
        return this.getApellido()+", "+this.getNombre();
    }
}
