package com.laboratorio.hermesperezmunoa;

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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return true;
        }

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
        private String[] pictogramasDelNino;
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edicion, container, false);



            //CONTENIDO DEL FRAGMENTO

            gridView = (GridView) rootView.findViewById(R.id.pictogrmas_edicion);

            DataBaseManager DBmanager = new DataBaseManager(getActivity());
            pictogramasDelNino = DBmanager.getPictogramas(1);


            switch ((int) getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:

                    imagenes=new String[]{
                            "pista/casco.png",
                            "pista/chapas.png",
                            "pista/letras.png",
                            "pista/cubos.png",
                            "pista/maracas.png",
                            "pista/palos.png",
                            "pista/pato.png",
                            "pista/pelota.png",
                            "pista/riendas.png",
                            "pista/burbujas.png",
                            "pista/broches.png",
                            "pista/aro.png",
                            "pista/tarima.png"};
                    break;
                case 1:
                    imagenes=new String[]{
                            "establo/cepillo.png",
                            "establo/limpieza.png",
                            "establo/escarba.png",
                            "establo/montura.png",
                            "establo/matra.png",
                            "establo/raqueta_dura.png",
                            "establo/raqueta_blanda.png",
                            "establo/pasto.png",
                            "establo/zanahoria.png",
                            "establo/caballo_b.png",
                            "establo/caballo_m.png",
                            "establo/caballo_n.png"};
                    break;
                case 2:
                    imagenes=new String[]{
                            "necesidades/bano.png" ,
                            "necesidades/sed.png" ,
                            "necesidades/seed.png"};
                    break;
                case 3:
                    imagenes=new String[]{
                            "emociones/dolorida.png",
                            "emociones/dolorido.png",
                            "emociones/cansada.png",
                            "emociones/cansado.png",
                            "emociones/triste.png",
                            "emociones/tristee.png",
                            "emociones/sorprendida.png",
                            "emociones/sorprendido.png",
                            "emociones/asustado.png",
                            "emociones/asustada.png",
                            "emociones/contenta.png",
                            "emociones/contento.png",
                            "emociones/enojada.png",
                            "emociones/enojado.png"};
                    break;
                case 4:
                    imagenes=new String[]{
                            "emociones/dolorida.png",
                            "emociones/dolorido.png",
                            "emociones/cansada.png",
                            "emociones/asustado.png",
                            "emociones/asustada.png",
                            "emociones/contenta.png",
                            "emociones/contento.png",
                            "emociones/enojada.png",
                            "emociones/enojado.png"};
            }



            //TAMANO PANTALLA
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width=dm.widthPixels;


            //ESPACIO DISPONIBLE PARA GRID
            gridView.setColumnWidth(width);

            //TAMANO COLUMNAS DEL GRID
            width=(( width-90)/4);
            adaptador = new AdaptadorDePictogramas(getActivity(), imagenes,width);
            gridView.setAdapter(adaptador);


            //Listener gridview
            if((int) getArguments().getInt(ARG_SECTION_NUMBER)==4){
                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                            ImageView tv = (ImageView) gridView.getChildAt(position);
                            tv.setBackgroundColor(Color.parseColor("#000000"));
                           //eliminar elemento bd y gridview
                            return true;

                        }
                    });


                }
                else{
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        ImageView tv = (ImageView) gridView.getChildAt(position);

                        if(false){//saber que concha de color tiene de fondo!!!!
                            tv.setBackgroundColor(Color.parseColor("#9eb7c9"));
                            //Baja bd

                        }
                        else{
                            tv.setBackgroundColor(Color.parseColor("#303F9F"));
                            //ALTA BD

                        }



                    }

                });

            }







            return rootView;
        }

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
          return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {return 5;}

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pista";
                case 1:
                    return "Establo";
                case 2:
                    return "Necesidades";
                case 3:
                    return "Emociones";
                case 4:
                    return child.getNombre();
            }
            return null;
        }
    }
}
