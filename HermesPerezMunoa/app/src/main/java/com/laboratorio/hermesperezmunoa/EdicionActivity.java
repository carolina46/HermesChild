package com.laboratorio.hermesperezmunoa;

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
import android.widget.GridView;

public class EdicionActivity extends SuperSolapas {

    private String nombre;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);


        //RECUPERO PARAMETROS
        Bundle bundle = getIntent().getExtras();
        nombre=  bundle.getString("chico");

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
        private Integer[] imagenes;
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


            switch ((int) getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    imagenes = new Integer[]{
                            R.drawable.casco,
                            R.drawable.chapas,
                            R.drawable.letras,
                            R.drawable.cubos,
                            R.drawable.maracas,
                            R.drawable.palos,
                            R.drawable.pato,
                            R.drawable.pelota,
                            R.drawable.riendas,
                            R.drawable.burbujas,
                            R.drawable.broches,
                            R.drawable.aro,
                            R.drawable.tarima
                    };
                    break;
                case 1:
                    imagenes = new Integer[]{
                            R.drawable.cepillo,
                            R.drawable.limpieza,
                            R.drawable.escarba,
                            R.drawable.montura,
                            R.drawable.matra,
                            R.drawable.raqueta_dura,
                            R.drawable.raqueta_blanda,
                            R.drawable.pasto,
                            R.drawable.zanahoria,
                            R.drawable.caballo_b,
                            R.drawable.caballo_m,
                            R.drawable.caballo_n};
                    break;
                case 2:
                    imagenes = new Integer[]{
                            R.drawable.bano,
                            R.drawable.sed,
                            R.drawable.seed};
                    break;
                case 3:
                    imagenes = new Integer[]{
                            R.drawable.dolorida,
                            R.drawable.dolorido,
                            R.drawable.cansada,
                            R.drawable.cansado,
                            R.drawable.triste,
                            R.drawable.tristee,
                            R.drawable.sorprendida,
                            R.drawable.sorprendido,
                            R.drawable.asustado,
                            R.drawable.asustada,
                            R.drawable.contenta,
                            R.drawable.contento,
                            R.drawable.enojada,
                            R.drawable.enojado};
                    break;
                case 4:
                    imagenes = new Integer[0];
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
                    return nombre;
            }
            return null;
        }
    }
}
