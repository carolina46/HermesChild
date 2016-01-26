package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionGeneralActivity extends AppCompatActivity {

    private  EditText ip;
    private  EditText puerto;
    private  List<String> aux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_general);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DataBaseManager DBmanager = new DataBaseManager(this);
        aux  = DBmanager.getConfiguration();


        ip = (EditText) findViewById(R.id.editIP);
        ip.setText(aux.get(0));
        puerto = (EditText) findViewById(R.id.editPuerto);
        puerto.setText(aux.get(1));

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    aux= new ArrayList<String>();
                    ip = (EditText) findViewById(R.id.editIP);
                    aux.add( ip.getText().toString());
                    puerto = (EditText) findViewById(R.id.editPuerto);
                    aux.add( puerto.getText().toString());
                    DataBaseManager DBmanager = new DataBaseManager(this);
                    DBmanager.setConfiguration(aux);

                    Intent intent = new Intent(ConfiguracionGeneralActivity.this, HermesActivity.class);
                    startActivity(intent);
                return true;
            default:
                return true;
        }
    }


}
