package com.laboratorio.hermesperezmunoa;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class AdaptadorDePictogramas extends BaseAdapter {

    private Activity _activity;
    private List<Pictograma> listaIdImagenes;
    private int imageWidth;

    public AdaptadorDePictogramas(Activity activity, List<Pictograma> listaIdImagenes, int imageWidth) {
        this._activity = activity;
        this.listaIdImagenes = listaIdImagenes;
        this.imageWidth = imageWidth;

    }

    @Override
    public int getCount() {
        return this.listaIdImagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listaIdImagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Pictograma> getElements(){
        return this.listaIdImagenes;
    }

    public void deleteElement(int id){
        this.listaIdImagenes.remove(id);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
            imageView.setPadding(2, 2, 2, 2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
        }
        else {
            imageView = (ImageView) convertView;
        }
        Pictograma p= (Pictograma)getItem(position);
        if(p.isSelected())imageView.setBackgroundColor(Color.parseColor("#303F9F"));
        ((SuperSolapas) _activity).loadBitmap(p.getCarpeta()+"/"+p.getNombre()+".png", imageView, _activity);

        return imageView;
    }



}