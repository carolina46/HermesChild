package com.laboratorio.hermesperezmunoa;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Carolina on 16/12/2015.
 */
class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private Activity res;

    public BitmapWorkerTask(ImageView imageView, Activity res) {
        this.res = res;
        imageViewReference = new WeakReference<ImageView>(imageView);
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream ims = null;
        try {
            ims = new BufferedInputStream(res.getAssets().open(params[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(ims);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
