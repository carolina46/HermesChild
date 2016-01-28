package com.laboratorio.hermesperezmunoa;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class AdaptadorDePictogramas extends BaseAdapter {

    private Activity _activity;
    private String[] listaIdImagenes;
    private int imageWidth;

    public AdaptadorDePictogramas(Activity activity, String[] listaIdImagenes, int imageWidth) {
        this._activity = activity;
        this.listaIdImagenes = listaIdImagenes;
        this.imageWidth = imageWidth;

    }

    @Override
    public int getCount() {
        return this.listaIdImagenes.length;
    }

    @Override
    public Object getItem(int position) {
        return this.listaIdImagenes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
            imageView.setPadding(2, 2, 2, 2);
            imageView.setBackgroundColor(Color.parseColor("#9eb7c9"));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            //((SuperSolapas) _activity).loadBitmap(this.listaIdImagenes[position], imageView);


            InputStream ims = null;
            try {
                ims = _activity.getAssets().open(listaIdImagenes[position]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imageView.setImageDrawable(d);


            //imageView.setImageDrawable(_activity.getResources().getDrawable(this.listaIdImagenes[position]));

        } else {
            imageView = (ImageView) convertView;
        }




        return imageView;
    }



}