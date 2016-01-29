package com.laboratorio.hermesperezmunoa;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
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
import android.widget.AdapterView;
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
       //Parametros
        child = (Child) getIntent().getExtras().getSerializable("chico");
        //Titulo menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HERMES  " + (child.getNombre() + " " + child.getApellido()).toUpperCase());
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), child);
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




    //////////////////////////////////////////////////////////
    public static class PlaceholderFragment extends Fragment {
        private GridView gridView;
        private int anchoColumna;
        private AdaptadorDePictogramas adaptador;
        private String[] imagenes;
        private List<Pictograma> pictogramasChico;
        private List<Pictograma> pictogramasCategoria;
        private static Child child;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {  }

        public static PlaceholderFragment newInstance(int sectionNumber, Child aChild) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            child = aChild;
            return fragment;
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_modo_nino, container, false);
            //Contenido
            gridView = (GridView) rootView.findViewById(R.id.pictogrmas);
            DataBaseManager DBmanager = new DataBaseManager(getActivity());
            pictogramasChico = DBmanager.getPictogramasChild(child.getId());
            List<String> categoriasHabilitadas = child.categoriasHabilitadas();
            categoriasHabilitadas.add(child.getNombre());
            //Agrego pictograms dependiendo numero de solapa
            String c= categoriasHabilitadas.get(((int) getArguments().getInt(ARG_SECTION_NUMBER)));
            if(c.equals(child.getNombre())){pictogramasCategoria = pictogramasChico;}
            else{pictogramasCategoria  = DBmanager.getPictogramasCategoriaChico(c, child.getId());}

            //Calculo tamaño pantalla
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            //Determino espacio para el gridView
            int espacioGridView=0;
            int margenColumnas=0;
            switch (child.getTamPictograma()){
                case 3: espacioGridView= (width*75)/100; margenColumnas=12; break;
                case 4: espacioGridView= (width*80)/100; margenColumnas=20; break;
                case 5: espacioGridView= (width*85)/100; margenColumnas=28;  break;
            }
            gridView.setColumnWidth(espacioGridView);
            gridView.setNumColumns(child.getTamPictograma());
            width=((espacioGridView- (margenColumnas* child.getTamPictograma()))/child.getTamPictograma());
            //Adaptador
            adaptador = new AdaptadorDePictogramas(getActivity(), pictogramasCategoria,width);
            gridView.setAdapter(adaptador);

            //Tamano imagenes si y no
            ImageView im = (ImageView) rootView.findViewById(R.id.no);
            im.getLayoutParams().height=width;
            im.getLayoutParams().width=width;

            im.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    MediaPlayer mp = MediaPlayer.create(getActivity(),R.raw.no );
                    mp.start();    }
            });

            im = (ImageView) rootView.findViewById(R.id.si);
            im.getLayoutParams().height=width;
            im.getLayoutParams().width=width;

            im.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    MediaPlayer mp = MediaPlayer.create(getActivity(),R.raw.si );
                    mp.start();    }
            });

            //Listener del gridView para los sonidos
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Pictograma p = (Pictograma) gridView.getAdapter().getItem(position);
                    try {
                        AssetFileDescriptor ims = null;
                        ims = getActivity().getAssets().openFd(p.getCarpeta()+"/"+p.getNombre()+".m4a");
                        MediaPlayer mp = new MediaPlayer();
                        mp.setDataSource(ims.getFileDescriptor(),ims.getStartOffset(),ims.getLength());
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            });

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

        Child child;

        public SectionsPagerAdapter(FragmentManager fm, Child child) {
            super(fm);
            this.child = child;
        }

        @Override
        public Fragment getItem(int position) {
           return PlaceholderFragment.newInstance(position, child);
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
