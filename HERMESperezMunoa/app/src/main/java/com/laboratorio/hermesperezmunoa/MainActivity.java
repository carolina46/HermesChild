package com.laboratorio.hermesperezmunoa;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Child> childList=new ArrayList<Child>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarNinos();

        ListView listA = (ListView) this.findViewById(R.id.listaAlumnos);
        ListAdapter adapter = new ArrayAdapter<Child>(this, android.R.layout.simple_list_item_1, childList);
        listA.setAdapter(adapter);

    }


    private void cargarNinos(){
        childList.add(new Child ("Marcos", "Perez"));
        childList.add(new Child ("Laura", "Aguirre"));
        childList.add(new Child ("Diego", "Maradona"));
        childList.add(new Child ("Harry", "Potter"));
        childList.add(new Child ("Maria", "Ramirez"));
        childList.add(new Child ("Angel", "Akike"));
        childList.add(new Child ("Sandra", "Gulli"));
        childList.add(new Child ("Martin", "Paz"));
        childList.add(new Child ("Clara", "Marrero"));
        childList.add(new Child ("Claudia", "Panazzi"));
    }
}
