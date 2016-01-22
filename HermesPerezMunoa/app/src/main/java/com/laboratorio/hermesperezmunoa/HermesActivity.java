package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        cargarNinos();

        ListView listA = (ListView) this.findViewById(R.id.listaAlumnos);
        ListAdapter adapter = new ArrayAdapter<Child>(this, android.R.layout.simple_list_item_1, childList);
        listA.setAdapter(adapter);

        listA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HermesActivity.this, ModoNinoActivity.class);
                Child c = (Child) adapterView.getAdapter().getItem(position);
                intent.putExtra("chico", c.getNombre());
                startActivity(intent);
            }
        });

        newChild = (Button) findViewById(R.id.BotonNuevoAlumno);

        newChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Intent intent = new Intent(HermesActivity.this, AjustesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void cargarNinos(){
        childList.add(new Child ("Laura", "Aguirre"));
        childList.add(new Child ("Angel", "Akike"));
        childList.add(new Child ("Sandra", "Gulli"));
        childList.add(new Child ("Diego", "Maradona"));
        childList.add(new Child("Clara", "Marrero"));
        childList.add(new Child ("Maria", "Ramirez"));
        childList.add(new Child("Claudia", "Panazzi"));
        childList.add(new Child ("Martin", "Paz"));
        childList.add(new Child ("Marcos", "Perez"));
        childList.add(new Child ("Harry", "Potter"));
    }
}
