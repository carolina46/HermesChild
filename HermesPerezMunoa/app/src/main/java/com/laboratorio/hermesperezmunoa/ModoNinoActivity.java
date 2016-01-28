package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ModoNinoActivity extends SuperSolapas {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Child child;
    private AppBarLayout bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_nino);
        //Button back del menu



       //RECUPERO PARAMETROS
       child =  (Child) getIntent().getExtras().getSerializable("chico");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("HERMES      " + child.getNombre());
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modo_nino, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.modo_edicion) {
           Intent intent = new Intent(ModoNinoActivity.this, EdicionActivity.class);
           intent.putExtra("chico", child);
           startActivity(intent);
        }
        if(id == R.id.ajustes) {
            Intent intent = new Intent(ModoNinoActivity.this, AjustesActivity.class);
            intent.putExtra("activityOrigen", "modoNino");
            intent.putExtra("chico", child);
            startActivity(intent);
        }
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(ModoNinoActivity.this, HermesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //FRAGMENTO
    public static class PlaceholderFragment extends Fragment {
        private GridView gridView;
        private int anchoColumna;
        private AdaptadorDePictogramas adaptador;
        private String[] imagenes;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {  }

        //SONIDO BOTONES DE SI Y NO
        public void presionoSi (View v) {
            MediaPlayer mp = MediaPlayer.create(getActivity(),R.raw.si );
            mp.start();
        }

        public void presionoNo (View v) {
            MediaPlayer mp = MediaPlayer.create(getActivity(),R.raw.no );
            mp.start();
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            System.out.print(sectionNumber);
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_modo_nino, container, false);

            //CONTENIDO DEL FRAGMENTO

            gridView = (GridView) rootView.findViewById(R.id.pictogrmas);





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
                    imagenes=new String[0];
            }


            //TAMANO PANTALLA
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width=dm.widthPixels;


            //ESPACIO DISPONIBLE PARA GRID
            gridView.setColumnWidth((width*75)/100);

            //TAMANO COLUMNAS DEL GRID
            width=((  ((width*75)/100)  -48)/3);
            adaptador = new AdaptadorDePictogramas(getActivity(), imagenes,width);
            gridView.setAdapter(adaptador);


            //TAMAÑO ADAPTATIVO DEL SI Y NO
            ImageView im = (ImageView) rootView.findViewById(R.id.no);
            im.getLayoutParams().height=width;
            im.getLayoutParams().width=width;

            im= (ImageView) rootView.findViewById(R.id.si);
            im.getLayoutParams().height=width;
            im.getLayoutParams().width=width;

            return rootView;
        }



        public InputStream getAsset(String ruta) {
            AssetManager assetManager = getResources().getAssets();
            InputStream inputStream = null;

            try {
                inputStream = assetManager.open(ruta);
                if (inputStream != null)
                    return inputStream;
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }







    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
                    //PlaceholderFragment.newInstance(position + 1);
        }

        //cantidad de solapas
        @Override
        public int getCount() {
            return child.categoriasHabilitadas().size() + 1;
        }

        //Nombre de las solapas
        @Override
        public CharSequence getPageTitle(int position) {
            List<String> categoriasHabilitadas = child.categoriasHabilitadas();
            categoriasHabilitadas.add(child.getNombre());
            return categoriasHabilitadas.get(position);
        }
    }
}
