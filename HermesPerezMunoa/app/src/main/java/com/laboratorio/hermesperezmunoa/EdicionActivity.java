package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class EdicionActivity extends SuperSolapas {

    private Child child;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);


        //RECUPERO PARAMETROS
        child =  (Child) getIntent().getExtras().getSerializable("chico");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), child );

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(EdicionActivity.this, ModoNinoActivity.class);
            intent.putExtra("chico", child);
            startActivity(intent);
        }
        return true;

    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private GridView gridView;
        private AdaptadorDePictogramas adaptador;
        private int anchoColumna;
        private String[] imagenes;
        private List<Pictograma> pictogramasCategoria;
        private List<Pictograma> pictogramasChico;
        private static Child child;
        private int width;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber, Child aChild) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            child = aChild;
            return fragment;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edicion, container, false);
            //Contenido del fragmento
            gridView = (GridView) rootView.findViewById(R.id.pictogrmas_edicion);
            DataBaseManager DBmanager = new DataBaseManager(getActivity());
            pictogramasChico = DBmanager.getPictogramasChild(child.getId());
            switch ((int) getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0: pictogramasCategoria = DBmanager.getPictogramasCategoria("pista"); break;
                case 1: pictogramasCategoria = DBmanager.getPictogramasCategoria("establo"); break;
                case 2: pictogramasCategoria = DBmanager.getPictogramasCategoria("necesidades"); break;
                case 3: pictogramasCategoria = DBmanager.getPictogramasCategoria("emociones"); break;
                case 4: pictogramasCategoria = pictogramasChico;
            }

            //Pinto pictogramas seleccionados
            if  ((int) getArguments().getInt(ARG_SECTION_NUMBER) !=4) {
                for(Pictograma p: pictogramasChico){
                    for(int i=0; i<pictogramasCategoria.size(); i++){
                        if(pictogramasCategoria.get(i).getId()== p.getId()){
                            pictogramasCategoria.get(i).setSelected(true);
                        }
                    }

                }
            }

            //Tamano de la pantalla
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            width=dm.widthPixels;
            //Tamano del gridview
            gridView.setColumnWidth(width-50);
            //Tamano columnas gridview
            width=(( width-150)/4);
            adaptador = new AdaptadorDePictogramas(getActivity(), pictogramasCategoria,width);
            gridView.setAdapter(adaptador);



            //Listener gridview
            if((int) getArguments().getInt(ARG_SECTION_NUMBER)==4){ //Solapa nino
                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                            ImageView tv = (ImageView) gridView.getChildAt(position);
                            int pictograma = ((Pictograma) gridView.getAdapter().getItem(position)).getId();
                            DataBaseManager DBmanager = new DataBaseManager(getActivity());
                            DBmanager.removePictogramaChico(pictograma,  child.getId());
                            //Elimino elemento del gridView
                            List<Pictograma> list = ((AdaptadorDePictogramas) gridView.getAdapter()).getElements();
                            list.remove(position);
                            adaptador = new AdaptadorDePictogramas(getActivity(), list,width);
                            gridView.setAdapter(adaptador);
                            return true;

                        }
                    });
            }
            else{//Solapas categorias
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        ImageView tv = (ImageView) gridView.getChildAt(position);
                        Pictograma p = (Pictograma) gridView.getAdapter().getItem(position);
                        DataBaseManager DBmanager = new DataBaseManager(getActivity());
                        if(p.isSelected()){
                            tv.setBackgroundColor(Color.parseColor("#9eb7c9"));
                            DBmanager.removePictogramaChico(p.getId(), child.getId());
                            p.setSelected(false);
                        }
                        else{
                            tv.setBackgroundColor(Color.parseColor("#303F9F"));
                            DBmanager.addPictogramaChico(p.getId(),  child.getId());
                            p.setSelected(true);
                        }
                }

                });
            }
            return rootView;
        }



    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private Child child;

        public SectionsPagerAdapter(FragmentManager fm, Child child) {
            super(fm);
            this.child = child;
        }


        @Override
        public Fragment getItem(int position) {
          return PlaceholderFragment.newInstance(position, child);
        }

        @Override
        public int getCount() {return 5;}

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Pista";
                case 1: return "Establo";
                case 2: return "Necesidades";
                case 3: return "Emociones";
                case 4: return child.getNombre();
            }
            return null;
        }
    }
}
