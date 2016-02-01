package com.laboratorio.hermesperezmunoa;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Carolina on 16/12/2015.
 */
class BitmapWorkerTask extends AsyncTask<String, Void, Drawable> {
    private final WeakReference<ImageView> imageViewReference;
    private Activity res;

    public BitmapWorkerTask(ImageView imageView, Activity res) {
        this.res = res;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }


    @Override
    protected Drawable doInBackground(String... params) {
        InputStream ims = null;
        try {
            ims = res.getAssets().open(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(ims, null);

        return d;
    }

    @Override
    protected void onPostExecute(Drawable d) {
        if (imageViewReference != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageDrawable(d);
            }
        }
    }
}
