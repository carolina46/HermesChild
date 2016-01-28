package com.laboratorio.hermesperezmunoa;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class AdaptadorDePictogramas extends BaseAdapter {

    private Activity _activity;
    private Integer[] listaIdImagenes;
    private int imageWidth;

    public AdaptadorDePictogramas(Activity activity, Integer[] listaIdImagenes,
                                int imageWidth) {
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
            imageView.setPadding(2,2,2,2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            ((SuperSolapas) _activity).loadBitmap(this.listaIdImagenes[position], imageView);
            //imageView.setImageDrawable(_activity.getResources().getDrawable(this.listaIdImagenes[position]));

        } else {
            imageView = (ImageView) convertView;
        }




        return imageView;
    }



}