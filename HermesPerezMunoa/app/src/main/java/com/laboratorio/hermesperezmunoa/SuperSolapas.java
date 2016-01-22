package com.laboratorio.hermesperezmunoa;


import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SuperSolapas extends AppCompatActivity {

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, getResources());
        task.execute(resId);
    }
}
