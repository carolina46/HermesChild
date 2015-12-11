package com.laboratorio.hermesperezmunoa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> childList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarNinos();
    }


    private void cargarNinos(){
        childList.add("Marcos");
        childList.add("Laura");
        childList.add("Coca");
        childList.add("Diego");
        childList.add("Noe");
        childList.add("Federico");
        childList.add("Juan");
    }
}
