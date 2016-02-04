package com.laboratorio.hermesperezmunoa;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SuperSolapas extends AppCompatActivity {

    public void loadBitmap(String res, ImageView imageView, Activity activity) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, activity);
        task.execute(res);
    }
}
