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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EdicionActivity extends SuperSolapas {

    private static Child child;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private static GridView fragmentoNino= null;
    private static GridView fragmentoPista = null;
    private static GridView fragmentoEstablo = null;
    private static GridView fragmentoNecesidades = null;
    private static GridView fragmentoEmociones = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);


        //RECUPERO PARAMETROS

        child=(new DataBaseManager(this)).getChildSelected();
        //child =  (Child) getIntent().getExtras().getSerializable("chico");

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
            this.finish();
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

            child=(new DataBaseManager(getContext())).getChildSelected();

            pictogramasChico = DBmanager.getPictogramasChild(child.getId());
            switch ((int) getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0: pictogramasCategoria = DBmanager.getPictogramasCategoria("pista");break;
                case 1: pictogramasCategoria = DBmanager.getPictogramasCategoria("establo");break;
                case 2: pictogramasCategoria = DBmanager.getPictogramasCategoria("necesidades");break;
                case 3: pictogramasCategoria = DBmanager.getPictogramasCategoria("emociones");break;
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
            width=(( width-200)/5);
            adaptador = new AdaptadorDePictogramas(getActivity(), pictogramasCategoria,width);
            gridView.setAdapter(adaptador);



            //GuardoAdaptadores
            switch ((int) getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0: fragmentoPista=gridView;break;
                case 1: fragmentoEstablo=gridView;break;
                case 2: fragmentoNecesidades=gridView;break;
                case 3: fragmentoEmociones=gridView;break;
                case 4: fragmentoNino=gridView;
            }


            //Listener gridview
            if((int) getArguments().getInt(ARG_SECTION_NUMBER)==4){ //Solapa nino
                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                        ImageView tv = (ImageView) gridView.getChildAt(position);
                        Pictograma pictograma = ((Pictograma) gridView.getAdapter().getItem(position));
                        DataBaseManager DBmanager = new DataBaseManager(getActivity());
                        DBmanager.removePictogramaChico(pictograma.getId(),  child.getId());
                        pictograma.setSelected(false);
                        //Elimino elemento del gridView
                        List<Pictograma> list = ((AdaptadorDePictogramas) gridView.getAdapter()).getElements();
                        list.remove(position);
                        adaptador = new AdaptadorDePictogramas(getActivity(), list,width);
                        gridView.setAdapter(adaptador);
                        refreshFragment(pictograma);
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
                            if(tv!=null){tv.setBackgroundColor(Color.parseColor("#9eb7c9"));}
                            DBmanager.removePictogramaChico(p.getId(), child.getId());
                            p.setSelected(false);
                        }
                        else{
                            if(tv!=null){tv.setBackgroundColor(Color.parseColor("#303F9F"));}
                            DBmanager.addPictogramaChico(p.getId(), child.getId());
                            p.setSelected(true);
                        }

                        if(tv==null) {
                            List<Pictograma> list = ((AdaptadorDePictogramas) gridView.getAdapter()).getElements();
                            adaptador = new AdaptadorDePictogramas(getActivity(), list, width);
                            gridView.setAdapter(adaptador);
                        }
                        if(fragmentoNino!=null){
                            List<Pictograma> list =DBmanager.getPictogramasChild(child.getId());
                            fragmentoNino.setAdapter(new AdaptadorDePictogramas(getActivity(), list, width));
                        }
                    }

                });
            }
            return rootView;
        }

        private void refreshFragment(Pictograma pictograma) {
            Map<String,Integer > posCarpetas = new HashMap<String,Integer >();
            posCarpetas.put("pista",0);
            posCarpetas.put("establo",1);
            posCarpetas.put("necesidades",2);
            posCarpetas.put("emociones",3);
            List<Pictograma> list;

            switch (posCarpetas.get(pictograma.getCarpeta())) {
                case 0:
                    if(fragmentoPista!=null){
                        list = ((AdaptadorDePictogramas) fragmentoPista.getAdapter()).getElements();
                        fragmentoPista.setAdapter(new AdaptadorDePictogramas(getActivity(), list, width));
                    }
                    break;
                case 1:
                    if(fragmentoEstablo!=null){
                        list = ((AdaptadorDePictogramas) fragmentoEstablo.getAdapter()).getElements();
                        fragmentoEstablo.setAdapter(new AdaptadorDePictogramas(getActivity(), list, width));
                    }
                    break;
                case 2:
                    if(fragmentoNecesidades!=null){
                        list = ((AdaptadorDePictogramas) fragmentoNecesidades.getAdapter()).getElements();
                        fragmentoEstablo.setAdapter(new AdaptadorDePictogramas(getActivity(), list, width));
                    }

                    break;
                case 3:
                    if(fragmentoEmociones!=null){
                        list = ((AdaptadorDePictogramas) fragmentoEmociones.getAdapter()).getElements();
                        fragmentoEstablo.setAdapter(new AdaptadorDePictogramas(getActivity(), list, width));
                    }
            }


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
