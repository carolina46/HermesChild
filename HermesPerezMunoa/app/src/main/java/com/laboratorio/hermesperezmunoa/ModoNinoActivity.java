package com.laboratorio.hermesperezmunoa;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

public class ModoNinoActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String nombre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo_nino);

        Bundle bundle = getIntent().getExtras();
        nombre=  bundle.getString("chico");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modo_nino, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.modo_edicion) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {

        private GridView gridView;
        private int anchoColumna;
        private AdaptadorDePictogramas adaptador;
        private Integer[] imagenes;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {  }


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


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

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

        @Override
        public int getCount() {
            // Show total pages.
            return 5;
        }

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
