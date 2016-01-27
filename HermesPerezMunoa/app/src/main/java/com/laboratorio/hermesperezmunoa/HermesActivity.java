package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HermesActivity extends AppCompatActivity {

    List<Child> childList=new ArrayList<Child>();
    Button newChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hermes);

        //LISTADO DE NINOS
        cargarNinos();

        ListView listA = (ListView) this.findViewById(R.id.listaAlumnos);
        ListAdapter adapter = new ArrayAdapter<Child>(this, android.R.layout.simple_list_item_1, childList);
        listA.setAdapter(adapter);

        listA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HermesActivity.this, ModoNinoActivity.class);
                Child c = (Child) adapterView.getAdapter().getItem(position);
                intent.putExtra("chico", c);
                startActivity(intent);
            }
        });


        //BOTON DE NUEVO NINO
        newChild = (Button) findViewById(R.id.BotonNuevoAlumno);

        newChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Intent intent = new Intent(HermesActivity.this, AjustesActivity.class);
                intent.putExtra("activityOrigen", "main");
                startActivity(intent);
            }
        });


    }


    //MENU
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(HermesActivity.this, ConfiguracionGeneralActivity.class);
        startActivity(intent);
        return true;
    }

    //CONSULTA BD
    private void cargarNinos(){
        DataBaseManager DBmanager = new DataBaseManager(this);
        childList= DBmanager.listChild();
    }
}
