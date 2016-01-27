package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AjustesActivity extends AppCompatActivity {

    private String origen="";
    private Child child;
    private Button eliminarChico;
    private Button guaradarChico;
    private EditText nombre;
    private EditText apellido;
    private RadioButton sexoF;
    private Boolean sexo;
    private RadioButton pictograma;
    private int tamPictograma;
    private CheckBox categoria;
    private boolean[] categorias = new boolean[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        //Button back del menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Parametros
        Bundle bundle = getIntent().getExtras();
        origen=bundle.getString("activityOrigen");
        //El nino existe debo caragar datos
        if(origen.equals("modoNino")){
            child =  (Child) getIntent().getExtras().getSerializable("chico");
            apellido = (EditText) findViewById(R.id.editApellido);
            apellido.setText(child.getApellido());
            nombre = (EditText) findViewById(R.id.editNombre);
            nombre.setText(child.getNombre());
            sexoF = (RadioButton) findViewById(R.id.radioButtonF);
            sexoF.setChecked(child.getSexoF());
            sexoF = (RadioButton) findViewById(R.id.radioButtonM);
            sexoF.setChecked(!child.getSexoF());
            pictograma = (RadioButton) findViewById(R.id.radioButtonChico);
            pictograma.setChecked(child.getTamPictograma()==5);
            pictograma = (RadioButton) findViewById(R.id.radioButtonMediano);
            pictograma.setChecked(child.getTamPictograma()==4);
            pictograma = (RadioButton) findViewById(R.id.radioButtonGrande);
            pictograma.setChecked(child.getTamPictograma()==3);
            categorias= child.getCategorias();
            categoria = (CheckBox) findViewById(R.id.checkBoxP);
            categoria.setChecked(categorias[0]);
            categoria = (CheckBox) findViewById(R.id.checkBoxE);
            categoria.setChecked(categorias[1]);
            categoria = (CheckBox) findViewById(R.id.checkBoxN);
            categoria.setChecked(categorias[2]);
            categoria = (CheckBox) findViewById(R.id.checkBoxEm);
            categoria.setChecked(categorias[3]);
        }
        else {
            eliminarChico = (Button) findViewById(R.id.buttonEliminarA);
            eliminarChico.setVisibility(View.INVISIBLE);
        }



        //BOTON ELIMINAR
        eliminarChico = (Button) findViewById(R.id.buttonEliminarA);
        eliminarChico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                if(origen.equals("modoNino")){
                    DataBaseManager DBmanager = new DataBaseManager(AjustesActivity.this);
                    DBmanager.deleteChild(child.getId());
                    Intent intent = new Intent(AjustesActivity.this, HermesActivity.class);
                    startActivity(intent);
                }


            }
        });


        //BOTON GUARDAR
        guaradarChico = (Button) findViewById(R.id.buttonAgregarA);
        guaradarChico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                //RECUPERAR DATOS
                apellido = (EditText) findViewById(R.id.editApellido);
                nombre = (EditText) findViewById(R.id.editNombre);
                //COMPROBAMOS CAMPOS COMPLETOS
                if(apellido.length()==0 || nombre.length()==0 ){
                    Toast mensaje = Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT);
                    mensaje.show();
                }
                else {
                    //COMPROBAMOS QUE EL ALUMNO NO EXISTA
                    DataBaseManager DBmanager = new DataBaseManager(AjustesActivity.this);
                    boolean existe=DBmanager.childExist(apellido.getText().toString(), nombre.getText().toString());
                    if (origen.equals("main") && existe ) {
                        Toast mensaje = Toast.makeText(getApplicationContext(), "El alumno ya existe", Toast.LENGTH_SHORT);
                        mensaje.show();
                    }
                    else {
                        if (origen.equals("modoNino") && existe && !(child.getNombre().equals(nombre.getText().toString()) && child.getApellido().equals(apellido.getText().toString()))) {
                            Toast mensaje = Toast.makeText(getApplicationContext(), "El alumno ya existe", Toast.LENGTH_SHORT);
                            mensaje.show();
                        }else{
                            //RECUPERAR DATOS
                            sexoF = (RadioButton) findViewById(R.id.radioButtonF);
                            sexo = sexoF.isChecked();
                            pictograma = (RadioButton) findViewById(R.id.radioButtonChico);
                            if (pictograma.isChecked()) {
                                tamPictograma = 5;
                            } else {
                                pictograma = (RadioButton) findViewById(R.id.radioButtonMediano);
                                if (pictograma.isChecked()) {
                                    tamPictograma = 4;
                                } else {
                                    tamPictograma = 3;
                                }
                            }
                            categoria = (CheckBox) findViewById(R.id.checkBoxP);
                            categorias[0] = categoria.isChecked();
                            categoria = (CheckBox) findViewById(R.id.checkBoxE);
                            categorias[1] = categoria.isChecked();
                            categoria = (CheckBox) findViewById(R.id.checkBoxN);
                            categorias[2] = categoria.isChecked();
                            categoria = (CheckBox) findViewById(R.id.checkBoxEm);
                            categorias[3] = categoria.isChecked();

                            if (origen.equals("main")) {
                                //AGREGAR ALUMNO BD
                                child = new Child(0, nombre.getText().toString(), apellido.getText().toString(), sexo, tamPictograma, categorias);
                                DBmanager.addChild(child);
                                Intent intent = new Intent(AjustesActivity.this, HermesActivity.class);
                                startActivity(intent);
                            } else {
                                //MODIFICAR ALUMNO BD
                                int id = child.getId();
                                child = new Child(id, nombre.getText().toString(), apellido.getText().toString(), sexo, tamPictograma, categorias);
                                DBmanager.updateChild(child);
                                Intent intent = new Intent(AjustesActivity.this, ModoNinoActivity.class);
                                intent.putExtra("chico", child);
                                startActivity(intent);

                            }
                        }
                    }

                }
            }
        });



    }




    //MANEJO DEL BOTON BACK
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent intent;
            if(origen.equals("main")){
                intent = new Intent(AjustesActivity.this, HermesActivity.class);
            }
            else{
                intent = new Intent(AjustesActivity.this, ModoNinoActivity.class);
                intent.putExtra("chico", child);
            }
            startActivity(intent);
        }
        return true;
    }
}
